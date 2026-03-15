package com.car.main;

import com.car.model.Car;
import com.car.service.CarService;

import java.sql.SQLException;
import java.util.*;


public class CarController {

    public static void main(String[] args) {

        Scanner sc= new Scanner(System.in);

        CarService carService= new CarService();

        System.out.println("------------- Car Rental System -------------");

        while(true) {
            System.out.println("1. Display Car information, owner name, year of purchase, and mileage");
            System.out.println("2. Display Car brand and number of cars of the brand");
            System.out.println("3. Exit");
            int choice= sc.nextInt();
            if(choice==3){
                break;
            }
            else{
                switch(choice){
                    case 1:
                        try {
                            List<Car> list = new ArrayList<>();

                            list = carService.getCarInfo();

                            list.forEach(car -> {
                                System.out.println("Car id: " + car.getId());
                                System.out.println("Registration Number: " + car.getRegistrationNumber());
                                System.out.println("Chassis Number: " + car.getChasisNumber());
                                System.out.println("Registration State: " + car.getRegistrationNumber());
                                System.out.println("Brand: " + car.getBrand());
                                System.out.println("Model: " + car.getModel());
                                System.out.println("Variant: " + car.getVariant());
                                System.out.println("Owner Name: " + car.getOwner().getName());
                                System.out.println("Year of Purchase: " + car.getCarDetails().getYear_of_purchase());
                                System.out.println("Mileage: " + car.getCarDetails().getMileage());
                                System.out.println("    ");
                            });
                        }catch (SQLException e){
                            throw new RuntimeException(e);
                        }

                        break;
                    case 2:

                        System.out.println("Brand \t\t\t\t Number of Cars");
                        Map<String, Integer> map= new LinkedHashMap<>();

                        try {

                            map = carService.getCarBrandStat();

                            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                                System.out.println(entry.getKey() + "\t\t\t\t" + entry.getValue());
                            }
                        }catch (SQLException e){
                            throw new RuntimeException(e);
                        }

                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }
        }
    }


}
