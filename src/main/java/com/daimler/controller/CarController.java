package com.daimler.controller;

import com.daimler.model.Car;
import com.daimler.service.CarService;
import com.daimler.service.CarServiceImpl;
import com.daimler.service.exception.CarNotFoundException;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import groovy.servlet.GroovyServlet;
import groovy.xml.MarkupBuilder;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

import javax.script.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CarController extends HttpServlet {


    private CarService carService;
    private String idParam = "";
    private String scriptContent;


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
            GroovyShell groovyShell = new GroovyShell();
            readFile();
            Script script = groovyShell.parse(scriptContent);
            //setting atributes in script here
            Binding binding = new Binding();
            binding.setVariable("res", req);
            //run the script
            script.setBinding(binding);
            Object car = script.run();

        //    idParam = req.getParameter("id");
             writer.println(car);
            // Forward to GSP file to display message
//            RequestDispatcher dispatcher = req
//                    .getRequestDispatcher("/index.tpl");
//            dispatcher.forward(req, res);            RequestDispatcher dispatcher = req
//                    .getRequestDispatcher("/index.tpl");
//            dispatcher.forward(req, res);
        } catch (IOException ex) {
            Logger.getLogger(CarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void readFile() throws IOException {
        String fileName = "index.tpl";
        ClassLoader classLoader = new CarController().getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        String content = new String(Files.readAllBytes(file.toPath()));
        scriptContent = content.substring(60, 227);
        String expressionsContent = content.substring(215, 536);

        List<String> list = new ArrayList<>(Arrays.asList(content));

        System.out.println(scriptContent);
        System.out.println("-------------------------------");
        System.out.println(expressionsContent);

    }

    public String returnTemplate(Car car){
        StringWriter writer = new StringWriter();


        String html = "<html>\n" +
                "    <script type=\"server/groovy\">\n" +
                "        import com.daimler.controller.CarController\n" +
                "        def cltr = new CarController()\n" +
                "        def id = res.getParameter(\"id\")\n" +
                "        def car = cltr.lookup(id)\n" +
                "</script>\n" +
                "    <head>\n" +
                "        <title>${car.brand}</title>\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        <h1 title=\"${car.brand}\">${car.brand}</h1>\n" +
                "        <h2 data-if=\"car.ecoFriendly\" title=\"${car.fuelType}\">Fuel Type:\n" +
                "            ${car.fuelType}</h2>\n" +
                "        <div data-loop-model=\"car.models\">Model: ${model}</div>\n" +
                "    </body>\n" +
                "</html>";

        return html;
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

    public String getScriptContent() {
        return scriptContent;
    }

    public void setScriptContent(String scriptContent) {
        this.scriptContent = scriptContent;
    }
}
