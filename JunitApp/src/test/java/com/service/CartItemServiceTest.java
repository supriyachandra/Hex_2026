package com.service;

import com.exception.InvalidNameException;
import com.exception.InvalidPriceException;
import com.model.CartItem;
import com.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CartItemServiceTest {

    CartItemService cartItemService= new CartItemService();

    @Test
    public void computeTotalCostTest() {

        // Preparing data
        List<CartItem> items = new ArrayList<>();
        CartItem item1 = new CartItem(1, "Notebook", BigDecimal.valueOf(120), BigDecimal.valueOf(4));
        CartItem item2 = new CartItem(2, "Pens", BigDecimal.valueOf(10), BigDecimal.valueOf(15));
        CartItem item3 = new CartItem(3, "Diary", BigDecimal.valueOf(280), BigDecimal.valueOf(2));

        items.add(item1);
        items.add(item2);
        items.add(item3);

        User user = new User(1, "Harry_123", "PREMIUM");

        Assertions.assertTrue(BigDecimal.valueOf(1071)
                .compareTo(cartItemService.computeTotalCost(items, user)) == 0);


        // preparing data for normal and not eligible
        List<CartItem> items1 = new ArrayList<>();
        items1.add(item2);

        User user1 = new User(1, "Harry_123", "Normal");


        Assertions.assertTrue(BigDecimal.valueOf(150).
                compareTo(cartItemService.computeTotalCost(items1, user1))==0);


        // Status is normal and eligible for discount
        Assertions.assertTrue(BigDecimal.valueOf(1130.5).
                compareTo(cartItemService.computeTotalCost(items, user1))==0);


        // Status is premium but not eligible for discount
        List<CartItem> items5= new ArrayList<>();
        items5.add(item2);
        Assertions.assertTrue(BigDecimal.valueOf(150).
                compareTo(cartItemService.computeTotalCost(items5, user))==0);




//
        // validateUser fails
        Assertions.assertThrows(NullPointerException.class,
                ()-> cartItemService.computeTotalCost(null, user));
//
//
//        // validate item fails
//        //Assertions.assertEquals(false, cartItemService.computeTotalCost(null, user));
    }


        @Test
        public void validateItemTest() {
            // test if item is null
            Assertions.assertThrows(NullPointerException.class,
                    () -> cartItemService.validateItem(null));

            //preparing items
            CartItem item1 = new CartItem(1, "", BigDecimal.valueOf(120), BigDecimal.valueOf(4));
            CartItem item2 = new CartItem(2, "Pens", BigDecimal.valueOf(-2), BigDecimal.valueOf(15));

            // if item name is empty
            Assertions.assertThrows(InvalidNameException.class,
                    ()-> cartItemService.validateItem(item1));

            // if name is null
            CartItem item3 = new CartItem(1, null, BigDecimal.valueOf(120), BigDecimal.valueOf(4));
            Assertions.assertThrows(NullPointerException.class,
                    ()-> cartItemService.validateItem(item3));

            // Item price is 0 or negative
            Assertions.assertThrows(InvalidPriceException.class,
                    ()-> cartItemService.validateItem(item2));

        }

        @Test
        public void validateUserTest(){
            // Test if user is null
            Assertions.assertThrows(NullPointerException.class,
                    () -> cartItemService.validateUser(null));

            //status is not valid
            User user1= new User(1, "Harry", "Simple");
            Assertions.assertThrows(RuntimeException.class,
                    () -> cartItemService.validateUser(user1));

            //username is empty
            User user2= new User(1, "", "Premium");
            Assertions.assertThrows(RuntimeException.class,
                    () -> cartItemService.validateUser(user2));
        }
}
