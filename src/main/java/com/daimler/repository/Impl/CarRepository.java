package com.daimler.repository.Impl;

import com.daimler.model.Car;
import com.daimler.model.CarBuilder;
import com.daimler.repository.genericRepository.GenericRepository;
import com.daimler.repository.genericRepository.impl.Repository;

import java.util.ArrayList;
import java.util.List;

public class CarRepository extends Repository<Car> implements GenericRepository {


    @Override
    public Car lookup(String id) {
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
