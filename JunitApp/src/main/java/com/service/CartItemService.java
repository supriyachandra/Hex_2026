package com.service;

import com.exception.InvalidNameException;
import com.exception.InvalidPriceException;
import com.model.CartItem;
import com.model.User;

import java.math.BigDecimal;
import java.util.List;

public class CartItemService {

    public BigDecimal computeTotalCost(List<CartItem> items, User user) {
        BigDecimal totalPrice = BigDecimal.valueOf(0);

        // Validate if item is null
        if (items == null) {
            throw new NullPointerException("Items cannot be null");
        }

        validateUser(user);


            for (CartItem item : items) {

                validateItem(item);

                    totalPrice = totalPrice.add(item.getPrice().
                            multiply(item.getQuantity()));
            }

            if (user.getStatus().equalsIgnoreCase("Normal")
                    && totalPrice.compareTo(BigDecimal.valueOf(1000)) > 0) {

                BigDecimal discount = totalPrice.multiply(new BigDecimal("0.05"));
                totalPrice = totalPrice.subtract(discount);
            }

            if (user.getStatus().equalsIgnoreCase("Premium")
                    && totalPrice.compareTo(BigDecimal.valueOf(500)) > 0) {

                BigDecimal discount = totalPrice.multiply(new BigDecimal("0.10"));
                totalPrice = totalPrice.subtract(discount);
            }


            return totalPrice;
        }


    public boolean validateUser(User user) {
        // Validate user

        if(user == null){
            throw new NullPointerException("User cannot be null");
        }

        if(user.getUsername().isEmpty()){
            throw new RuntimeException("Username cannot be empty");
        }

        if(!(user.getStatus().equalsIgnoreCase("NORMAL") || user.getStatus().equalsIgnoreCase("PREMIUM"))){
            throw new RuntimeException("Invalid Status. Can only be NORMAL or PREMIUM");
        }
        return true;

    }


    public boolean validateItem (CartItem item){

            //Validate if item is negative
            if (item.getPrice().compareTo(BigDecimal.valueOf(0)) < 0) {
                throw new InvalidPriceException("Price cannot be negative");
            }

            if ( item.getName() == null) {
                throw new NullPointerException("Name cannot be empty");
            }

            // validate if name is null or empty
            if (item.getName().isEmpty()) {
                throw new InvalidNameException("Name cannot be empty");
            }

            return true;
    }
}
