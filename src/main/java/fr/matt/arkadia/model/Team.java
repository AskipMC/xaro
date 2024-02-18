package fr.matt.arkadia.model;

import org.bukkit.Location;

import java.util.ArrayList;

public class Team {

    private String name;
    private String color;
    private ArrayList<String> players;

    private double baseX;
    private double baseY;
    private double baseZ;

    private int score;
    public Team() {
        players = new ArrayList<String>();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public ArrayList<String> getPlayers() {
        return this.players;
    }

    public void addPlayer(String playerName) {
        this.players.add(playerName);
    }

    public void removePlayer(String playerName) {
        this.players.remove(playerName);
    }

    public double getBaseX() {
        return this.baseX;
    }

    public void setBaseX(double baseX) {
        this.baseX = baseX;
    }

    public double getBaseY() {
        return this.baseY;
    }

    public void setBaseY(double baseY) {
        this.baseY = baseY;
    }

    public double getBaseZ() {
        return this.baseZ;
    }

    public void setBaseZ(double baseZ) {
        this.baseZ = baseZ;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
