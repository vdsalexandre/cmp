package com.vds.cmp;

public enum Drinks {
    TEA('T', "tea", 0.4),
    COFFEE('C', "coffee", 0.6),
    CHOCOLATE('H', "chocolate", 0.5),
    ORANGE_JUICE('O', "orange juice", 0.6);

    private char code;
    private String name;
    private double price;

    Drinks(char code, String name, double price) {
        this.code = code;
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public char getCode() {
        return code;
    }
}
