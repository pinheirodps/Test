import com.daimler.model.Car;
import com.daimler.model.CarBuilder;
import groovy.lang.GroovyShell;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TestTemplate {

    private static final String EXPRESSION_REGEX = "[$]{1}[{]{1}([a-zA-Z0-9.]+)[}]{1}";
    private String scriptContent;
    private String expressionsContent;

    @Test
    public void groovyScript() throws IOException {

        final StringBuilder commandBuilder = new StringBuilder();
        commandBuilder.append("import com.daimler.model.Car \n");
        commandBuilder.append("import com.daimler.controller.CarController \n");
        commandBuilder.append("import java.util.* \n");
        commandBuilder.append("car = new Car()              \n");
        commandBuilder.append("car.setBrand('William')      \n");
        commandBuilder.append("car.setFuelType('Gasoline')  \n");
        commandBuilder.append(" final List<Car> cars = new ArrayList<Car>() {}  \n");
        //commandBuilder.append("car.setModels(cars)     \n");

        final GroovyShell shell = new GroovyShell();
        shell.evaluate(commandBuilder.toString());

        readFile();

        final Set<String> expressions = this.getExpressionsFromHTML(returnTemplate());
        final Map<String, Object> valueByExpression = this.getValueByExpression(expressions, shell);
        valueByExpression.forEach((key, value) -> System.out.println(String.format("%s: %s", key, value)));
    }

    private Map<String, Object> getValueByExpression(final Set<String> expressions, final GroovyShell shell) {
        return expressions.stream().collect(Collectors.toMap(expression-> expression, shell::evaluate));
    }

    private Set<String> getExpressionsFromHTML(final String html) {
        final Set<String> expressions = new HashSet<>();
        final Matcher matcher = Pattern.compile(EXPRESSION_REGEX).matcher(html);
        while (matcher.find()) {
            expressions.add(matcher.group(1));
        }
        return expressions;
    }

    private String returnTemplate() {
        final StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<html>                                                                          \n");
        htmlBuilder.append("    <head>                                                                      \n");
        htmlBuilder.append("        <title>${car.brand}</title>                                             \n");
        htmlBuilder.append("    </head>                                                                     \n");
        htmlBuilder.append("    <body>                                                                      \n");
        htmlBuilder.append("        <h1 title=\"${car.brand}\">${car.brand}</h1>                            \n");
        htmlBuilder.append("        <h2 data-if=\"${car.ecoFriendly}\" title=\"${car.fuelType}\">Fuel Type: ${car.fuelType}</h2> \n");
//        htmlBuilder.append("                                                          \n");
        htmlBuilder.append("        <div data-loop-model=\"car.models\">Model:</div>                        \n");
        htmlBuilder.append("    </body>                                                                     \n");
        htmlBuilder.append("</html>                                                                         \n");
        return htmlBuilder.toString();
    }

    @Test
    public void readFile() throws IOException {
        String fileName = "index.tpl";
        ClassLoader classLoader = new CarTest().getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        String content = new String(Files.readAllBytes(file.toPath()));
        scriptContent = content.substring(45, 204);
        expressionsContent = content.substring(217, 542);


        System.out.println(scriptContent);
        System.out.println("-------------------------------");
        System.out.println(expressionsContent);

    }

    private Car buildCar() {
        Car car = new CarBuilder().setBrand("Smart").setEcoFriendly(false).setFuelType("Diesel").setNumberOfModels(1).build();
        return car;
    }
}
