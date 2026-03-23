package com.controller;

import com.config.ProjConfig;
import com.model.Fund;
import com.model.Manager;
import com.service.FundService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Scanner;

public class FundController {
    public static void main(String[] args) {
        var context= new AnnotationConfigApplicationContext(ProjConfig.class);
        FundService fundService= context.getBean(FundService.class);

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Add Manager");
            System.out.println("2. Add Fund");
            System.out.println("3. Show Funds by Manager");
            System.out.println("0. Exit");

            int input = sc.nextInt();

            if (input == 0) break;

            switch (input) {

                case 1:
                    Manager manager = new Manager();
                    System.out.println("Enter Manager Name:");
                    manager.setName(sc.next());

                    System.out.println("Enter Email:");
                    manager.setEmail(sc.next());

                    fundService.addManager(manager);
                    System.out.println("Manager added!");
                    break;

                case 2:
                    System.out.println("Enter Manager ID:");
                    Long managerId= sc.nextLong();
                    try {
                        Fund fund = new Fund();

                        System.out.println("Enter Fund Name:");
                        fund.setName(sc.next());

                        System.out.println("Enter sum Amount:");
                        fund.setSumAmount(sc.nextBigDecimal());

                        System.out.println("Enter Expense Ratio:");
                        fund.setExpenseRatio(sc.nextBigDecimal());

                        fundService.addFund(fund, managerId);

                        System.out.println("Fund added!");

                    } catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 3:
                    System.out.println("Enter Manager ID:");
                    Long mid = sc.nextLong();

                    List<Fund> funds = fundService.getFundsByManager(mid);
                    funds.forEach(System.out::println);
                    break;
            }
        }

        sc.close();
        context.close();
    }
}
