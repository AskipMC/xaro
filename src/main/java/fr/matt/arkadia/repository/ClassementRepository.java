package fr.matt.arkadia.repository;

import fr.matt.arkadia.DocumentStoreHolder;
import fr.matt.arkadia.model.Classement;
import fr.matt.arkadia.model.Team;
import net.ravendb.client.documents.session.IDocumentSession;
import org.bukkit.ChatColor;

public class ClassementRepository {

    public ClassementRepository() {

    }

    public String startClassement(Classement classement) {
        try (IDocumentSession session = DocumentStoreHolder.getStore().openSession()) {
            session.store(classement);
            session.saveChanges();

            return ChatColor.GREEN + "Le classement " + classement.getName() + " vient de se lancer !";
        }
    }

    public String stopClassement() {

        try (IDocumentSession session = DocumentStoreHolder.getStore().openSession()) {
            Classement classement = session.query(Classement.class)
                                        .whereEquals("running", true)
                                        .firstOrDefault();

            if(classement == null) return null;

            classement.setRunning(false);
            session.saveChanges();

            return classement.getName();
        }
    }

    public Classement getRunningClassement() {
        try (IDocumentSession session = DocumentStoreHolder.getStore().openSession()) {
            return session.query(Classement.class)
                    .whereEquals("running", true)
                    .firstOrDefault();
        }
    }

    public boolean isClassementRunning() {
        try (IDocumentSession session = DocumentStoreHolder.getStore().openSession()) {
            return !session.query(Classement.class)
                    .whereEquals("running", true)
                    .toList()
                    .isEmpty();
        }
    }

    public boolean isClassementNameExist(String classementName) {
        try (IDocumentSession session = DocumentStoreHolder.getStore().openSession()) {
            return !session.query(Classement.class)
                    .whereEquals("name", classementName)
                    .toList()
                    .isEmpty();
        }
    }

}
