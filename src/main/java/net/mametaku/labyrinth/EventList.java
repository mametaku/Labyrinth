package net.mametaku.labyrinth;

import net.mametaku.labyrinth.gamesystem.LabyrinthSystem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import static net.mametaku.labyrinth.Main.*;

public class EventList implements Listener {

    public EventList(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void ClickGUI(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getCurrentItem() == null) return;
        ItemStack clickedItem = e.getCurrentItem();
        if (e.getView().getTitle().equals(title)) {
            e.setCancelled(true);
            if (currentPlayer.contains(e.getWhoClicked().getUniqueId()) && clickedItem.getItemMeta() != null) {
                switch (clickedItem.getItemMeta().getDisplayName()) {
                    case "スタート":
                        labyrinthGameInventory.put(labyrinthGame.get(player.getUniqueId()), new InventoryGUI(54, title));
                        if (labyrinthGame.get(player.getUniqueId()).playerLocate.equals(LabyrinthSystem.PlayerLocate.DUNGEON)) {
                            new LabyrinthGUI().labyrinthMenu(player);
                            return;
                        }
                        new LabyrinthGUI().townMenu(player);
                        break;
                    case "ランキング":
                        new LabyrinthGUI().rankingMenu(player);
                        break;
                    case "進む": {
                        LabyrinthSystem labyrinth = labyrinthGame.get(player.getUniqueId());
                        if (labyrinth.playerLocate == LabyrinthSystem.PlayerLocate.TOWN) return;
                        labyrinth.updateViewDirection();
                        labyrinth.movePlayer(player);
                        break;
                    }
                    case "右に回転": {
                        LabyrinthSystem labyrinth = labyrinthGame.get(player.getUniqueId());
                        if (labyrinth.playerLocate == LabyrinthSystem.PlayerLocate.TOWN) return;
                        labyrinth.setPlayerViewDirection(LabyrinthSystem.SpinDirection.RIGHT, labyrinth.playerView);
                        labyrinth.updateViewDirection();
                        labyrinth.updateMap(player);
                        break;
                    }
                    case "左に回転": {
                        LabyrinthSystem labyrinth = labyrinthGame.get(player.getUniqueId());
                        if (labyrinth.playerLocate == LabyrinthSystem.PlayerLocate.TOWN) return;
                        labyrinth.setPlayerViewDirection(LabyrinthSystem.SpinDirection.LEFT, labyrinth.playerView);
                        labyrinth.updateViewDirection();
                        labyrinth.updateMap(player);
                        break;
                    }
                }
            }
        }
    }
}
