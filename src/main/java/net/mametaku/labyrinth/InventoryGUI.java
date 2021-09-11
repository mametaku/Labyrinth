package net.mametaku.labyrinth;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InventoryGUI {

    Inventory inv;

    public InventoryGUI(int i, String name){
        inv=Bukkit.createInventory(null, i,name);
    }

    public void clear(){
        inv.clear();
    }

    public void openInventory(Player player){
        player.openInventory(inv);
    }

    public void setItem(int slot,ItemStack item){
        inv.setItem(slot,item);
    }

    public void setItem(int slot,Material material,String name,String...lore){
        this.setItem(slot,createGuiItem(material,name,lore));
    }

    public void setItem(int slot,Material material,String name,int cmd,String...lore){
        this.setItem(slot,createGuiItem(material,name,cmd,lore));
    }

    public void setItem(int slot,int amount,Material material,String name,String...lore){
        this.setItem(slot,createGuiItem(amount,material,name,lore));
    }

    public void removeItem(int slot){
        inv.setItem(slot,new ItemStack(Material.STONE,0));
    }

    public void cloneItem(int slot,int clonedSlot){
        inv.setItem(slot,inv.getItem(clonedSlot));
    }

    public void enchantItem(int slot){
        ItemStack item=inv.getItem(slot);
        if(item==null)return;
        item.addUnsafeEnchantment(Enchantment.LUCK,1);
        final ItemMeta itemMeta = item.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(itemMeta);
        inv.setItem(slot,item);
    }

    protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    protected ItemStack createGuiItem(final Material material, final String name, final int cmd, final String... lore) {
        final ItemStack item = new ItemStack(material,1);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));

        meta.setCustomModelData(cmd);

        item.setItemMeta(meta);

        return item;
    }

    protected ItemStack createGuiItem(int amount,final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, amount);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

}