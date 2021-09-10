package net.mametaku.labyrinth;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import static net.mametaku.labyrinth.Main.currentPlayer;
import static net.mametaku.labyrinth.Main.title;

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
                    new LabyrinthGUI().gameMenu(player);
                } else if(matchName(clickedItem,"ランキング")){
                    new LabyrinthGUI().rankingMenu(player);
                }
            }
        }
    }
}
