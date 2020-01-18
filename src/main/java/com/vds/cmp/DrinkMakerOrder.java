package com.vds.cmp;

import com.vds.error.WrongOrderException;

public class DrinkMakerOrder {
    private Character drink;
    private Integer sugarQuantity;
    private Integer stickOrNot;
    private String message;

    public DrinkMakerOrder(String commandOrder) throws WrongOrderException {
        try {
            String[] instructions = commandOrder.split(":");

            this.drink = instructions[0].charAt(0);

            if (instructions.length > 2) {
                try {
                    this.sugarQuantity = Integer.valueOf(instructions[1]);
                } catch (NumberFormatException ex) {
                    this.sugarQuantity = null;
                }

                try {
                    this.stickOrNot = Integer.valueOf(instructions[2]);
                } catch (NumberFormatException ex) {
                    this.stickOrNot = null;
                }
            }
            else {
                this.message = instructions[1];
            }
        } catch (Exception ex) {
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
}
