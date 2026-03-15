package com.car.model;

public class CarDetails {
    private int id;
    private int year_of_purchase;
    private  int mileage;

    public CarDetails(int id, int year_of_purchase, int mileage) {
        this.id = id;
        this.year_of_purchase = year_of_purchase;
        this.mileage = mileage;
    }

    public CarDetails(){}

    public int getId() {
        return id;
    }

    public int getMileage() {
        return mileage;
    }

    public int getYear_of_purchase() {
        return year_of_purchase;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public void setYear_of_purchase(int year_of_purchase) {
        this.year_of_purchase = year_of_purchase;
    }

    @Override
    public String toString() {
        return "CarDetails{" +
                "id=" + id +
                ", year_of_purchase=" + year_of_purchase +
                ", mileage=" + mileage +
                '}';
    }
}
