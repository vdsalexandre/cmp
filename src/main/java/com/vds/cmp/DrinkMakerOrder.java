package com.vds.cmp;

import com.vds.error.WrongOrderException;

public class DrinkMakerOrder {
    private Character drink;
    private Integer sugarQuantity;
    private Integer stickOrNot;
    private String message;
    private double orderPrice;
    private boolean orderOkay;

    public DrinkMakerOrder(String commandOrder) throws WrongOrderException {
        if (commandOrder.contains(":")) {
            String[] instructions = commandOrder.split(":");

            if (instructions.length > 4 || instructions.length < 1) throw new WrongOrderException("Wrong order");

            this.drink = instructions[0].charAt(0);

            if (!isMessage()) {
                try {
                    this.sugarQuantity = Integer.valueOf(instructions[1]);
                } catch (Exception ex) {
                    this.sugarQuantity = null;
                }

                try {
                    this.stickOrNot = Integer.valueOf(instructions[2]);
                } catch (Exception ex) {
                    this.stickOrNot = null;
                }

                this.orderPrice = getOrderPrice();

                if (instructions.length == 4) {
                    try {
                        orderOkay = calculateOrderOkay(Double.valueOf(instructions[3]));
                    } catch (Exception ex) {
                        orderOkay = false;
                    }
                }
            }
            else {
                this.message = instructions[1];
            }
        }
        else {
            throw new WrongOrderException("Wrong order !");
        }
    }

    public Character getDrink() {
        return drink;
    }

    public void setDrink(Character drink) {
        this.drink = drink;
    }

    public Integer getSugarQuantity() {
        return sugarQuantity;
    }

    private Integer showSugarQuantity() {
        return sugarQuantity == null ? 0 : sugarQuantity;
    }

    public void setSugarQuantity(Integer sugarQuantity) {
        this.sugarQuantity = sugarQuantity;
    }

    public Integer getStickOrNot() {
        return stickOrNot;
    }

    public void setStickOrNot(Integer stickOrNot) {
        this.stickOrNot = stickOrNot;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        if (isMessage()) {
            return "DrinkMakerOrder {" + "\n\t" +
                    "message: " + message + "\n" +
                    '}';
        }
        else {
            return "DrinkMakerOrder {" + "\n\t" +
                    "drink: " + getDrinkName() + ",\n\t" +
                    "sugarQuantity: " + showSugarQuantity() + ",\n\t" +
                    "stickOrNot: " + isStick() + ",\n\t" +
                    "orderPrice: " + orderPrice + "€,\n\t" +
                    "isOrderOkay: " + orderOkay + "\n" +
                    '}';
        }
    }

    private String getDrinkName() {
        String drinkName = "";

        if (this.drink == 'T') drinkName = "tea";

        if (this.drink == 'H') drinkName = "chocolate";

        if (this.drink == 'C') drinkName = "coffee";

        return drinkName;
    }

    private Boolean isMessage() {
        return this.drink == 'M';
    }

    private String isStick() {
        return this.stickOrNot != null ? "Yes" : "No";
    }

    public double getOrderPrice() {
        if (Drinks.CHOCOLATE.getName().equals(getDrinkName())) return Drinks.CHOCOLATE.getPrice();

        if (Drinks.COFFEE.getName().equals(getDrinkName())) return Drinks.COFFEE.getPrice();

        if (Drinks.TEA.getName().equals(getDrinkName())) return Drinks.TEA.getPrice();

        return 0.0;
    }

    public boolean isOrderOkay() {
        return orderOkay;
    }

    private boolean calculateOrderOkay(double money) {
        return money >= orderPrice;
    }
}
