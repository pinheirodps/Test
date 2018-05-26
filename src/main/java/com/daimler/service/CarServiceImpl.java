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
    public Car lookup(String id) throws CarNotFoundException {
       Car car = repository.findById(id);
        if (car!=null){
            return car;
        }else {
            throw new CarNotFoundException("Car not found!");
        }
    }

    @Override
    public List<Car> findAll() {
        return repository.findAll();
    }
}
