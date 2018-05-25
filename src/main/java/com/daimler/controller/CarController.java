package com.daimler.controller;

import com.daimler.model.Car;
import com.daimler.service.CarService;
import com.daimler.service.CarServiceImpl;

import javax.servlet.http.HttpServlet;
import java.util.List;
import java.util.Optional;

public class CarController extends HttpServlet {

    private CarService carService;

    public CarController() {
        this.carService = new CarServiceImpl();
    }

    public Optional<Car> lookup(String id){
        return carService.lookup(id);
    }

    public List<Car> findAll(){
        return carService.findAll();
    }
}
