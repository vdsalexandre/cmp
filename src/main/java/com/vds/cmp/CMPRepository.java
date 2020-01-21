package com.vds.cmp;

import java.util.ArrayList;
import java.util.List;

public class CMPRepository {
    private List<DrinkMakerOrder> allCommands;

    public CMPRepository() {
        this.allCommands = new ArrayList<DrinkMakerOrder>();
    }

    public void addNewDrinkMakerOrder(DrinkMakerOrder order) {
        allCommands.add(order);
    }

    public List<DrinkMakerOrder> getAllCommandsWith(char code) {
        List<DrinkMakerOrder> orders = new ArrayList<>();

        for (DrinkMakerOrder drinkOrder : allCommands) {
            if (drinkOrder.getCode() == code)
                orders.add(drinkOrder);
        }
        return orders;
    }

    public List<DrinkMakerOrder> getAllCommands() {
        return allCommands;
    }

    public double getTotalPriceFromAllCommands() {
        double total = 0;

        for (DrinkMakerOrder drinkOrder : allCommands) {
            total += drinkOrder.getOrderPrice();
        }
        return total;
    }

    public double getTotalPriceFromCommandsWith(char code) {
        double total = 0;

        for (DrinkMakerOrder drinkOrder : allCommands) {
            if (drinkOrder.getCode() == code)
                total += drinkOrder.getOrderPrice();
        }
        return total;
    }

    public String printReport() {
        String report = "";
        int totalTab[] = new int[Drinks.values().length];
        for (int total : totalTab) total = 0;

        int i = 0;
        double totalFinal = 0;
        for (Drinks drink : Drinks.values()) {
            for (DrinkMakerOrder order : allCommands) {
                if (order.getCode() == drink.getCode())
                    totalTab[i] += 1;
            }
            double totalForThisDrink = totalTab[i] * drink.getPrice();
            totalFinal += totalForThisDrink;
            report += drink.getName() + " (" + drink.getCode() + ") = " + totalTab[i] + " commande(s) --> total = " + (totalForThisDrink) + "€\n";
            i++;
        }
        report += "\nNombre total de commande(s) = " + allCommands.size() + ", Somme totale = " + totalFinal + "€";
        return report;
    }
}
