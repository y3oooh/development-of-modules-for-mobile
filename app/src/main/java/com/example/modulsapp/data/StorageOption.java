package com.example.modulsapp.data;

public class StorageOption {
    private final int gb;
    private final int price;

    public StorageOption(int gb, int price) {
        this.gb = gb;
        this.price = price;
    }

    public int getGb() { return gb; }
    public int getPrice() { return price; }
}
