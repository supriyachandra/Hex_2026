package com.controller;

import com.config.ProjConfig;
import com.enums.UserMembership;
import com.model.CartItem;
import com.model.User;
import com.service.CartService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;


public class AppController {
    public static void main(String[] args) {

        var context = new AnnotationConfigApplicationContext(ProjConfig.class);
        CartService cartService = context.getBean(CartService.class);

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("1. Insert User into DB");
            System.out.println("2. Insert Item into DB");
            System.out.println("3. Fetch All Items");
            System.out.println("4. Fetch items by Given User name ");
            System.out.println("5. Delete an item ");
            System.out.println("6. Exit");

            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 6) {
                break;
            }
            switch (choice) {
                case 1:
                    System.out.println("Enter user name: ");
                    String userName= sc.nextLine();
                    //sc.nextLine();
                    System.out.println("Enter Membership: ");
                    String membership= sc.nextLine();

                    User user= context.getBean(User.class);

                    user.setName(userName);
                    user.setUserMembership(UserMembership.valueOf(membership.toUpperCase()));

                    try {
                        cartService.insertUser(user);
                    } catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                        throw new RuntimeException("Insertion failed");
                    }

                    System.out.println("User Record Inserted in DB");
                    break;

                case 2:
                    System.out.println("Enter item name: ");
                    String itemName= sc.nextLine();

                    System.out.println("Enter price: ");
                    BigDecimal price= sc.nextBigDecimal();

                    System.out.println("Enter quantity: ");
                    int qty= sc.nextInt();

                    System.out.println("Enter User ID: ");
                    int userId= sc.nextInt();

                    CartItem item= context.getBean(CartItem.class);
                    item.setName(itemName);
                    item.setPrice(price);
                    item.setQty(qty);

                    User user1= context.getBean(User.class);
                    user1.setId(userId);

                    item.setUser(user1);

                    try{
                        cartService.insertItem(item);
                    } catch (RuntimeException e) {
                        throw new RuntimeException("Insertion failed. "+ e);
                    }

                    System.out.println("Item Record Inserted in DB");
                    break;

                case 3:
                    List<CartItem> list= cartService.fetchAllItems();
                    System.out.println("---- List Of Items (With Users)----");
                    list.forEach(System.out::println);

                    break;
                case 4:

                    System.out.println("Enter user name: ");
                    String name= sc.nextLine().toLowerCase();

                    List<CartItem> items = cartService.fetchAllItemsByUserName(name);
                    items.forEach(System.out::println);

                    break;
                case 5:
                    System.out.println("Enter Item ID to delete: ");
                    int id= sc.nextInt();

                    cartService.deleteItemById(id);
                    System.out.println("Item deleted successfully!");
                    break;
                default:
                    System.out.println("Invalid Input");
                    break;
            }

        }
        sc.close();
    }
}
