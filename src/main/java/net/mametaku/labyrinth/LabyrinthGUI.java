package net.mametaku.labyrinth;

import net.mametaku.labyrinth.gamesystem.LabyrinthSystem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static net.mametaku.labyrinth.Main.*;

public class LabyrinthGUI {



    public void startMenu(Player player){
        LabyrinthSystem labyrinth = labyrinthGame.get(player.getUniqueId());
        InventoryGUI inv = labyrinthGameInventory.get(labyrinth);
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
            inv.setItem(i,Material.RED_STAINED_GLASS_PANE,"");
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


    public void gameMenu(Player player){
        LabyrinthSystem labyrinth = labyrinthGame.get(player.getUniqueId());
        InventoryGUI inv = labyrinthGameInventory.get(labyrinth);
        labyrinth.playerView = LabyrinthSystem.ViewDirection.NORTH;
        labyrinth.updateMap(player);
        inv.setItem(1,Material.matchMaterial(config.getString("Material"))," ",1);
        inv.setItem(27,Material.matchMaterial(config.getString("Material")),"左に回転",config.getInt("Leftspinarrowcmd"));
        inv.setItem(28,Material.matchMaterial(config.getString("Material")),"上",config.getInt("Uparrowcmd"));
        inv.setItem(29,Material.matchMaterial(config.getString("Material")),"右に回転",config.getInt("Rightspinarrowcmd"));
        inv.setItem(36,Material.matchMaterial(config.getString("Material")),"持ち物",config.getInt("itembagiconcmd"));
        inv.setItem(37,Material.matchMaterial(config.getString("Material")),"決定・攻撃",config.getInt("Selectandattackiconcmd"));
        inv.setItem(38,Material.matchMaterial(config.getString("Material")),"ログ",config.getInt("Logiconcmd"));
        inv.setItem(45,Material.matchMaterial(config.getString("Material")),"情報",config.getInt("Playerstatusiconcmd"));
        inv.setItem(46,Material.matchMaterial(config.getString("Material")),"トロフィー",config.getInt("Trophyiconcmd"));
        inv.setItem(47,Material.matchMaterial(config.getString("Material")),"設定",config.getInt("Settungiconcmd"));
        inv.openInventory(player);
    }
}
