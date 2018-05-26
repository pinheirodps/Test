package com.daimler.service;

import com.daimler.model.Car;
import com.daimler.service.exception.CarNotFoundException;

import java.util.List;
import java.util.Optional;

public interface CarService {

    Car lookup(final String id) throws CarNotFoundException;
    List<Car> findAll();
}
