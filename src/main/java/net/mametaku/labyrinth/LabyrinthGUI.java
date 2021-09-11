package net.mametaku.labyrinth;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static net.mametaku.labyrinth.LabyrinthSystem.MaterialType.*;
import static net.mametaku.labyrinth.Main.*;

public class LabyrinthGUI {



    public void startMenu(Player player){
        LabyrinthSystem labyrinth = labyrinthGame.get(player.getUniqueId());
        InventoryGUI inv = labyrinthGameInventory.get(labyrinth);
        inv.setItem(10,Material.GREEN_CONCRETE,"スタート","ここをクリックで始まります！");
        inv.setItem(12,Material.BOOK,"ゲーム説明","テスト","テスト","テスト");
        inv.setItem(14,Material.SPRUCE_SAPLING,"ランキング",1000,"ここをクリックでランキングを表示！");
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

    public void gameMenu(Player player){
        LabyrinthSystem labyrinth = labyrinthGame.get(player.getUniqueId());
        InventoryGUI inv = labyrinthGameInventory.get(labyrinth);
        labyrinth.playerView = LabyrinthSystem.ViewDirection.NORTH;
        labyrinth.updateMap(player);
        inv.setItem(1,Material.SPRUCE_SAPLING," ",1001);
        inv.setItem(27,Material.SPRUCE_SAPLING,"左に回転",1002);
        inv.setItem(28,Material.SPRUCE_SAPLING,"上",1003);
        inv.setItem(29,Material.SPRUCE_SAPLING,"右に回転",1004);
        inv.setItem(37,Material.END_CRYSTAL,"決定・攻撃",1006);
        inv.setItem(45,Material.SPRUCE_SAPLING,"情報",1008);
        inv.setItem(47,Material.SPRUCE_SAPLING,"ログ",1010);
        inv.openInventory(player);
    }
}
