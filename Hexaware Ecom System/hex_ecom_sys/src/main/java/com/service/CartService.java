package com.service;

import com.enums.UserMembership;
import com.exception.InvalidMemberShipException;
import com.exception.InvalidNameException;
import com.model.CartItem;
import com.model.User;
import com.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;        // DI using constructor instead of Autowired

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }


    public void insertUser(User user) {
        if(user.getName()== null || user.getName().isBlank()){
            throw new InvalidNameException("Name Cannot Be Null or Empty");
        }
        if(!(user.getUserMembership() == UserMembership.NORMAL|| user.getUserMembership()== UserMembership.PREMIUM)){
            throw new InvalidMemberShipException("Membership Invalid");
        }

        cartRepository.insertUser(user);

    }

    public void insertItem(CartItem item) {
        if(item.getName()== null || item.getName().isBlank()){
            throw new InvalidNameException("Name Cannot Be Null or Empty");
        }

        cartRepository.insertItem(item);
    }

    public List<CartItem> fetchAllItems() {
        return cartRepository.fetchAllItems();
    }

    public List<CartItem> fetchAllItemsByUserName(String name) {
        return cartRepository.fetchAllItemsByUserName(name);
    }

    public void deleteItemById(int id) {
        int rowsAffected= cartRepository.deleteItemById(id);

        if(rowsAffected==0){
            throw new RuntimeException("Invalid ID (ID not in DB)");
        }
    }
}
