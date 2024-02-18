package fr.matt.arkadia.event;

import fr.matt.arkadia.manager.TeamManager;
import fr.matt.arkadia.model.Team;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class TeamEvent implements Listener {

    private TeamManager teamManager;

    public TeamEvent(TeamManager teamManager) {
        this.teamManager = teamManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        Team team = this.teamManager.getPlayerTeam(player.getName());

        if(team == null) return;

        String displayName = this.teamManager.buildChatColor(team.getColor()) + player.getName();
        player.setDisplayName(displayName);
        player.setPlayerListName(displayName);
    }
}
