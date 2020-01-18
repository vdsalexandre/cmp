package com.vds.cmp;

import com.vds.error.WrongOrderException;

public class DrinkMakerOrder {
    private Character code;
    private Integer sugarQuantity;
    private Integer stickOrNot;
    private String message;
    private double orderPrice;
    private boolean orderOkay;
    private double moneyToBack;

    public DrinkMakerOrder(String commandOrder) throws WrongOrderException {
        if (isCommandOrderOkay(commandOrder)) {
            String[] instructions = commandOrder.split(":");

            if (instructions.length > 4 || instructions.length < 1) throw new WrongOrderException("Wrong order");

            this.code = instructions[0].charAt(0);

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
                        double orderMoney = Double.valueOf(instructions[3]);
                        orderOkay = calculateOrderOkay(orderMoney);
                        moneyToBack = orderMoney - orderPrice;
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

    public Character getCode() {
        return code;
    }

    public void setCode(Character code) {
        this.code = code;
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

    public boolean isCommandOrderOkay(String commandOrder) {
        return commandOrder.contains(":");
    }

    @Override
    public String toString() {
        if (isMessage()) {
            return "DrinkMakerOrder {" + "\n\t" +
                    "message= " + message + "\n" +
                    '}';
        }
        else {
            return "DrinkMakerOrder {" + "\n\t" +
                    "drink= " + getDrinkName() + ",\n\t" +
                    "sugarQuantity= " + showSugarQuantity() + ",\n\t" +
                    "stickOrNot= " + isStick() + ",\n\t" +
                    "orderPrice= " + orderPrice + "€,\n\t" +
                    "isOrderOkay= " + orderOkay + "\n" +
                    '}';
        }
    }

    private String getDrinkName() {
        String drinkName = "";

        if (this.code == 'T') drinkName = "tea";

        if (this.code == 'H') drinkName = "chocolate";

        if (this.code == 'C') drinkName = "coffee";

        return drinkName;
    }

    private boolean isMessage() {
        return this.code == 'M';
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

    public String createNewMessage() {
        return !orderOkay ? "M:Solde insuffisant" : "M:Paiement ok, rendu= " + moneyToBack + "€";
    }
}
