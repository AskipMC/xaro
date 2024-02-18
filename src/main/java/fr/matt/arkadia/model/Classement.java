package fr.matt.arkadia.model;

import org.bukkit.block.Block;
import org.bukkit.entity.Mob;

public class Classement {

    private String name;
    private boolean running;

    private Economie economie;

    private String blockQuest;

    private String mobQuest;

    public Classement() {

    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRunning() {
        return this.running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public Economie getEconomie() {
        return this.economie;
    }

    public void setEconomie(Economie economie) {
        this.economie = economie;
    }

    public String getBlockQuest() {
        return this.blockQuest;
    }

    public void setBlockQuest(String block) {
        this.blockQuest = block;
    }

    public String getMobQuest() {
        return this.mobQuest;
    }

    public void setMobQuest(String mob) {
        this.mobQuest = mob;
    }


}
