package com.daimler.controller;

import com.daimler.model.Car;
import com.daimler.service.CarService;
import com.daimler.service.CarServiceImpl;
import com.daimler.service.exception.CarNotFoundException;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
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
            GroovyShell groovyShell = new GroovyShell();
            readFile();
            Script script = groovyShell.parse(scriptContent);

            //setting atributes in script here
            Binding binding = new Binding();
            binding.setVariable("res", req);
            //run the script

            script.setBinding(binding);
            Car car = (Car) script.run();
            if (car == null) {
                writer.println("Car not found");
                return;
            }
            script.setProperty("car", car);

            final StringBuilder html = getStringTemplate(script);

            writer.println(html);

        } catch (IOException ex) {
            Logger.getLogger(CarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private StringBuilder getStringTemplate(Script script) {
        Object brand = script.evaluate("car.brand");
        Object fuelType = script.evaluate("car.fuelType");
        Object ecoFriendly = script.evaluate("car.ecoFriendly");
        String visible = ecoFriendly.equals(Boolean.valueOf("false")) ? "none" : "block";

        final StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n" +
                "<html><head><title>" + brand + "</title><head>    " +
                "<h1 title=\"" + brand + "\">" + brand + "</h1>" +
                " <h2 data-if=\"" + ecoFriendly + "\" title=\"" + fuelType + "\" style=\"display:" + visible + ";\">Fuel Type:" + fuelType + "</h2>" +

                "</body>\n" +
                "</html>");
        return html;
    }

    public void readFile() throws IOException {
        String fileName = "index.tpl";
        ClassLoader classLoader = new CarController().getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        String content = new String(Files.readAllBytes(file.toPath()));
        scriptContent = content.substring(45, 204);
        expressionsContent = content.substring(217, 542);

        System.out.println(scriptContent);
        System.out.println("-------------------------------");
        System.out.println(expressionsContent);

    }


    public Car lookup(String id) {
        try {
            return carService.lookup(id);
        } catch (CarNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

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
