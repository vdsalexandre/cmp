package com.vds.cmp;

import com.vds.error.WrongOrderException;
import com.vds.services.BeverageQuantityCheckerImpl;
import com.vds.services.EmailNotifierImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DrinkMakerServiceTest {
    BeverageQuantityCheckerImpl quantityCheckerService = Mockito.mock(BeverageQuantityCheckerImpl.class);
    EmailNotifierImpl emailNotifierService = Mockito.mock(EmailNotifierImpl.class);

    @Test()
    void testBeverageQuantityCheckerService() throws WrongOrderException {
        DrinkMakerOrder order = new DrinkMakerOrder("C:1:0:1");
        order.initializeBeverageQuantityCheckerService(quantityCheckerService);
        when(quantityCheckerService.isEmpty("coffee")).thenReturn(false);
        assertEquals(false, order.isEnough("coffee"));
        verify(quantityCheckerService).isEmpty("coffee");
    }

    @Test()
    void testEmailNotifierService() throws WrongOrderException {
        DrinkMakerOrder order = new DrinkMakerOrder("Hh:::2");
        order.initializeEmailNotifierService(emailNotifierService);
        doNothing().when(emailNotifierService).notifyMissingDrink("chocolate");
        order.notifyMissingDrink("chocolate");
        verify(emailNotifierService).notifyMissingDrink("chocolate");
    }
}
