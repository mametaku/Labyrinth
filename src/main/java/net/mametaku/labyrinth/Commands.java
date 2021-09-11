package net.mametaku.labyrinth;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

import static net.mametaku.labyrinth.Main.*;
import static net.mametaku.labyrinth.Main.title;

public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("プレイヤー以外は実行できません");
            return true;
        }
        Player player = (Player) sender;
        if (args.length != 0) {
            switch (args[0]) {
                case "help":
                    player.sendMessage("テスト");
                return false;
                case  "create":
                    new LabyrinthSystem();
            }
        }
        currentPlayer.add(player.getUniqueId());
        labyrinthGame.put(player.getUniqueId(),new LabyrinthSystem());
        labyrinthGameInventory.put(labyrinthGame.get(player.getUniqueId()),new InventoryGUI(27,title));
        new LabyrinthGUI().startMenu(player);
        return false;
    }
}
