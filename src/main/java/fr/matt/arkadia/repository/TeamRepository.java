package fr.matt.arkadia.repository;

import fr.matt.arkadia.DocumentStoreHolder;
import fr.matt.arkadia.model.Team;
import net.ravendb.client.documents.session.IDocumentSession;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;

public class TeamRepository {

    public TeamRepository() {

    }

    public String createTeam(Team team) {

        try (IDocumentSession session = DocumentStoreHolder.getStore().openSession()) {

            boolean alreadyTakenName = !session.query(Team.class)
                                        .whereEquals("name", team.getName())
                                        .toList()
                                        .isEmpty();

            if(alreadyTakenName) return ChatColor.RED + "Ce nom de team est déjà pris.";

            session.store(team);
            session.saveChanges();

            return ChatColor.GREEN + "La team " + team.getName() + " a été créé.";
        }

    }

    public String disbandTeam(String teamName) {

        try (IDocumentSession session = DocumentStoreHolder.getStore().openSession()) {
            Team team = session.query(Team.class)
                    .whereEquals("name", teamName)
                    .firstOrDefault();

            if(team == null) return ChatColor.RED + "Nom de team introuvable.";

            session.delete(team);
            session.saveChanges();

            return ChatColor.GREEN + "La team " + teamName + " a été supprimé.";
        }

    }

    public String addTeamPlayer(String teamName, String playerName) {
        //ajouter une verif que le joueur est pas deja dans une team
        try (IDocumentSession session = DocumentStoreHolder.getStore().openSession()) {
            Team team = session.query(Team.class)
                    .whereEquals("name", teamName)
                    .firstOrDefault();

            if(team == null) return ChatColor.RED + "Nom de team introuvable.";

            team.addPlayer(playerName);
            session.saveChanges();

            return ChatColor.GREEN + "Le joueur " + playerName + " a été ajouté à la team " + teamName + ".";
        }
    }

    public String removeTeamPlayer(String teamName, String playerName) {

        try (IDocumentSession session = DocumentStoreHolder.getStore().openSession()) {
            Team team = session.query(Team.class)
                    .whereEquals("name", teamName)
                    .firstOrDefault();

            if(team == null) return ChatColor.RED + "Nom de team introuvable.";

            team.removePlayer(playerName);
            session.saveChanges();

            return ChatColor.GREEN + "Le joueur " + playerName + " a été enlevé de la team " + teamName + ".";
        }
    }

    public String setTeamColor(String teamName, String color) {

        try (IDocumentSession session = DocumentStoreHolder.getStore().openSession()) {
            Team team = session.query(Team.class)
                    .whereEquals("name", teamName)
                    .firstOrDefault();

            if(team == null) return ChatColor.RED + "Nom de team introuvable.";

            team.setColor(color);
            session.saveChanges();

            return ChatColor.GREEN + "Le couleur " + color + " a été ajouté à la team " + teamName + ".";
        }
    }

    public Team getTeamWithName(String teamName) {
        try (IDocumentSession session = DocumentStoreHolder.getStore().openSession()) {
            return session.query(Team.class)
                    .whereEquals("name", teamName)
                    .firstOrDefault();
        }
    }

    public Team getTeamWithPlayerName(String playerName) {
        try (IDocumentSession session = DocumentStoreHolder.getStore().openSession()) {
            List<Team> teams = session.query(Team.class).toList();

            for(int i=0; i< teams.size(); i++) {
                if(teams.get(i).getPlayers().contains(playerName)) return teams.get(i);
            }

            return null;
        }
    }

    public List<Team> getTeams() {
        try (IDocumentSession session = DocumentStoreHolder.getStore().openSession()) {
            return session.query(Team.class).toList();
        }
    }

    public Location getPlayerBase(String playerName, World world) {
        try (IDocumentSession session = DocumentStoreHolder.getStore().openSession()) {
            Team team = this.getTeamWithPlayerName(playerName);

            if(team == null) return null;
            if(team.getBaseX()==0 && team.getBaseY()==0 && team.getBaseZ()==0) return null;

            return new Location(world, team.getBaseX(), team.getBaseY(), team.getBaseZ());
        }
    }

    public boolean setTeamBase(String playerName, Location location) {
        try (IDocumentSession session = DocumentStoreHolder.getStore().openSession()) {
            Team team = this.getTeamWithPlayerName(playerName);
            if(team == null) return false;

            Team teamSession = session.query(Team.class)
                    .whereEquals("name", team.getName())
                    .firstOrDefault();
            teamSession.setBaseX(location.getX());
            teamSession.setBaseY(location.getY());
            teamSession.setBaseZ(location.getZ());
            session.saveChanges();

            return true;
        }
    }
}
