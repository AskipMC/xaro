package fr.matt.arkadia.event;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEvent implements Listener {

    @EventHandler
    public void onChatEvent(AsyncPlayerChatEvent event) {
        event.setMessage(ChatColor.WHITE + event.getMessage());
    }
}
