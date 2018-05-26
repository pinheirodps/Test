import com.daimler.controller.CarController;
import com.daimler.model.Car;
import com.daimler.model.CarBuilder;
import com.daimler.service.CarService;
import com.daimler.service.CarServiceImpl;
import com.daimler.service.exception.CarNotFoundException;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import org.codehaus.groovy.control.CompilationFailedException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;


public class CarTest {

    private static final String STATUS_OK ="OK" ;
    private static final String CONSTID ="id"; ;
    private static CarService carService;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @BeforeClass
    public static void initCar() {
        carService = new CarServiceImpl();
    }

    @Test
    public void testBuildOneCar(){
        Car car = buildCar();
        System.out.println(car);
    }



    @Test
    public void testFindAllCars(){
        List<Car> cars = carService.findAll();
        cars.forEach((car)-> System.out.println(car));
        assertTrue(cars.equals(getCars()));
    }



    @Test
    public void testFindCarById() throws CarNotFoundException {
        Car testCar = buildCar();
        Car car = carService.lookup("1");
        assertTrue(testCar.equals(car));
        System.out.println(car);
    }

    @Test
    public void testCarNotFound() throws CarNotFoundException  {
        //test type
        thrown.expect(CarNotFoundException.class);
        //test message
        thrown.expectMessage(is("Car not found!"));
        Car car = carService.lookup("10");

    }


    private Car buildCar(){
        Car car = new CarBuilder("Smart", false, "Diesel", 1).build();
        return car;
    }

    private List<Car> getCars(){
        final List<Car> cars = new ArrayList<Car>() {
            {
                add(new CarBuilder().setBrand("Smart").setEcoFriendly(false).setFuelType("Diesel").setNumberOfModels(1).build());
                add(new CarBuilder().setBrand("Mercedes-Benz").setEcoFriendly(true).setFuelType("Hybrid").setNumberOfModels(3).build());
                add(new CarBuilder().setBrand("Mercedes-AMG").setEcoFriendly(true).setFuelType("Electric").setNumberOfModels(0).build());
            }
        };
        return cars;
    }


    public String getId(){
        return "1";
    }

    @Test
    public void testScript() {
        GroovyShell groovyShell = new GroovyShell();

        try {
            Script script = groovyShell.parse(new File("src/main/webapp/WEB-INF/index.groovy"));
            Map bindings = script.getBinding().getVariables();
            when(request.getParameter("id")).thenReturn("1");

            //setting atributes in script here
            bindings.put("car",request);

            //run the script
            script.run();

            //getting object values after the script
             Object carObject =  bindings.get("car");
            System.out.println(carObject.toString());


        } catch (CompilationFailedException | IOException ex) {
            //improve
            ex.printStackTrace();
        }
    }

    @Test
    public void testScript2() throws InterruptedException, IOException, ResourceException, ScriptException, groovy.util.ScriptException {
        Binding binding = new Binding();
        binding.setVariable(CONSTID, "1");
        GroovyScriptEngine engine = new GroovyScriptEngine("D:\\challenge\\Daimler\\src\\main\\webapp\\WEB-INF");

        while (true) {
            Object ret = engine.run("index.groovy", binding);
            if(STATUS_OK.equals(ret.toString())){
                if(binding.hasVariable("car")){
                    Object details = binding.getVariable("car");
                    System.out.println(details);
                }
            }else{

                System.out.println("Something wrong in groovy");
            }
            Thread.sleep(3000);
        }
    }



}
