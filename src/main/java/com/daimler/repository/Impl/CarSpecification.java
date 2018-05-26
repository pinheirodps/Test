package com.daimler.repository.Impl;

import com.daimler.model.Car;
import com.daimler.model.CarBuilder;
import com.daimler.repository.CarRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarSpecification implements CarRepository {


    @Override
    public Car findById(String id) {
        if (id != null && !id.isEmpty()) {
            final int carId = Integer.valueOf(id);
            if (carId > 0 && carId <= findAll().size()) {
                return (findAll().get(carId - 1));
            }
        }

        return null;
    }

    @Override
    public List<Car> findAll() {
        final List<Car> cars = new ArrayList<Car>() {
            {
                add(new CarBuilder().setBrand("Smart").setEcoFriendly(false).setFuelType("Diesel").setNumberOfModels(1).build());
                add(new CarBuilder().setBrand("Mercedes-Benz").setEcoFriendly(true).setFuelType("Hybrid").setNumberOfModels(3).build());
                add(new CarBuilder().setBrand("Mercedes-AMG").setEcoFriendly(true).setFuelType("Electric").setNumberOfModels(0).build());
            }
        };
        return cars;
    }
}
