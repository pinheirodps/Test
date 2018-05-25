package com.daimler.service;

import com.daimler.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarService {

    Optional<Car> lookup(final String id);
    List<Car> findAll();
}
