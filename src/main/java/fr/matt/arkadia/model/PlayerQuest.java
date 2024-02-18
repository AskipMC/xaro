package fr.matt.arkadia.model;

public class PlayerQuest {

    private String playerName;
    private String classementName;

    private int countBlock;
    private int countMob;
    public PlayerQuest() {

    }

    public PlayerQuest(String playerName, String classementName, int countBlock, int countMob) {
        this.playerName = playerName;
        this.classementName = classementName;
        this.countBlock = countBlock;
        this.countMob = countMob;
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

    public int getCountBlock() {
        return this.countBlock;
    }

    public void setCountBlock(int countBlock) {
        this.countBlock = countBlock;
    }

    public int getCountMob() {
        return this.countMob;
    }

    public void setCountMob(int countMob) {
        this.countMob = countMob;
    }
}
