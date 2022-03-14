package net.mametaku.labyrinth;

import net.mametaku.labyrinth.gamesystem.LabyrinthSystem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

import static net.mametaku.labyrinth.Main.*;

public class LabyrinthGUI {



    public void startMenu(Player player){
        LabyrinthSystem labyrinth = labyrinthGame.get(player.getUniqueId());
        InventoryGUI inv = labyrinthGameInventory.get(labyrinth);
        if (labyrinth.playerLocate == null){
            labyrinth.playerLocate = LabyrinthSystem.PlayerLocate.DUNGEON;
        }
        inv.setItem(10,Material.GREEN_CONCRETE,"スタート","ここをクリックで始まります！");
        inv.setItem(12,Material.BOOK,"ゲーム説明","テスト","テスト","テスト");
        inv.setItem(14,Material.SPRUCE_SAPLING,"ランキング",config.getInt("Rankingiconcmd"),"ここをクリックでランキングを表示！");
        inv.setItem(16,Material.RED_CONCRETE,"ゲーム終了","ここをクリックで画面を閉じます！");
        inv.openInventory(player);
    }

    public void rankingMenu(Player player){
        LabyrinthSystem labyrinth = labyrinthGame.get(player.getUniqueId());
        InventoryGUI inv = labyrinthGameInventory.get(labyrinth);
        for (int i=46;i<53;i++){
            inv.setItem(i,Material.RED_STAINED_GLASS_PANE," ");
        }
        ItemStack rightArrow = new SkullMaker().withSkinUrl("http://textures.minecraft.net/texture/19bf3292e126a105b54eba713aa1b152d541a1d8938829c56364d178ed22bf").build();
        ItemStack leftArrow = new SkullMaker().withSkinUrl("http://textures.minecraft.net/texture/bd69e06e5dadfd84e5f3d1c21063f2553b2fa945ee1d4d7152fdc5425bc12a9").build();
        inv.setItem(45,leftArrow);
        inv.setItem(53,rightArrow);
        inv.openInventory(player);
    }

    public void itemMenu(Player player){
        LabyrinthSystem labyrinth = labyrinthGame.get(player.getUniqueId());
        InventoryGUI inv = labyrinthGameInventory.get(labyrinth);
        for (int i=45;i<54;i++){
            inv.setItem(i,Material.PURPLE_STAINED_GLASS_PANE,"");
        }
        inv.openInventory(player);
    }


    public void labyrinthMenu(Player player){
        LabyrinthSystem labyrinth = labyrinthGame.get(player.getUniqueId());
        InventoryGUI inv;
        labyrinth.playerView = LabyrinthSystem.ViewDirection.NORTH;
        labyrinth.playerLocate = LabyrinthSystem.PlayerLocate.DUNGEON;
        labyrinth.updateMap(player);
        inv = getUI(player);
        inv.openInventory(player);
    }

    public void townMenu(Player player){
        LabyrinthSystem labyrinth = labyrinthGame.get(player.getUniqueId());
        InventoryGUI inv;
        labyrinth.playerLocate = LabyrinthSystem.PlayerLocate.TOWN;
        inv = getUI(player);
        for(int ix=0;ix<6;ix++){
            for(int iy=0;iy<6;iy++){
                inv.setItem(ix+9*iy+3,Material.matchMaterial(config.getString("Material"))," ",config.getInt("Treeiconcmd"));
            }
        }
        inv.setItem(14,Material.matchMaterial(config.getString("Material")),"ダンジョン",config.getInt("Dungeoniconcmd"));
        inv.setItem(15,Material.matchMaterial(config.getString("Material")),"宿屋",config.getInt("Inniconcmd"));
        inv.setItem(22,Material.matchMaterial(config.getString("Material")),"武器・防具屋",config.getInt("Weaponshopiconcmd"));
        inv.setItem(23,Material.matchMaterial(config.getString("Material"))," ",config.getInt("Groundiconcmd"));
        inv.setItem(24,Material.matchMaterial(config.getString("Material"))," ",config.getInt("Groundiconcmd"));
        inv.setItem(25,Material.matchMaterial(config.getString("Material")),"アイテム屋",config.getInt("Itemshopiconcmd"));
        inv.setItem(31,Material.matchMaterial(config.getString("Material"))," ",config.getInt("Groundiconcmd"));
        inv.setItem(32,Material.matchMaterial(config.getString("Material"))," ",config.getInt("Welliconcmd"));
        inv.setItem(33,Material.matchMaterial(config.getString("Material"))," ",config.getInt("Groundiconcmd"));
        inv.setItem(34,Material.matchMaterial(config.getString("Material"))," ",config.getInt("Groundiconcmd"));
        inv.setItem(41,Material.matchMaterial(config.getString("Material"))," ",config.getInt("Groundiconcmd"));
        inv.setItem(42,Material.matchMaterial(config.getString("Material"))," ",config.getInt("Groundiconcmd"));
        inv.openInventory(player);
    }

    private InventoryGUI getUI(Player player){
        LabyrinthSystem labyrinth = labyrinthGame.get(player.getUniqueId());
        InventoryGUI inv = labyrinthGameInventory.get(labyrinth);
        inv.setItem(1,Material.matchMaterial(config.getString("Material"))," ",1);
        inv.setItem(27,Material.matchMaterial(config.getString("Material")),"左に回転",config.getInt("Leftspinarrowcmd"));
        inv.setItem(28,Material.matchMaterial(config.getString("Material")),"進む",config.getInt("Uparrowcmd"));
        inv.setItem(29,Material.matchMaterial(config.getString("Material")),"右に回転",config.getInt("Rightspinarrowcmd"));
        inv.setItem(36,Material.matchMaterial(config.getString("Material")),"持ち物",config.getInt("itembagiconcmd"));
        inv.setItem(37,Material.matchMaterial(config.getString("Material")),"決定・攻撃",config.getInt("Selectandattackiconcmd"));
        inv.setItem(38,Material.matchMaterial(config.getString("Material")),"ログ",config.getInt("Logiconcmd"));
        inv.setItem(45,Material.matchMaterial(config.getString("Material")),"情報",config.getInt("Playerstatusiconcmd"));
        inv.setItem(46,Material.matchMaterial(config.getString("Material")),"ヘルプ",config.getInt("Helpiconcmd"));
        inv.setItem(47,Material.matchMaterial(config.getString("Material")),"設定",config.getInt("Settungiconcmd"));
        return inv;
    }
}
