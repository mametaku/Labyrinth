package net.mametaku.labyrinth;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.util.Random;
import java.util.Stack;

import static net.mametaku.labyrinth.Main.dataFolder;

public class LabyrinthSystem {

    private int pointX; //ブロックを置いたり消したりする目印。
    private int pointY;
    private int labyrinthSize = 5;
    private String[][] labyrinthObject = new String[labyrinthSize][labyrinthSize];

    LabyrinthSystem() throws IOException {
        for (int i = 0; i < labyrinthSize; i++) {
            for (int j = 0; j < labyrinthSize; j++) {
                labyrinthObject[i][j] = "wall";
            }
        }

        pointX = randomPos(labyrinthSize);
        pointY = randomPos(labyrinthSize);
        labyrinthObject[pointX][pointY] = "empty";

        dig();
        show();
    }

    private void dig() throws IOException {
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
                            Bukkit.broadcastMessage("0");
                            break;//u
                        }
                    }
                case 1:
                    if (pointY != labyrinthSize - 2) {
                        if (labyrinthObject[pointX][pointY + 2].equals("wall")) {
                            labyrinthObject[pointX][pointY + 1] = "empty";
                            pointY += 2;
                            Bukkit.broadcastMessage("1");
                            break;//d
                        }
                    }
                case 2:
                    if (pointX != 1) {
                        if (labyrinthObject[pointX - 2][pointY].equals("wall")) {
                            labyrinthObject[pointX - 1][pointY] = "empty";
                            pointX -= 2;
                            Bukkit.broadcastMessage("2");
                            break;//l
                        }
                    }
                case 3:
                    if (pointX != labyrinthSize - 2) {
                        if (labyrinthObject[pointX + 2][pointY].equals("wall")) {
                            labyrinthObject[pointX + 1][pointY] = "empty";
                            pointX += 2;
                            Bukkit.broadcastMessage("3");
                            break;//r
                        }
                    }
                    labyrinthObject[pointX][pointY] = "empty";
                    show();
                    dig();
            }
        }else if (isAbleDig()){
            pointX = randomPos(labyrinthSize);
            pointY = randomPos(labyrinthSize);
            show();
            dig();
        }
    }

    int randomPos(int muki) { //x,y座標共に奇数なランダムな座標を返す
        int result = 1 + 2 * (int) Math.floor((Math.random() * (muki - 1)) / 2);
        return result;
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

    public void show() throws IOException {
        File file = new File(dataFolder,"labyrinth.txt");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
        for (int y = 0; y < labyrinthObject[0].length; y++) {
            pw.println("");
            for (int x = 0; x < labyrinthObject.length; x++) {
                if (labyrinthObject[x][y].equals("wall")) {
                    pw.print("##");
                } else {
                    pw.print("  ");
                }
            }
        }
        pw.close();
    }
}
