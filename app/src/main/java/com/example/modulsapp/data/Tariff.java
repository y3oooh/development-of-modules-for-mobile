package com.example.modulsapp.data;

public class Tariff {
    private final String name;
    private final int basePrice;

    public Tariff(String name, int basePrice) {
        this.name = name;
        this.basePrice = basePrice;
    }

    public String getName() { return name; }
    public int getBasePrice() { return basePrice; }
}
