package fr.matt.arkadia;

import fr.matt.arkadia.command.*;
import fr.matt.arkadia.event.*;
import fr.matt.arkadia.manager.ClassementManager;
import fr.matt.arkadia.manager.TeamManager;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import static org.bukkit.Bukkit.getServer;

public final class Arkadia extends JavaPlugin {

    @Override
    public void onEnable() {

        System.out.println("Le plugin Arkadia est ON !");

        getServer().getPluginManager().registerEvents(new ChatEvent(), this);

        TeamManager teamManager = new TeamManager();
        TeamCommand teamCommand = new TeamCommand(teamManager);

        getCommand("t").setExecutor(teamCommand);
        getCommand("spawn").setExecutor(teamCommand);
        getCommand("team").setExecutor(teamCommand);
        getCommand("base").setExecutor(teamCommand);
        getCommand("setbase").setExecutor(teamCommand);

        getServer().getPluginManager().registerEvents(new TeamEvent(teamManager), this);

        ClassementManager classementManager = new ClassementManager();
        getCommand("quest").setExecutor(new QuestCommand(classementManager));

        getServer().getPluginManager().registerEvents(new BlockBreakEvent(classementManager), this);
        getServer().getPluginManager().registerEvents(new EntityKillEvent(classementManager), this);
        getServer().getPluginManager().registerEvents(new ConnectionEvent(classementManager), this);

        ClassementCommand classementCommand = new ClassementCommand(classementManager);
        ShopCommand shopCommand = new ShopCommand(classementManager);

        getCommand("classement").setExecutor(classementCommand);
        getCommand("shop").setExecutor(shopCommand);
        getCommand("money").setExecutor(new MoneyCommand(classementManager));

        getServer().getPluginManager().registerEvents( new ShopEvent(classementManager), this);
    }

    @Override
    public void onDisable() {
        System.out.println("Le plugin Arkadia est OFF !");
    }
}
