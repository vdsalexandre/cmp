package com.vds.cmp;

import com.vds.error.WrongOrderException;
import com.vds.services.BeverageQuantityCheckerImpl;
import com.vds.services.EmailNotifierImpl;

public class DrinkMakerOrder {
    private Character code;
    private Integer sugarQuantity;
    private Integer stickOrNot;
    private String message;
    private double orderPrice;
    private boolean orderOkay;
    private double moneyToBack;
    private boolean extraHot;
    private BeverageQuantityCheckerImpl quantityCheckerService;
    private EmailNotifierImpl emailNotifierService;

    public DrinkMakerOrder(String commandOrder) throws WrongOrderException {
        if (isCommandOrderOkay(commandOrder)) {
            String[] instructions = commandOrder.split(":");

            if (instructions.length > 4 || instructions.length < 1) throw new WrongOrderException("Wrong order");

            String commandCode = instructions[0];
            this.extraHot = checkIfDrinkIsExtraHot(commandCode);
            this.code = commandCode.charAt(0);

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

    public Integer getSugarQuantity() {
        return sugarQuantity;
    }

    private Integer showSugarQuantity() {
        return sugarQuantity == null ? 0 : sugarQuantity;
    }

    public Integer getStickOrNot() {
        return stickOrNot;
    }

    public String getMessage() {
        return message;
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
                    "extra hot= " + extraHot + ",\n\t" +
                    "sugarQuantity= " + showSugarQuantity() + ",\n\t" +
                    "stickOrNot= " + isStick() + ",\n\t" +
                    "orderPrice= " + orderPrice + "€,\n\t" +
                    "isOrderOkay= " + orderOkay + "\n" +
                    '}';
        }
    }

    public String getDrinkName() {
        for (Drinks drink : Drinks.values()) {
            if (this.code == drink.getCode())
                return drink.getName();
        }

        return "";
    }

    public boolean checkIfDrinkIsExtraHot(String commandCode) {
        if (commandCode.length() == 2 && commandCode.endsWith("h"))
            return true;
        else
            return false;
    }

    private boolean isMessage() {
        return this.code == 'M';
    }

    private String isStick() {
        return this.stickOrNot != null ? "Yes" : "No";
    }

    public double getOrderPrice() {
        for (Drinks drink : Drinks.values()) {
            if (this.code == drink.getCode())
                return drink.getPrice();
        }

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

    public boolean isExtraHot() {
        return extraHot;
    }

    public boolean isEnough(String drinkName) {
        return quantityCheckerService.isEmpty(drinkName);
    }

    public void notifyMissingDrink(String drinkName) {
        emailNotifierService.notifyMissingDrink(drinkName);
    }

    public void initializeBeverageQuantityCheckerService(BeverageQuantityCheckerImpl quantityCheckerService) {
        this.quantityCheckerService = quantityCheckerService;
    }

    public void initializeEmailNotifierService(EmailNotifierImpl emailNotifierService) {
        this.emailNotifierService = emailNotifierService;
    }
}
