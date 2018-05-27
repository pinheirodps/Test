package com.daimler.service;

import com.daimler.model.Car;
import com.daimler.service.exception.CarNotFoundException;
import com.daimler.service.generic.GenericService;

import java.util.List;

public interface CarService extends GenericService<Car> {

    Car lookup(final String id) throws CarNotFoundException;
    List<Car> findAll();
}
