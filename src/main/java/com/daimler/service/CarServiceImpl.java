package com.daimler.service;

import com.daimler.model.Car;
import com.daimler.repository.CarRepository;
import com.daimler.repository.Impl.CarSpecification;
import com.daimler.service.exception.CarNotFoundException;

import java.util.List;
import java.util.Optional;

public class CarServiceImpl implements CarService {

    private CarRepository repository;

    public CarServiceImpl() {
        this.repository = new CarSpecification();
    }

    @Override
    public Optional<Car> lookup(String id)  {
       Optional<Car> car = repository.findById(id);
        if (!car.isPresent()){
            try {
                throw  new CarNotFoundException("Car not found!");
            } catch (CarNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
        return car;

    }

    @Override
    public List<Car> findAll() {
        return repository.findAll();
    }
}
