package ru.mipt.java2016.homework.g595.yakusheva.task4;

import javafx.application.Application;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mipt.java2016.homework.tests.task1.AbstractCalculatorTest;

/**
 * Created by Софья on 27.10.2016.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = NetworkCalculatorApplication.class)
public class MyFirstNetworkCalculatorTest extends AbstractCalculatorTest {

    @Autowired
    MyFirstCalculator calculator;
    @Autowired
    MyCalculatorDao dao;

    @Override
    protected MyFirstCalculator calc() {
        return calculator;
    }
}
