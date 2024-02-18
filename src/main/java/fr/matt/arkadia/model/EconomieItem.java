package fr.matt.arkadia.model;

import org.bukkit.Material;

public class EconomieItem {

    private String category;

    private int max; //max vente

    private double value;

    private Material material;

    private int byteMaterial;

    public EconomieItem(Material material, int byteMaterial, int max, double value, String category) {
        this.material = material;
        this.byteMaterial = byteMaterial;
        this.max = max;
        this.value = value;
        this.category = category;
    }

    public EconomieItem() {}

    public EconomieItem(Material material, int max, double value, String category) {
        this.material = material;
        this.max = max;
        this.value = value;
        this.category = category;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getValue() {
        return this.value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getMax() {
        return this.max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public Material getMaterial() {
        return this.material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public int getByteMaterial() {
        return this.byteMaterial;
    }

    public void setByteMaterial(byte byteMaterial) {
        this.byteMaterial = byteMaterial;
    }
}
