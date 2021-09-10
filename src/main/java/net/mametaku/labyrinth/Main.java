package net.mametaku.labyrinth;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Main extends JavaPlugin {

    public static Config config;
    public static HashMap<UUID, LabyrinthSystem> labyrinthgame=new HashMap<>();
    public static List<UUID> currentPlayer=new ArrayList<>();
    public static boolean playable;
    public static MySQLManager mySQLManager;
    public static String  title = "              §8§l[§5§lLabyrinth§8§l]";

    @Override
    public void onEnable() {
        config=new Config(this);
        playable=false;
        mySQLManager=new MySQLManager(this,"labyrinth");
        playable=config.getBoolean("canPlay");
        getCommand("labyrinth").setExecutor(new Commands());
        new EventList(this);

    }

    @Override
    public void onDisable() {

    }

}