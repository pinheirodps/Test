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
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;


public class CarTest {

    private static final String STATUS_OK = "OK";
    private static final String CONSTID = "id";
    ;
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
    public void testBuildOneCar() {
        Car car = buildCar();
        System.out.println(car);
    }


    @Test
    public void testFindAllCars() {
        List<Car> cars = carService.findAll();
        cars.forEach((car) -> System.out.println(car));
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
    public void testCarNotFound() throws CarNotFoundException {
        //test type
        thrown.expect(CarNotFoundException.class);
        //test message
        thrown.expectMessage(is("Car not found!"));
        Car car = carService.lookup("10");

    }


    private Car buildCar() {
        Car car = new CarBuilder("Smart", false, "Diesel", 1).build();
        return car;
    }

    private List<Car> getCars() {
        final List<Car> cars = new ArrayList<Car>() {
            {
                add(new CarBuilder().setBrand("Smart").setEcoFriendly(false).setFuelType("Diesel").setNumberOfModels(1).build());
                add(new CarBuilder().setBrand("Mercedes-Benz").setEcoFriendly(true).setFuelType("Hybrid").setNumberOfModels(3).build());
                add(new CarBuilder().setBrand("Mercedes-AMG").setEcoFriendly(true).setFuelType("Electric").setNumberOfModels(0).build());
            }
        };
        return cars;
    }


    public String getId() {
        return "1";
    }

    @Test
    public void testScript() {
        GroovyShell groovyShell = new GroovyShell();

        try {
            Script script = groovyShell.parse(new File("src/main/webapp/WEB-INF/index.groovy"));
            Binding binding = new Binding();
            Map bindings = script.getBinding().getVariables();
            when(request.getParameter("id")).thenReturn("1");

            //setting atributes in script here
            bindings.put("res", request);

            //run the script
            script.run();

            //getting object values after the script
            Car carObject = (Car) bindings.get("car");
            System.out.println(carObject.toString());


        } catch (CompilationFailedException | IOException ex) {
            //improve
            ex.printStackTrace();
        }
    }

    @Test
    public void testScript2() throws InterruptedException, IOException, ResourceException, ScriptException, groovy.util.ScriptException {
        Binding binding = new Binding();
        when(request.getParameter("id")).thenReturn("1");

        binding.setVariable("res", request);

        GroovyScriptEngine engine = new GroovyScriptEngine("D:\\challenge\\Daimler\\src\\main\\webapp\\WEB-INF");

        Car  car = (Car) engine.run("index.groovy", binding);
        System.out.println(car.toString());


    }

    @Test
    public void readFile() throws IOException {
            String fileName = "index.tpl";
            ClassLoader classLoader = new CarTest().getClass().getClassLoader();
            File file = new File(classLoader.getResource(fileName).getFile());
            String content = new String(Files.readAllBytes(file.toPath()));
            String scriptContent = content.substring(0, 215);
            String expressionsContent = content.substring(215, 536);

            List<String> list = new ArrayList<>(Arrays.asList(content));

          System.out.println(scriptContent);
        System.out.println("-------------------------------");
          System.out.println(expressionsContent);

    }

    @Test
    public void groovyScript() {
        /*
          <script type="server/groovy">
             import com.daimler.model.Car
             car = new Car()
             car.setBrand('William')
           </script>
         */
        final StringBuilder commandBuilder = new StringBuilder();
        commandBuilder.append("import com.daimler.model.Car\n");
        commandBuilder.append("car = new Car()\n");
        commandBuilder.append("car.setBrand('William')");

        GroovyShell shell = new GroovyShell();
        shell.evaluate(commandBuilder.toString());

        getValueByExpression(Arrays.asList("car.brand"), shell)
                .forEach((key, value) -> System.out.println(String.format("%s: %s", key, value)));
    }

    private Map<String, Object> getValueByExpression(final List<String> expressions, final GroovyShell shell) {
        return expressions.stream().collect(Collectors.toMap(expression-> expression, shell::evaluate));
    }

}
