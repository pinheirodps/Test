import com.daimler.controller.CarController;
import com.daimler.model.Car;
import com.daimler.model.CarBuilder;
import com.daimler.service.CarService;
import com.daimler.service.CarServiceImpl;
import com.daimler.service.exception.CarNotFoundException;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;


public class CarTest {

    private static CarService carService;

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
        ScriptEngineManager engineManager = new ScriptEngineManager();
        ScriptEngine scriptEngine = engineManager.getEngineByName("nashorn");

        String fileName = "src/main/resources/js/jsfile.js";
        String functionName = "doIt";
        try {

            scriptEngine.eval("load('" + fileName + "');");
            Invocable inv = (Invocable) scriptEngine;

            Car retValue = (Car) inv.invokeFunction(functionName, new CarTest());

            System.out.println(fileName + "@" + functionName + " returned " + retValue);

        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }




}
