package net.mametaku.labyrinth;

import org.bukkit.Bukkit;

import java.io.*;
import java.util.Random;

import static net.mametaku.labyrinth.Main.config;
import static net.mametaku.labyrinth.Main.dataFolder;

public class LabyrinthSystem {

    private static int pointX; //ブロックを置いたり消したりする目印。
    private static int pointY;
    public int labyrinthSize = config.getInt("labyrinthSize");
    public String[][] labyrinthObject = new String[labyrinthSize][labyrinthSize];

    public LabyrinthSystem() {
        if (labyrinthSize % 2 != 0 && 5 <= labyrinthSize) {
            for (int i = 0; i < labyrinthSize; i++) {
                for (int j = 0; j < labyrinthSize; j++) {
                    labyrinthObject[i][j] = "wall";
                }
            }
            pointX = randomPos();
            pointY = randomPos();
            labyrinthObject[pointX][pointY] = "empty";

            dig();
            setplayerAndGoal();
            show();
        } else {
            Bukkit.getLogger().info("縦・横共に5以上の奇数で作成してください。");
        }
    }

    private void dig() {
        if (labyrinthObject[pointX][pointY].equals("empty") && isAbleContinueDig()){
            Random rnd = new Random();
            int direction;
            direction = rnd.nextInt(4);
            switch (direction){
                case 0:
                    if (pointY != 1) {
                        if (labyrinthObject[pointX][pointY - 2].equals("wall")) {
                            labyrinthObject[pointX][pointY - 1] = "empty";
                            pointY -= 2;
                            //u
                        }
                    }
                case 1:
                    if (pointY != labyrinthSize - 2) {
                        if (labyrinthObject[pointX][pointY + 2].equals("wall")) {
                            labyrinthObject[pointX][pointY + 1] = "empty";
                            pointY += 2;
                            //d
                        }
                    }
                case 2:
                    if (pointX != 1) {
                        if (labyrinthObject[pointX - 2][pointY].equals("wall")) {
                            labyrinthObject[pointX - 1][pointY] = "empty";
                            pointX -= 2;
                            //l
                        }
                    }
                case 3:
                    if (pointX != labyrinthSize - 2) {
                        if (labyrinthObject[pointX + 2][pointY].equals("wall")) {
                            labyrinthObject[pointX + 1][pointY] = "empty";
                            pointX += 2;
                            //r
                        }
                    }
            }
            labyrinthObject[pointX][pointY] = "empty";
            dig();
        }else if (isAbleDig()){
            pointX = randomPos();
            pointY = randomPos();
            dig();
        }
    }

    int randomPos() { //x,y座標共に奇数なランダムな座標を返す
        return 1 + 2 * (int) Math.floor((Math.random() * (labyrinthSize - 1)) / 2);
    }

    private boolean isAbleDig() { //まだ掘るところがあるか確かめる
        int cnt = 0;
        for (int i = 0;i < labyrinthSize;i++){
            for (int j = 0;j < labyrinthSize;j++){
                if (i % 2 != 0 && j % 2 != 0){
                    if (!labyrinthObject[i][j].equals("empty")){
                        cnt++;
                    }
                }
            }
        }
        return (cnt != 0);
    }

    private boolean isAbleContinueDig(){
        if (pointY != 1){
            if (labyrinthObject[pointX][pointY - 2].equals("wall")){
                return true;
            }
        }
        if (pointY != labyrinthSize - 2){
            if (labyrinthObject[pointX][pointY + 2].equals("wall")){
                return true;
            }
        }
        if (pointX != 1){
            if (labyrinthObject[pointX - 2][pointY].equals("wall")){
                return true;
            }
        }
        if (pointX != labyrinthSize - 2){
            if (labyrinthObject[pointX + 2][pointY].equals("wall")){
                return true;
            }
        }
        return false;
    }

    private void setplayerAndGoal(){
        boolean player = true;
        boolean goal = true;
        Random rnd = new Random();
        while(player || goal){
            int x = rnd.nextInt(labyrinthSize - 2) + 1;
            int y = rnd.nextInt(labyrinthSize - 2) + 1;
            if (player){
                if (!labyrinthObject[x][y].equals("wall")){
                    labyrinthObject[x][y] = "player";
                    player = false;
                }
            }else {
                if (!labyrinthObject[x][y].equals("wall") && !labyrinthObject[x][y].equals("player")){
                    labyrinthObject[x][y] = "goal";
                    goal = false;
                }
            }
        }
    }

    public void show() {
        File file = new File(dataFolder,"labyrinth.txt");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new BufferedWriter(new FileWriter(file, false)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int y = 0; y < labyrinthSize; y++) {
            pw.println("");
            for (int x = 0; x < labyrinthSize; x++) {
                switch (labyrinthObject[x][y]) {
                    case "wall":
                        pw.print("##");
                        break;
                    case "player":
                        pw.print("PP");
                        break;
                    case "goal":
                        pw.print("GG");
                        break;
                    default:
                        pw.print("  ");
                        break;
                }
            }
        }
        pw.close();
    }

    public Boolean checkWall(int i,int j){
        return labyrinthObject[i][j].equals("wall");
    }

    public int[] getplayerPoint() {
        for (int i = 0;i < labyrinthSize;i++){
            for (int j = 0;j < labyrinthSize;j++){
                if (labyrinthObject[i][j].equals("player")){
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

//    public int[] getGoalPoint() {
//        for (int i = 0;i < labyrinthSize;i++){
//            for (int j = 0;j < labyrinthSize;j++){
//                if (labyrinthObject[i][j].equals("goal")){
//                    return new int[]{i, j};
//                }
//            }
//        }
//        return null;
//    }
}
