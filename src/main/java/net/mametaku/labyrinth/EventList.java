package net.mametaku.labyrinth;

import net.mametaku.labyrinth.gamesystem.LabyrinthSystem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.ShulkerBullet;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.InvocationTargetException;

import static net.mametaku.labyrinth.Main.*;

public class EventList implements Listener {

    public EventList(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    private boolean matchName(ItemStack item, String name) {
        return item.getItemMeta() != null && item.getItemMeta().getDisplayName().equals(name);
    }

    @EventHandler
    public void ClickGUI(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getCurrentItem() == null) return;
        ItemStack clickedItem = e.getCurrentItem();
        if (e.getView().getTitle().equals(title)) {
            e.setCancelled(true);
            if (currentPlayer.contains(e.getWhoClicked().getUniqueId())) {
                int slot = e.getSlot();
                if (matchName(clickedItem, "スタート")) {
                    labyrinthGameInventory.put(labyrinthGame.get(player.getUniqueId()),new InventoryGUI(54,title));
                    new LabyrinthGUI().gameMenu(player);
                } else if(matchName(clickedItem,"ランキング")){
                    new LabyrinthGUI().rankingMenu(player);
                } else if (matchName(clickedItem,"上")){
                    LabyrinthSystem labyrinth = labyrinthGame.get(player.getUniqueId());
                    labyrinth.updateViewDirection();
                    labyrinth.movePlayer(player);
                }else if (matchName(clickedItem,"右に回転")){
                    LabyrinthSystem labyrinth = labyrinthGame.get(player.getUniqueId());
                    labyrinth.setPlayerViewDirection(LabyrinthSystem.SpinDirection.RIGHT,labyrinth.playerView);
                    labyrinth.updateViewDirection();
                    labyrinth.updateMap(player);
                } else if (matchName(clickedItem,"左に回転")){
                    LabyrinthSystem labyrinth = labyrinthGame.get(player.getUniqueId());
                    labyrinth.setPlayerViewDirection(LabyrinthSystem.SpinDirection.LEFT,labyrinth.playerView);
                    labyrinth.updateViewDirection();
                    labyrinth.updateMap(player);
                } else if (matchName(clickedItem,"トロフィー")){
                }
            }
        }
    }
}
