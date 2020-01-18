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
}