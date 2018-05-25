import com.daimler.controller.CarController;
import com.daimler.model.Car;
import com.daimler.model.CarBuilder;
import com.daimler.service.CarService;
import com.daimler.service.CarServiceImpl;
import com.daimler.service.exception.CarNotFoundException;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;


public class CarTest {

    private static CarService carService;


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
    public void testFindCarById(){
        Car testCar = buildCar();
        Optional<Car> car = carService.lookup("1");
        assertTrue(testCar.equals(car.get()));
        System.out.println(car);
    }

    @Test(expected = CarNotFoundException.class)
    public void testCarNotFound() throws CarNotFoundException {
        Optional<Car> car = carService.lookup("10");
        System.out.println(car);
        assertFalse(carThrowException());
    }


    private boolean carThrowException() throws CarNotFoundException {
        throw new CarNotFoundException();
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







}
