package com.daimler.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Car {

    private String brand;
    private boolean ecoFriendly;
    private String fuelType;
    private List<String> models;

    public Car(CarBuilder carBuilder) {
        this.brand = carBuilder.getBrand();
        this.ecoFriendly = carBuilder.isEcoFriendly();
        this.fuelType = carBuilder.getFuelType();
        this.models =  new ArrayList<>();

        for (int it = 0; it < carBuilder.getNumberOfModels(); it++) {
            models.add("Model " + (it + 1));
        }
    }

    public Car() {

    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public boolean isEcoFriendly() {
        return ecoFriendly;
    }

    public void setEcoFriendly(boolean ecoFriendly) {
        this.ecoFriendly = ecoFriendly;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public List<String> getModels() {
        return models;
    }

    public void setModels(List<String> models) {
        this.models = models;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(brand, car.brand);
    }

    @Override
    public int hashCode() {

        return Objects.hash(brand);
    }

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", ecoFriendly=" + ecoFriendly +
                ", fuelType='" + fuelType + '\'' +
                ", models=" + models +
                '}';
    }
}
