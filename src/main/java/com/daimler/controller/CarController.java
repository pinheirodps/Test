package com.daimler.controller;

import com.daimler.model.Car;
import com.daimler.service.CarService;
import com.daimler.service.Impl.CarServiceImpl;
import com.daimler.service.exception.CarNotFoundException;
import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CarController extends HttpServlet {


    private CarService carService;
    private String scriptContent;


    private String expressionsContent;


    public CarController() {
        this.carService = new CarServiceImpl();
    }


    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try (PrintWriter writer = res.getWriter()) {
            //setting binding
            Binding binding = new Binding();
            binding.setVariable("res", req);
            Car car = null;
            try {
                car = carService.lookup(req.getParameter("id"));
            } catch (CarNotFoundException e) {
                System.out.println(e.getMessage());
                Logger.getLogger(CarController.class.getName()).log(Level.SEVERE, null, e.getMessage());
                writer.println(e.getMessage());
                return;
            }
            //filling the html with cars data from template groovy
            final StringBuilder html = getStringTemplate(binding);

            writer.println(html);

        } catch (IOException ex) {
            Logger.getLogger(CarController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ResourceException e) {
            e.printStackTrace();
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }


    private StringBuilder getStringTemplate(Binding binding) throws IOException, ResourceException, ScriptException {
        //getting groovy file template to compile html
        GroovyScriptEngine engine = new GroovyScriptEngine("D:\\challenge\\Daimler\\src\\main\\webapp\\WEB-INF");

        String modelsHtml = (String) engine.run("index.groovy", binding);

        final StringBuilder html = new StringBuilder();
        html.append(modelsHtml);
        return html;
    }

    //find car by id
    public Car lookup(String id) {
        try {
            return carService.lookup(id);
            //throws the expection if the car not found
        } catch (CarNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    //find all the cars
    public List<Car> findAll() {
        return carService.findAll();
    }


    public String getScriptContent() {
        return scriptContent;
    }

    public void setScriptContent(String scriptContent) {
        this.scriptContent = scriptContent;
    }

    public String getExpressionsContent() {
        return expressionsContent;
    }

    public void setExpressionsContent(String expressionsContent) {
        this.expressionsContent = expressionsContent;
    }

}
