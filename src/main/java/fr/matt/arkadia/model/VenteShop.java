package fr.matt.arkadia.model;

public class VenteShop {

    private String teamName;
    private String playerName;
    private String classementName;
    private String itemName;
    private int quantity;
    private double value;

    public VenteShop() {

    }

    public String getTeamName() {
        return this.teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    public String getClassementName() {
        return this.classementName;
    }

    public void setClassementName(String classementName) {
        this.classementName = classementName;
    }

    public String getItemName() {
        return this.itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getValue() {
        return this.value;
    }

    public void setValue(double value) {
        this.value = value;
    }

}
