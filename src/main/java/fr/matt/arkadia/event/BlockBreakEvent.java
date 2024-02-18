package fr.matt.arkadia.event;

import fr.matt.arkadia.manager.ClassementManager;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BlockBreakEvent implements Listener {

    private ClassementManager classementManager;

    public BlockBreakEvent(ClassementManager classementManager) {
        this.classementManager = classementManager;
    }

    @EventHandler
    public void onBlockBreak(org.bukkit.event.block.BlockBreakEvent event)
    {
        Block block = event.getBlock();
        Player player = event.getPlayer();

        if(!block.getType().toString().equals(this.classementManager.getCurrentBlockQuest())) return;

        this.classementManager.addBlockBreakCounter(player.getName());
    }
}
