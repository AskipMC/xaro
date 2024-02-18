package fr.matt.arkadia.repository;

import fr.matt.arkadia.DocumentStoreHolder;
import fr.matt.arkadia.model.Classement;
import fr.matt.arkadia.model.VenteShop;
import net.ravendb.client.documents.session.IDocumentSession;

import java.util.List;

public class ShopRepository {

    public ShopRepository() {

    }

    public List<VenteShop> getClassementVentesShop(String classementName) {
        try (IDocumentSession session = DocumentStoreHolder.getStore().openSession()) {
            return session.query(VenteShop.class)
                    .whereEquals("classementName", classementName)
                    .toList();
        }
    }

    public List<VenteShop> getPlayerVentesShop(String playerName, String classementName) {
        try (IDocumentSession session = DocumentStoreHolder.getStore().openSession()) {
            return session.query(VenteShop.class)
                    .whereEquals("playerName", playerName)
                    .whereEquals("classementName", classementName)
                    .toList();
        }
    }

    public boolean addShopVente(VenteShop vente, int limite) {
        try (IDocumentSession session = DocumentStoreHolder.getStore().openSession()) {
            List<Integer> quantities = session.query(VenteShop.class)
                    .whereEquals("teamName", vente.getTeamName())
                    .whereEquals("classementName", vente.getClassementName())
                    .selectFields(Integer.class, "quantity")
                    .toList();

            int quantity = 0;
            if(quantities.size() != 0) quantity = quantities.stream()
                                                    .mapToInt(a -> a)
                                                    .sum();

            if(limite <= quantity + vente.getQuantity()) {
                System.out.println("Limite :" + limite);
                System.out.println("Qt db :" + quantity);
                System.out.println("Qt vente :" + vente.getQuantity());
                return false;

            }
            else {

                session.store(vente);
                session.saveChanges();

                return true;
            }
        }
    }
}
