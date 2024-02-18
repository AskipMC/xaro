package fr.matt.arkadia.event;

import fr.matt.arkadia.manager.ClassementManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionEvent implements Listener {

    private ClassementManager classementManager;
    public ConnectionEvent(ClassementManager classementManager) {
        this.classementManager = classementManager;
    }

    @EventHandler
    public void onConnected(PlayerJoinEvent event) {
        this.classementManager.loadPlayerQuest(event.getPlayer().getName());
    }
    @EventHandler
    public void onDisconnected(PlayerQuitEvent event) {
        this.classementManager.savePlayerQuest(event.getPlayer().getName());
    }
}
