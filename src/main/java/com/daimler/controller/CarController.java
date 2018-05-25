package com.daimler.controller;

import com.daimler.model.Car;
import com.daimler.service.CarService;
import com.daimler.service.CarServiceImpl;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CarController extends HttpServlet {

    private ScriptEngineManager factory;
    private ScriptEngine engine;
    private ScriptObjectMirror script;
    private CarService carService;

    public CarController() {
        this.carService = new CarServiceImpl();
    }




    @Override
    public void init() throws ServletException {
        try {
            factory = new ScriptEngineManager();
            engine = factory.getEngineByName("nashorn");
            script = (ScriptObjectMirror)engine.eval("function(writer) {writer.print('Hello, World!');}");
        } catch (ScriptException ex) {
            Logger.getLogger(CarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try (PrintWriter writer = res.getWriter()) {
            script.call(null, writer);
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(CarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    public Optional<Car> lookup(String id){
        return carService.lookup(id);
    }

    public List<Car> findAll(){
        return carService.findAll();
    }







}
