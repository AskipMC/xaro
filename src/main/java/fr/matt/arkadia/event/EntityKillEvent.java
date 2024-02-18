package fr.matt.arkadia.event;

import fr.matt.arkadia.manager.ClassementManager;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityKillEvent implements Listener {

    private ClassementManager classementManager;

    public EntityKillEvent(ClassementManager classementManager) {
        this.classementManager = classementManager;
    }

    @EventHandler
    public void onEntityKill(EntityDeathEvent event)
    {

        Entity entity = event.getEntity();
        if(!(entity instanceof Mob)) return;

        Player player = event.getEntity().getKiller();
        if(player == null) return;

        if(!entity.getType().toString().equalsIgnoreCase(this.classementManager.getCurrentMobQuest())) return;

        this.classementManager.addMobKillCounter(player.getName());
    }
}
