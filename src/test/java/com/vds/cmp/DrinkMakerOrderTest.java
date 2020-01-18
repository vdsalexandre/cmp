package com.vds.cmp;

import com.vds.error.WrongOrderException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class DrinkMakerOrderTest {

    @Test
    void createNewOrder() throws WrongOrderException {
        DrinkMakerOrder order = new DrinkMakerOrder("T:1:0");
        Assertions.assertEquals('T', order.getDrink());
        Assertions.assertEquals(1, order.getSugarQuantity());
        Assertions.assertEquals(0, order.getStickOrNot());
    }

    @Test
    void createNewOrderNoSugar() throws WrongOrderException {
        DrinkMakerOrder order = new DrinkMakerOrder("H::0");
        Assertions.assertEquals('H', order.getDrink());
        Assertions.assertEquals(null, order.getSugarQuantity());
        Assertions.assertEquals(0, order.getStickOrNot());
    }

    @Test()
    void createWrongOrder() {
        Assertions.assertThrows(WrongOrderException.class, () -> {
            DrinkMakerOrder order = new DrinkMakerOrder("no-order");
        });
    }

    @Test()
    void createOrderWithTooMuchParameters() {
        Assertions.assertThrows(WrongOrderException.class, () -> {
           DrinkMakerOrder order = new DrinkMakerOrder("T:1:0:coucou");
        });
    }

    @Test()
    void createNewOrderNoStick() throws WrongOrderException {
        DrinkMakerOrder order = new DrinkMakerOrder("C:2:");
        Assertions.assertEquals('C', order.getDrink());
        Assertions.assertEquals(2, order.getSugarQuantity());
        Assertions.assertEquals(null, order.getStickOrNot());
    }

    @Test()
    void createNewMessage() throws WrongOrderException {
        DrinkMakerOrder order = new DrinkMakerOrder("M:il faudrait acheter a nouveau du café, il y en a plus ...");
        Assertions.assertEquals('M', order.getDrink());
        Assertions.assertEquals(order.getMessage(), order.getMessage());
    }

    @Test()
    void showNewOrder() throws WrongOrderException {
        DrinkMakerOrder order = new DrinkMakerOrder("H:1:0");
        System.out.println(order);

        DrinkMakerOrder order2 = new DrinkMakerOrder("T:2:");
        System.out.println(order2);

        DrinkMakerOrder order3 = new DrinkMakerOrder("M:there is not enough sugar in my tea ...");
        System.out.println(order3);

        DrinkMakerOrder order4 = new DrinkMakerOrder("C::");
        System.out.println(order4);
    }
}