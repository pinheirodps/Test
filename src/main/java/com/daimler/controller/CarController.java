package com.daimler.controller;

import com.daimler.model.Car;
import com.daimler.service.CarService;
import com.daimler.service.CarServiceImpl;
import com.daimler.service.exception.CarNotFoundException;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

import javax.script.*;
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


    private CarService carService;
    private String idParam = "";

    public CarController() {
        this.carService = new CarServiceImpl();
    }


    public CarController(String idParam) {
        this.idParam = idParam;
        this.carService = new CarServiceImpl();
    }




    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try (PrintWriter writer = res.getWriter()) {

             ScriptEngineManager engineManager = new ScriptEngineManager();
             ScriptEngine scriptEngine = engineManager.getEngineByName("nashorn");
             Invocable inv = (Invocable) scriptEngine;
             String fileName = "src/main/resources/js/jsfile.js";
             String functionName = "getId";

            idParam = req.getParameter("id");
            scriptEngine.eval("load('" + fileName + "');");
            Car retValue = (Car) inv.invokeFunction(functionName, new CarController(idParam));
            if (retValue == null){
                String message = "Car not found!";
                writer.println(message);

            }else{
                writer.println(retValue);
            }
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(CarController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    public String getId(){
        return idParam;
    }

    public Car lookup(String id) {
        try {
            return carService.lookup(id);
        } catch (CarNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Car> findAll(){
        return carService.findAll();
    }


    public String getIdParam() {
        return idParam;
    }

    public void setIdParam(String idParam) {
        this.idParam = idParam;
    }
}
