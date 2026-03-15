package com.car.model;

public class Car {
    private int id;
    private String registrationNumber;
    private String chasisNumber;
    private String registrationState;
    private String brand;
    private String model;
    private String variant;
    private Owner owner;
    private CarDetails carDetails;

    public String getRegistrationState() {
        return registrationState;
    }

    public void setRegistrationState(String registrationState) {
        this.registrationState = registrationState;
    }

    public Car(int id, String registrationNumber, String chasisNumber, String registrationState, String brand, String model, String variant, Owner owner, CarDetails carDetails) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.chasisNumber = chasisNumber;
        this.registrationState = registrationState;
        this.brand = brand;
        this.model = model;
        this.variant = variant;
        this.owner = owner;
        this.carDetails = carDetails;
    }

    public int getId() {
        return id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getChasisNumber() {
        return chasisNumber;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getVariant() {
        return variant;
    }

    public Owner getOwner() {
        return owner;
    }

    public CarDetails getCarDetails() {
        return carDetails;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public void setChasisNumber(String chasisNumber) {
        this.chasisNumber = chasisNumber;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public void setCarDetails(CarDetails carDetails) {
        this.carDetails = carDetails;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", chasisNumber='" + chasisNumber + '\'' +
                ", registrationState='" + registrationState + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", variant='" + variant + '\'' +
                ", owner=" + owner +
                ", carDetails=" + carDetails +
                '}';
    }
}
