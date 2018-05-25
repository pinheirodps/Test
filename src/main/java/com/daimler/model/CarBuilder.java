package com.daimler.model;


public class CarBuilder {
    private String brand;
    private boolean ecoFriendly;
    private String fuelType;
    private int numberOfModels;

    public CarBuilder(String brand, boolean ecoFriendly, String fuelType, int numberOfModels) {
        this.brand = brand;
        this.ecoFriendly = ecoFriendly;
        this.fuelType = fuelType;
        this.numberOfModels = numberOfModels;
    }

    public CarBuilder() {

    }

    public CarBuilder setBrand(final String brand) {
        this.brand = brand;
        return this;
    }

    public CarBuilder setEcoFriendly(final boolean ecoFriendly) {
        this.ecoFriendly = ecoFriendly;
        return this;
    }

    public CarBuilder setFuelType(final String fuelType) {
        this.fuelType = fuelType;
        return this;
    }

    public CarBuilder setNumberOfModels(final int numberOfModels) {
        this.numberOfModels = numberOfModels;
        return this;
    }

    public Car build() {
        return new Car(this);
    }

    public String getBrand() {
        return brand;
    }

    public boolean isEcoFriendly() {
        return ecoFriendly;
    }

    public String getFuelType() {
        return fuelType;
    }

    public int getNumberOfModels() {
        return numberOfModels;
    }
}
