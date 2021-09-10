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
    private Stack<Integer> pointXStack = new Stack<Integer>();
    private Stack<Integer> pointYStack = new Stack<Integer>();
    private int labyrinthSize = 21;
    private String[][] labyrinthObject = new String[labyrinthSize][labyrinthSize];

    LabyrinthSystem() throws IOException {
        for (int i = 0; i < labyrinthSize; i++) {
            for (int j = 0; j < labyrinthSize; j++) {
                labyrinthObject[i][j] = "wall";
            }
        }

        Random rnd = new Random();
        pointX = rnd.nextInt((labyrinthSize / 2) * 2 - 1);
        pointY = rnd.nextInt((labyrinthSize / 2) * 2 - 1);
        labyrinthObject[pointX][pointY] = "empty";
        pointXStack.push(pointX);
        pointYStack.push(pointY);

        dig();
        show();
    }

    private void dig(){
        if (labyrinthObject[pointX][pointY] != null && labyrinthObject[pointX][pointY].equals("empty") && isAbleContinueDig()){
            Random rnd = new Random();
            int direction;
            direction = rnd.nextInt(4);
            switch (direction){
                case 0:
                    if (pointY != 1) {
                        if (labyrinthObject[pointX][pointY - 2].equals("wall")) {
                            labyrinthObject[pointX][pointY - 1] = "empty";
                            pointY -= 2;
                            break;//u
                        }
                    }
                    break;
                case 1:
                    if (pointY != labyrinthSize - 2) {
                        if (labyrinthObject[pointX][pointY + 2].equals("wall")) {
                            labyrinthObject[pointX][pointY + 1] = "empty";
                            pointY += 2;
                            break;//d
                        }
                    }
                    break;
                case 2:
                    if (pointX != 1) {
                        if (labyrinthObject[pointX - 2][pointY].equals("wall")) {
                            labyrinthObject[pointX - 1][pointY] = "empty";
                            pointX -= 2;
                            break;//l
                        }
                    }
                    break;
                case 3:
                    if (pointX != labyrinthSize - 2) {
                        if (labyrinthObject[pointX + 2][pointY].equals("wall")) {
                            labyrinthObject[pointX + 1][pointY] = "empty";
                            pointX += 2;
                            break;//r
                        }
                    }
                    labyrinthObject[pointX][pointY] = "empty";
                    dig();
            }
        }else if (isAbleDig()){
            pointX = randomPos(labyrinthSize);
            pointY = randomPos(labyrinthSize);
            dig();
        }
    }

    int randomPos(int muki) { //x,y座標共に奇数なランダムな座標を返す
        int result = 1 + 2 * (int) Math.floor((Math.random() * (muki - 1)) / 2);
        return result;
    }

    private boolean isAbleDig() { //まだ掘るところがあるか確かめる
        boolean result;
        int cnt = 0;
        for (int y = 0; y < labyrinthSize; y++) {
            for (int x = 0; x < labyrinthSize; x++) {
                if (x % 2 != 0 && y % 2 != 0) {

                    if (!labyrinthObject[x][y].equals("empty")) {
                        cnt++;
                    }
                }
            }
        }
        if (cnt == 0) {
            result = false;
        } else {
            result = true;
        }
        return result;
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
