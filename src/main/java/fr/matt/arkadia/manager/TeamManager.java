package fr.matt.arkadia.manager;

import fr.matt.arkadia.model.Team;
import fr.matt.arkadia.repository.TeamRepository;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TeamManager {

    private TeamRepository teamRepository;

    public TeamManager() {
        this.teamRepository = new TeamRepository();
    }

    public String createTeam(String teamName) {

        Team team = new Team();
        team.setName(teamName);
        team.setColor("gray");

        return this.teamRepository.createTeam(team);
    }

    public String disbandTeam(String teamName) {

        return this.teamRepository.disbandTeam(teamName);
    }

    public String teamShow(String teamName) {

        Team team = this.teamRepository.getTeamWithName(teamName);

        if(team == null) return ChatColor.RED + "Nom de team introuvable.";

        String response = ChatColor.GRAY + "--- Team " + buildChatColor(team.getColor()) + team.getName() + ChatColor.GRAY + " ---";
        for(int i=0; i<team.getPlayers().size();i++) response += "\n- " + team.getPlayers().get(i);

        return response;
    }

    public String teamList() {

        List<Team> teams = this.teamRepository.getTeams();

        String response = ChatColor.GRAY + "--- Liste des teams ---";
        for(int i=0; i<teams.size();i++) response += "\n- " + teams.get(i).getName();

        return response;
    }

    public String addTeamPlayer(String teamName, String playerName) {

        return  this.teamRepository.addTeamPlayer(teamName, playerName);
    }

    public String removeTeamPlayer(String teamName, String playerName) {

        return this.teamRepository.removeTeamPlayer(teamName, playerName);
    }

    public String setTeamColor(String teamName, String color) {

        if(this.buildChatColor(color).equals(ChatColor.GRAY)) return ChatColor.RED + "Cette couleur n'est pas prise en charge.";

        return this.teamRepository.setTeamColor(teamName, color);
    }

    public Team getPlayerTeam(String playerName) {

        return this.teamRepository.getTeamWithPlayerName(playerName);
    }

    public void sendTeamMessage(Player player, String message) {

        Team team = this.teamRepository.getTeamWithPlayerName(player.getName());
        if(team == null) {
            player.sendMessage(ChatColor.RED + "Vous n'etes dans aucune team.");
            return;
        }

        ArrayList<String> teamPlayers = team.getPlayers();

        Collection<? extends Player> onlinePlayers = Bukkit.getServer().getOnlinePlayers();
        for(Player p : onlinePlayers) {
            if(teamPlayers.contains(p.getName())){
                p.sendMessage(buildChatColor(team.getColor()) + player.getName() + " : " + message);
            }
        }
    }

    public ChatColor buildChatColor(String color) {

        if(color.equalsIgnoreCase("blue")) {
            return ChatColor.BLUE;
        }
        else if(color.equalsIgnoreCase("aqua")) {
            return ChatColor.AQUA;
        }
        else if(color.equalsIgnoreCase("red")) {
            return ChatColor.RED;
        }
        else if(color.equalsIgnoreCase("yellow")) {
            return ChatColor.YELLOW;
        }
        else if(color.equalsIgnoreCase("green")) {
            return ChatColor.GREEN;
        }
        else if(color.equalsIgnoreCase("purple")) {
            return ChatColor.LIGHT_PURPLE;
        }
        else if(color.equalsIgnoreCase("darkaqua")) {
            return ChatColor.DARK_AQUA;
        }
        else if(color.equalsIgnoreCase("darkblue")) {
            return ChatColor.DARK_BLUE;
        }
        else if(color.equalsIgnoreCase("darkgray")) {
            return ChatColor.DARK_GRAY;
        }
        else if(color.equalsIgnoreCase("darkgreen")) {
            return ChatColor.DARK_GREEN;
        }
        else if(color.equalsIgnoreCase("darkpurple")) {
            return ChatColor.DARK_PURPLE;
        }
        else return ChatColor.GRAY;
    }

    public void teleportToBase(Player player) {

        World world = player.getLocation().getWorld();
        Location base = this.teamRepository.getPlayerBase(player.getName(), world);
        if(base == null) {
            player.sendMessage(ChatColor.RED + "Vous n'etes pas dans une team ou vous n'avez pas de base.");
            return;
        }

        player.sendMessage(ChatColor.AQUA + "Téléportation à votre base.");
        player.teleport(base);
    }

    public void setBase(Player player, Location location) {

        if(this.teamRepository.setTeamBase(player.getName(), location)) player.sendMessage(ChatColor.AQUA + "Vous venez de poser vote nouvelle base.");
        else player.sendMessage(ChatColor.RED + "Vous n'etes pas dans une team ou une erreur s'est produite.");
    }
 }
