package fr.matt.arkadia.repository;

import fr.matt.arkadia.DocumentStoreHolder;
import fr.matt.arkadia.model.Classement;
import fr.matt.arkadia.model.PlayerQuest;
import fr.matt.arkadia.model.VenteShop;
import net.ravendb.client.documents.session.IDocumentSession;
import org.bukkit.ChatColor;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

public class PlayerQuestRepository {

    public PlayerQuestRepository() {

    }

    public List<PlayerQuest> getClassementPlayersQuests(String classementName) {
        try (IDocumentSession session = DocumentStoreHolder.getStore().openSession()) {
            return session.query(PlayerQuest.class)
                    .whereEquals("classementName", classementName)
                    .toList();
        }
    }

    public PlayerQuest getPlayerQuest(String playerName, String classementName) {
        try (IDocumentSession session = DocumentStoreHolder.getStore().openSession()) {
            return session.query(PlayerQuest.class)
                    .whereEquals("playerName", playerName)
                    .whereEquals("classementName", classementName)
                    .firstOrDefault();
        }
    }

    public void createPlayerQuest(PlayerQuest playerQuest) {
        try (IDocumentSession session = DocumentStoreHolder.getStore().openSession()) {
            session.store(playerQuest);
            session.saveChanges();
        }
    }

    public void savePlayerQuest(PlayerQuest playerQuest) {
        try (IDocumentSession session = DocumentStoreHolder.getStore().openSession()) {

            PlayerQuest quest = session.query(PlayerQuest.class)
                    .whereEquals("playerName", playerQuest.getPlayerName())
                    .whereEquals("classementName", playerQuest.getClassementName())
                    .firstOrDefault();

            quest.setCountBlock(playerQuest.getCountBlock());
            quest.setCountMob(playerQuest.getCountMob());

            session.saveChanges();
        }
    }
}
