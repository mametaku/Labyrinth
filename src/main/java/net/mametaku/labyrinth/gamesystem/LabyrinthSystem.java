package net.mametaku.labyrinth.gamesystem;

import net.mametaku.labyrinth.InventoryGUI;
import net.mametaku.labyrinth.SkullMaker;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Random;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static net.mametaku.labyrinth.Main.*;
import static net.mametaku.labyrinth.gamesystem.LabyrinthSystem.MaterialType.*;

public class LabyrinthSystem {

    private static int pointX; //ブロックを置いたり消したりする目印。
    private static int pointY;
    public int labyrinthSize = config.getInt("labyrinthSize");
    public MaterialType[][] labyrinthObject = new MaterialType[labyrinthSize][labyrinthSize];
    public  ResearchFlag[][] labyrinthFlag = new ResearchFlag[labyrinthSize][labyrinthSize];
    public ViewDirection playerView;
    public PlayerLocate playerLocate;
    public int playerFloor;
    public boolean canMove;

    public enum PlayerLocate{
        TOWN,
        DUNGEON,
    }

    public enum MaterialType{
        EMPTY,
        FRONTEISEMPTY,
        FRONTEISGOAL,
        WALL,
        PLAYER,
        GOAL,
    }

    public enum ResearchFlag{
        RESEARCH,//調査
        NOT_RESEARCH,//未調査
    }

    public enum ViewDirection{
        NORTH,
        SOUTH,
        WEST,//西
        EAST,//東
    }

    public enum SpinDirection{
        RIGHT,
        LEFT,
    }

    public LabyrinthSystem() {
        if (labyrinthSize % 2 != 0 && 5 <= labyrinthSize) {
            for (int i = 0; i < labyrinthSize; i++) {
                for (int j = 0; j < labyrinthSize; j++) {
                    labyrinthObject[i][j] = MaterialType.WALL;
                    labyrinthFlag[i][j] = ResearchFlag.NOT_RESEARCH;
                }
            }
            pointX = randomPos();
            pointY = randomPos();
            labyrinthObject[pointX][pointY] = MaterialType.EMPTY;

            dig();
            setplayerAndGoal();
        } else {
            Bukkit.getLogger().info("縦・横共に5以上の奇数で作成してください。");
        }
    }

    private void dig() {
        if (labyrinthObject[pointX][pointY].equals(MaterialType.EMPTY) && isAbleContinueDig()){
            Random rnd = new Random();
            int direction;
            direction = rnd.nextInt(4);
            switch (direction){
                case 0:
                    if (pointY != 1) {
                        if (labyrinthObject[pointX][pointY - 2].equals(MaterialType.WALL)) {
                            labyrinthObject[pointX][pointY - 1] = MaterialType.EMPTY;
                            pointY -= 2;
                            //u
                        }
                    }
                case 1:
                    if (pointY != labyrinthSize - 2) {
                        if (labyrinthObject[pointX][pointY + 2].equals(MaterialType.WALL)) {
                            labyrinthObject[pointX][pointY + 1] = MaterialType.EMPTY;
                            pointY += 2;
                            //d
                        }
                    }
                case 2:
                    if (pointX != 1) {
                        if (labyrinthObject[pointX - 2][pointY].equals(MaterialType.WALL)) {
                            labyrinthObject[pointX - 1][pointY] = MaterialType.EMPTY;
                            pointX -= 2;
                            //l
                        }
                    }
                case 3:
                    if (pointX != labyrinthSize - 2) {
                        if (labyrinthObject[pointX + 2][pointY].equals(MaterialType.WALL)) {
                            labyrinthObject[pointX + 1][pointY] = MaterialType.EMPTY;
                            pointX += 2;
                            //r
                        }
                    }
            }
            labyrinthObject[pointX][pointY] = MaterialType.EMPTY;
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
                    if (!labyrinthObject[i][j].equals(MaterialType.EMPTY)){
                        cnt++;
                    }
                }
            }
        }
        return (cnt != 0);
    }

    private boolean isAbleContinueDig(){
        if (pointY != 1){
            if (labyrinthObject[pointX][pointY - 2].equals(MaterialType.WALL)){
                return true;
            }
        }
        if (pointY != labyrinthSize - 2){
            if (labyrinthObject[pointX][pointY + 2].equals(MaterialType.WALL)){
                return true;
            }
        }
        if (pointX != 1){
            if (labyrinthObject[pointX - 2][pointY].equals(MaterialType.WALL)){
                return true;
            }
        }
        if (pointX != labyrinthSize - 2){
            if (labyrinthObject[pointX + 2][pointY].equals(MaterialType.WALL)){
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
                if (!labyrinthObject[x][y].equals(MaterialType.WALL)){
                    labyrinthObject[x][y] = MaterialType.PLAYER;
                    player = false;
                }
            }else {
                if (!labyrinthObject[x][y].equals(MaterialType.WALL) && !labyrinthObject[x][y].equals(MaterialType.PLAYER)){
                    labyrinthObject[x][y] = MaterialType.GOAL;
                    goal = false;
                }
            }
        }
        canMove = true;
    }

    public void setPlayerViewDirection(SpinDirection spin, ViewDirection view){
        if (spin == SpinDirection.RIGHT){
            switch (view){
                case NORTH:
                    playerView = ViewDirection.EAST;
                    break;
                case EAST:
                    playerView = ViewDirection.SOUTH;
                    break;
                case SOUTH:
                    playerView = ViewDirection.WEST;
                    break;
                case WEST:
                    playerView = ViewDirection.NORTH;
                    break;
            }
        }else {
            switch (view){
                case NORTH:
                    playerView = ViewDirection.WEST;
                    break;
                case WEST:
                    playerView = ViewDirection.SOUTH;
                    break;
                case SOUTH:
                    playerView = ViewDirection.EAST;
                    break;
                case EAST:
                    playerView = ViewDirection.NORTH;
                    break;
            }
        }
    }

    public void updateMap(Player player){
        LabyrinthSystem labyrinth = labyrinthGame.get(player.getUniqueId());
        InventoryGUI inv = labyrinthGameInventory.get(labyrinth);
        LabyrinthSystem.MaterialType[][] map = labyrinth.labyrinthObject;
        ViewDirection dir = playerView;
        for (int i = 0;i < labyrinth.labyrinthSize;i++){
            for (int j = 0;j < labyrinth.labyrinthSize;j++) {
                if (map[i][j] == FRONTEISEMPTY){
                    map[i][j] = EMPTY;
                    break;
                }
                if (map[i][j] == FRONTEISGOAL){
                    map[i][j] = GOAL;
                    break;
                }
            }
        }
        int[] playerPoint = labyrinth.getPlayerPoint();
        researchMap(playerPoint);
        for (int i = 0;i < labyrinth.labyrinthSize;i++){
            for (int j = 0;j < labyrinth.labyrinthSize;j++) {
                replaceObjectInFrontOf(map, i, j, dir);
            }
        }
        drawMap(playerPoint,map,labyrinth,inv);
    }

    public void movePlayer(Player player){
        LabyrinthSystem labyrinth = labyrinthGame.get(player.getUniqueId());
        InventoryGUI inv = labyrinthGameInventory.get(labyrinth);
        MaterialType[][] map = labyrinth.labyrinthObject;
        ViewDirection view = labyrinth.playerView;
        int[] playerPoint = new int[2];
        LOOP:
        for (int i = 0;i < labyrinth.labyrinthSize;i++){
            for (int j = 0;j < labyrinth.labyrinthSize;j++){
                if (map[i][j].equals(PLAYER)){
                    switch (view){
                        case NORTH:
                            if (!labyrinth.checkWall(i, j - 1)){
                                if (labyrinth.checkGoal(i, j - 1)){
                                    labyrinthObject[i][j] = EMPTY;
                                    labyrinthObject[i][j - 1] = PLAYER;
                                    drawMap(playerPoint,map,labyrinth,inv);
                                    canMove = false;
                                    nextFloor(inv,player);
                                    return;
                                }
                                labyrinthObject[i][j] = EMPTY;
                                labyrinthObject[i][j - 1] = PLAYER;
                                playerPoint = new int[]{i, j - 1};
                                researchMap(playerPoint);
                                break LOOP;
                            }
                            playerPoint = new int[]{i, j};
                            researchMap(playerPoint);
                            break LOOP;
                        case SOUTH:
                            if (!labyrinth.checkWall(i, j + 1)){
                                Bukkit.broadcastMessage(String.valueOf(labyrinthObject[i][j + 1]));
                                if (labyrinth.checkGoal(i, j + 1)){
                                    labyrinthObject[i][j] = EMPTY;
                                    labyrinthObject[i][j + 1] = PLAYER;
                                    playerPoint = new int[]{i, j + 1};
                                    researchMap(playerPoint);
                                    drawMap(playerPoint,map,labyrinth,inv);
                                    canMove = false;
                                    nextFloor(inv,player);
                                    return;
                                }
                                labyrinthObject[i][j] = EMPTY;
                                labyrinthObject[i][j + 1] = PLAYER;
                                playerPoint = new int[]{i, j + 1};
                                researchMap(playerPoint);
                                break LOOP;
                            }
                            playerPoint = new int[]{i, j};
                            researchMap(playerPoint);
                            break LOOP;
                        case WEST:
                            if (!labyrinth.checkWall(i - 1, j)){
                                Bukkit.broadcastMessage(String.valueOf(labyrinthObject[i - 1][j]));
                                if (labyrinth.checkGoal(i - 1, j)){
                                    labyrinthObject[i][j] = EMPTY;
                                    labyrinthObject[i - 1][j] = PLAYER;
                                    playerPoint = new int[]{i - 1, j};
                                    researchMap(playerPoint);
                                    drawMap(playerPoint,map,labyrinth,inv);
                                    canMove = false;
                                    nextFloor(inv,player);
                                    return;
                                }
                                labyrinthObject[i][j] = EMPTY;
                                labyrinthObject[i - 1][j] = PLAYER;
                                playerPoint = new int[]{i - 1, j};
                                researchMap(playerPoint);
                                break LOOP;
                            }
                            playerPoint = new int[]{i, j};
                            researchMap(playerPoint);
                            break LOOP;
                        case EAST:
                            if (!labyrinth.checkWall(i + 1, j)){
                                Bukkit.broadcastMessage(String.valueOf(labyrinthObject[i + 1][j]));
                                if (labyrinth.checkGoal(i + 1, j)){
                                    labyrinthObject[i][j] = EMPTY;
                                    labyrinthObject[i + 1][j] = PLAYER;
                                    playerPoint = new int[]{i + 1, j };
                                    researchMap(playerPoint);
                                    drawMap(playerPoint,map,labyrinth,inv);
                                    canMove = false;
                                    nextFloor(inv,player);
                                    return;
                                }
                                labyrinthObject[i][j] = EMPTY;
                                labyrinthObject[i + 1][j] = PLAYER;
                                playerPoint = new int[]{i + 1, j };
                                researchMap(playerPoint);
                                break LOOP;
                            }
                            playerPoint = new int[]{i, j};
                            researchMap(playerPoint);
                        break LOOP;
                    }
                }
            }
            drawMap(playerPoint,map,labyrinth,inv);
        }
        updateMap(player);
        labyrinthGameInventory.put(labyrinth,inv);
    }

    public void drawMap(int[] playerPoint,MaterialType[][] map,LabyrinthSystem labyrinth,InventoryGUI inv){
        int a = playerPoint[0];
        int b = playerPoint[1];
        int x = min(max(0,a-2), labyrinthSize - 6);
        int y = min(max(0,b-2), labyrinthSize - 6);
        ResearchFlag[][] rf = labyrinth.labyrinthFlag;
        for(int ix=0;ix<6;ix++){
            for(int iy=0;iy<6;iy++){
                switch (map[x+ix][y+iy]){
                    case WALL:
                        inv.setItem(ix+9*iy+3,Material.DEEPSLATE_BRICKS," ");
                        if (rf[x+ix][y+iy].equals(ResearchFlag.NOT_RESEARCH)){
                            inv.setItem(ix+9*iy+3,Material.PURPLE_STAINED_GLASS_PANE," ");
                        }
                        break;
                    case PLAYER:
                        inv.setItem(ix+9*iy+3,Material.PLAYER_HEAD,"PLAYER");
                        break;
                    case GOAL:
                        inv.setItem(ix+9*iy+3,Material.BELL,"GOAL");
                        if (rf[x+ix][y+iy].equals(ResearchFlag.NOT_RESEARCH)){
                            inv.setItem(ix+9*iy+3,Material.PURPLE_STAINED_GLASS_PANE," ");
                        }
                        break;
                    case EMPTY:
                        inv.setItem(ix+9*iy+3,Material.BLACK_STAINED_GLASS_PANE," ");
                        if (rf[x+ix][y+iy].equals(ResearchFlag.NOT_RESEARCH)){
                            inv.setItem(ix+9*iy+3,Material.PURPLE_STAINED_GLASS_PANE," ");
                        }
                        break;
                    case FRONTEISEMPTY:
                        inv.setItem(ix+9*iy+3,Material.BLACK_STAINED_GLASS_PANE," ");
                        if (rf[x+ix][y+iy].equals(ResearchFlag.NOT_RESEARCH)){
                            inv.setItem(ix+9*iy+3,Material.PURPLE_STAINED_GLASS_PANE," ");
                        }
                        inv.enchantItem(ix+9*iy+3);
                        break;
                    case FRONTEISGOAL:
                        inv.setItem(ix+9*iy+3,Material.BELL," ");
                        if (rf[x+ix][y+iy].equals(ResearchFlag.NOT_RESEARCH)){
                            inv.setItem(ix+9*iy+3,Material.PURPLE_STAINED_GLASS_PANE," ");
                        }
                        inv.enchantItem(ix+9*iy+3);
                        break;
                }
            }
        }
    }

    private void nextFloor(InventoryGUI inv,Player player){
        BukkitScheduler scheduler = Bukkit.getScheduler();
        for(int ix=0;ix<6;ix++){
            for(int iy=0;iy<6;iy++){
                int finalIx = ix;
                int finalIy = iy;
                scheduler.runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_DEEPSLATE_BREAK,1f,1f);
                        inv.setItem(finalIx +9* finalIy +3,Material.RED_STAINED_GLASS_PANE," ");
                        if (finalIx +9* finalIy +3 == 53){
                            return;
                        }
                    }
                },4 * (finalIx + finalIy));
            }
        }
        scheduler.runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                playerFloor++;
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE,1f,1f);
                ItemStack floorNumber = new SkullMaker().withSkinUrl("http://textures.minecraft.net/texture/caf1b280cab59f4469dab9f1a2af7927ed96a81df1e24d50a8e3984abfe4044").build();
                ItemStack f = new SkullMaker().withSkinUrl("http://textures.minecraft.net/texture/633c89a3c529d5136be6c49a62be0383fc3722cc990142e5cb3cc96db199d7d").build();
                inv.setItem(14,floorNumber);
                inv.setItem(15,f);
            }
        },40L);
        scheduler.runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                if (labyrinthSize % 2 != 0 && 5 <= labyrinthSize) {
                    for (int i = 0; i < labyrinthSize; i++) {
                        for (int j = 0; j < labyrinthSize; j++) {
                            labyrinthObject[i][j] = MaterialType.WALL;
                            labyrinthFlag[i][j] = ResearchFlag.NOT_RESEARCH;
                        }
                    }
                    pointX = randomPos();
                    pointY = randomPos();
                    labyrinthObject[pointX][pointY] = MaterialType.EMPTY;

                    dig();
                    setplayerAndGoal();
                } else {
                    Bukkit.getLogger().info("縦・横共に5以上の奇数で作成してください。");
                }
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,1f,1f);
                playerView = LabyrinthSystem.ViewDirection.NORTH;
                updateMap(player);
            }
        },80L);
    }

    private void replaceObjectInFrontOf(MaterialType[][] map, int i, int j, ViewDirection playerView) {
        if (map[i][j].equals(MaterialType.PLAYER)){
            switch (playerView){
                case NORTH:
                    if (map[i][j - 1].equals(GOAL)){
                        map[i][j - 1] = FRONTEISGOAL;
                        break;
                    }
                    if (!map[i][j - 1].equals(WALL)){
                        map[i][j - 1] = FRONTEISEMPTY;
                        break;
                    }
                    break;
                case SOUTH:
                    if (map[i][j + 1].equals(GOAL)){
                        map[i][j + 1] = FRONTEISGOAL;
                        break;
                    }
                    if (!map[i][j + 1].equals(WALL)){
                        map[i][j + 1] = FRONTEISEMPTY;
                        break;
                    }
                    break;
                case WEST:
                    if (map[i - 1][j].equals(GOAL)){
                        map[i - 1][j] = FRONTEISGOAL;
                        break;
                    }
                    if (!map[i - 1][j].equals(WALL)){
                        map[i - 1][j] = FRONTEISEMPTY;
                        break;
                    }
                    break;
                case EAST:
                    if (map[i + 1][j].equals(GOAL)){
                        map[i + 1][j] = FRONTEISGOAL;
                        break;
                    }
                    if (!map[i + 1][j].equals(WALL)){
                        map[i + 1][j] = FRONTEISEMPTY;
                        break;
                    }
                    break;
            }
        }
    }

    private Boolean checkGoal(int i,int j){
        return labyrinthObject[i][j].equals(FRONTEISGOAL);
    }

    private Boolean checkWall(int i,int j){
        return labyrinthObject[i][j].equals(WALL);
    }

    private void researchMap(int[] playerPoint){
        int a = playerPoint[0];
        int b = playerPoint[1];
        ResearchFlag[][] rf = labyrinthFlag;
        for (int i = -1;i <= 1;i++){
            for (int j = -1;j <= 1;j++){
                rf[a + i][b + j] = ResearchFlag.RESEARCH;
            }
        }
    }

    public int[] getPlayerPoint() {
        for (int i = 0;i < labyrinthSize;i++){
            for (int j = 0;j < labyrinthSize;j++){
                if (labyrinthObject[i][j].equals(MaterialType.PLAYER)){
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    public int[] getGoalPoint() {
        for (int i = 0;i < labyrinthSize;i++){
            for (int j = 0;j < labyrinthSize;j++){
                if (labyrinthObject[i][j].equals(MaterialType.GOAL)){
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }
}
