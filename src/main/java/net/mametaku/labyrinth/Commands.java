package net.mametaku.labyrinth;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

import static net.mametaku.labyrinth.Main.currentPlayer;

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
                    try {
                        new LabyrinthSystem();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        }
        currentPlayer.add(player.getUniqueId());
        new LabyrinthGUI().startMenu(player);
        return false;
    }
}
