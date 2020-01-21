package com.vds.cmp;

import com.vds.error.WrongOrderException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;


public class DrinkMakerOrderTest {

    @Test
    void createNewOrder() throws WrongOrderException {
        DrinkMakerOrder order = new DrinkMakerOrder("T:1:0");
        Assertions.assertEquals('T', order.getCode());
        Assertions.assertEquals(1, order.getSugarQuantity());
        Assertions.assertEquals(0, order.getStickOrNot());
    }

    @Test
    void createNewOrderNoSugar() throws WrongOrderException {
        DrinkMakerOrder order = new DrinkMakerOrder("H::0");
        Assertions.assertEquals('H', order.getCode());
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
           DrinkMakerOrder order = new DrinkMakerOrder("T:1:0:coucou:hello");
        });
    }

    @Test()
    void createNewOrderNoStick() throws WrongOrderException {
        DrinkMakerOrder order = new DrinkMakerOrder("C:2:");
        Assertions.assertEquals('C', order.getCode());
        Assertions.assertEquals(2, order.getSugarQuantity());
        Assertions.assertEquals(null, order.getStickOrNot());
    }

    @Test()
    void createNewMessage() throws WrongOrderException {
        DrinkMakerOrder order = new DrinkMakerOrder("M:il faudrait acheter a nouveau du café, il y en a plus ...");
        Assertions.assertEquals('M', order.getCode());
        Assertions.assertEquals(order.getMessage(), order.getMessage());
    }

    @Test()
    void showNewOrder() throws WrongOrderException {
        DrinkMakerOrder order1 = new DrinkMakerOrder("H:1:0");
        System.out.println(order1);

        DrinkMakerOrder order2 = new DrinkMakerOrder("T:2:");
        System.out.println(order2);

        DrinkMakerOrder order3 = new DrinkMakerOrder("M:there is not enough sugar in my tea ...");
        System.out.println(order3);

        DrinkMakerOrder order4 = new DrinkMakerOrder("C::");
        System.out.println(order4);

        DrinkMakerOrder order5 = new DrinkMakerOrder("T:2::2");
        System.out.println(order5);
    }

    @Test()
    void createNewOrderWithPaymentOk() throws WrongOrderException {
        DrinkMakerOrder order = new DrinkMakerOrder("C:::1");
        Assertions.assertEquals('C', order.getCode());
        Assertions.assertEquals(null, order.getSugarQuantity());
        Assertions.assertEquals(null, order.getStickOrNot());
        Assertions.assertEquals(true, order.isOrderOkay());
    }

    @Test()
    void createNewOrderWithPaymentKo() throws WrongOrderException {
        DrinkMakerOrder order = new DrinkMakerOrder("H:1:0:0.2");
        Assertions.assertEquals('H', order.getCode());
        Assertions.assertEquals(1, order.getSugarQuantity());
        Assertions.assertEquals(0, order.getStickOrNot());
        Assertions.assertEquals(false, order.isOrderOkay());
    }

    @Test()
    void createNewOrderAndSendMessage() throws WrongOrderException {
        DrinkMakerOrder order = new DrinkMakerOrder("C::0:1");
        Assertions.assertEquals('C', order.getCode());
        Assertions.assertEquals(null, order.getSugarQuantity());
        Assertions.assertEquals(0, order.getStickOrNot());
        Assertions.assertEquals(true, order.isOrderOkay());

        DrinkMakerOrder orderMessage = new DrinkMakerOrder(order.createNewMessage());
        Assertions.assertEquals('M', orderMessage.getCode());
        System.out.println(orderMessage);

        DrinkMakerOrder order1 = new DrinkMakerOrder("T:2::2");
        Assertions.assertEquals('T', order1.getCode());
        Assertions.assertEquals(2, order1.getSugarQuantity());
        Assertions.assertEquals(null, order1.getStickOrNot());
        Assertions.assertEquals(true, order1.isOrderOkay());

        DrinkMakerOrder orderMessage1 = new DrinkMakerOrder(order1.createNewMessage());
        Assertions.assertEquals('M', orderMessage.getCode());
        System.out.println(orderMessage1);

        DrinkMakerOrder order2 = new DrinkMakerOrder("C:::0.5");
        Assertions.assertEquals('C', order2.getCode());
        Assertions.assertEquals(null, order2.getSugarQuantity());
        Assertions.assertEquals(null, order2.getStickOrNot());
        Assertions.assertEquals(false, order2.isOrderOkay());

        DrinkMakerOrder orderMessage2 = new DrinkMakerOrder(order2.createNewMessage());
        Assertions.assertEquals('M', orderMessage2.getCode());
        System.out.println(orderMessage2);
    }

    @Test()
    void createNewOrderExtraHot() throws WrongOrderException {
        DrinkMakerOrder order = new DrinkMakerOrder("Ch:::1");
        Assertions.assertEquals('C', order.getCode());
        Assertions.assertEquals(true, order.isExtraHot());
        Assertions.assertEquals(null, order.getSugarQuantity());
        Assertions.assertEquals(null, order.getStickOrNot());
        Assertions.assertEquals(true, order.isOrderOkay());

        DrinkMakerOrder orderMessage = new DrinkMakerOrder(order.createNewMessage());
        Assertions.assertEquals('M', orderMessage.getCode());
    }

    @Test()
    void createNewOrderWithNewDrink() throws WrongOrderException {
        DrinkMakerOrder order = new DrinkMakerOrder("O:::2");
        Assertions.assertEquals('O', order.getCode());
        Assertions.assertEquals(false, order.isExtraHot());
        Assertions.assertEquals(null, order.getSugarQuantity());
        Assertions.assertEquals(null, order.getStickOrNot());
        Assertions.assertEquals(true, order.isOrderOkay());
    }

    @Test()
    void createOrdersAndShowThem() throws WrongOrderException {
        CMPRepository dataRepository = new CMPRepository();

        DrinkMakerOrder order1 = new DrinkMakerOrder("Th:2:0:2");
        dataRepository.addNewDrinkMakerOrder(order1);

        DrinkMakerOrder order2 = new DrinkMakerOrder("C:::1");
        dataRepository.addNewDrinkMakerOrder(order2);

        DrinkMakerOrder order3 = new DrinkMakerOrder("Ch:1:0:2");
        dataRepository.addNewDrinkMakerOrder(order3);

        DrinkMakerOrder order4 = new DrinkMakerOrder("O:::5");
        dataRepository.addNewDrinkMakerOrder(order4);

        DrinkMakerOrder order5 = new DrinkMakerOrder("Hh::0:2");
        dataRepository.addNewDrinkMakerOrder(order5);

        double total = dataRepository.getTotalPriceFromAllCommands();
        Assertions.assertEquals(2.7, total);

        double totalCoffee = dataRepository.getTotalPriceFromCommandsWith('C');
        Assertions.assertEquals(1.2, totalCoffee);

        System.out.println(dataRepository.printReport());
    }
}