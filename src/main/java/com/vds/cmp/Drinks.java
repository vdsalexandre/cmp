package com.vds.cmp;

public enum Drinks {
    TEA("tea", 0.4),
    COFFEE("coffee", 0.6),
    CHOCOLATE("chocolate", 0.5);

    private String name;
    private double price;

    Drinks(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
