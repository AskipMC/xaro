package fr.matt.arkadia.model;

import java.util.ArrayList;

public class Economie {

    ArrayList<EconomieItem> items;

    public Economie() {
         items = new ArrayList<EconomieItem>();
    }

    public ArrayList<EconomieItem> getItems() {
        return this.items;
    }

    public void addItem(EconomieItem item) {
        this.items.add(item);
    }

    public void removeItem(EconomieItem item) {
        this.items.remove(item);
    }
}
