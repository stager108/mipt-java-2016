package ru.mipt.java2016.homework.g595.yakusheva.task4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
//import ru.mipt.java2016.homework.base.task1.Calculator;
import ru.mipt.java2016.homework.base.task1.ParsingException;
import java.util.List;

@RestController
public class CalculatorController {
    private static final Logger LOG = LoggerFactory.getLogger(CalculatorController.class);
    @Autowired
    private MyFirstCalculator calculator;
    private MyCalculatorDao myDao;

    @RequestMapping(path = "/ping", method = RequestMethod.GET, produces = "text/plain")
    public String echo() {
        return "OK\n";
    }

    @RequestMapping(path = "/", method = RequestMethod.GET, produces = "text/html")
    public String main(@RequestParam(required = false) String name) {
        if (name == null) {
            name = "Anonymous";
        }
        return "<html>" +
                "<head><title>FirstCalculatorApplication</title></head>" +
                "<body><h1>Hello, " + name + "!</h1> <br> <h1>Welcome to calculator! </h1></body>" +
                "</html>";
    }

    @RequestMapping(path = "/eval", method = RequestMethod.POST, consumes = "text/plain", produces = "text/plain")
    public String eval(@RequestBody String expression) throws ParsingException {
        LOG.debug("Evaluation request: [" + expression + "]");
        double result = calculator.calculate(expression);
        LOG.trace("Result: " + result);
        return Double.toString(result) + "\n";
    }

    @RequestMapping(path = "/variable", method = RequestMethod.GET)
    public @ResponseBody List<String> getVariablesNames() {
        return myDao.getVariablesNames();
    }

    @RequestMapping(path = "/variable/{variableName}", method = RequestMethod.GET)
    public @ResponseBody Double getVariable(@PathVariable String variableName) {
        return myDao.loadVariable(variableName).getValue();
    }

    @RequestMapping(path = "/variable/{variableName}", method = RequestMethod.PUT,
            consumes = "text/plain", produces = "text/plain")
    public @ResponseBody Boolean putVariable(@PathVariable String variableName,
                                             @RequestBody String expression) throws ParsingException {
        return myDao.insertVariable(new MyVariable(variableName, calculator.calculate(expression)));
    }

    @RequestMapping(path = "/variable/{variableName}", method = RequestMethod.DELETE)
    public Boolean deleteVariable(@PathVariable String variableName) {
        return myDao.removeVariable(variableName);
    }

    @RequestMapping(path = "/function", method = RequestMethod.GET)
    public @ResponseBody List<String> getFunctionsNames() {
        return myDao.getFunctionsNames();
    }

    @RequestMapping(path = "/function/{functionName}", method = RequestMethod.GET)
    public @ResponseBody String getString(@PathVariable String functionName) {
        return myDao.loadFunction(functionName).getFunction();
    }

    @RequestMapping(path = "/function/{functionName}", method = RequestMethod.DELETE)
    public Boolean deleteFunction(@PathVariable String functionName) {
        return myDao.removeFunction(functionName);
    }
    @RequestMapping(path = "/function/{functionName}", method = RequestMethod.PUT)
    public Boolean putFunction(@PathVariable String functionName,
                               @RequestParam(value = "arguments") List<String> arguments,
                               @RequestBody String functionExpression) throws ParsingException {
        return myDao.insertFunction(new MyFunction(functionName, functionExpression, arguments));
    }

}
