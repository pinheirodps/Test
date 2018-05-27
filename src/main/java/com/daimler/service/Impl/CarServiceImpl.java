package com.daimler.service.Impl;

import com.daimler.model.Car;
import com.daimler.repository.Impl.CarRepository;
import com.daimler.service.CarService;
import com.daimler.service.exception.CarNotFoundException;
import com.daimler.service.generic.impl.GenericServiceImpl;

import java.util.List;

public  class CarServiceImpl extends GenericServiceImpl<Car> implements CarService {

    private CarRepository carRepository;

    public CarServiceImpl() {
        this.carRepository = new CarRepository();
    }

    @Override
    public Car lookup(String id) throws CarNotFoundException {
       Car car = carRepository.lookup(id);
        if (car!=null){
            return car;
        }else {
            throw new CarNotFoundException("Car not found!");
        }
    }


    @Override
    public List<Car> findAll() {
        return carRepository.findAll();
    }
}
