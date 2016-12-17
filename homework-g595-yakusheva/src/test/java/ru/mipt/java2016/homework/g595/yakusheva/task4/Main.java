package ru.mipt.java2016.homework.g595.yakusheva.task4;

import ru.mipt.java2016.homework.g595.yakusheva.task1.*;

import java.util.Arrays;

/**
 * Created by Софья on 17.12.2016.
 */
public class Main {
    public static void main(String [] args){
        MyCalculatorDao nC = new MyCalculatorDao();
        nC.postConstruct();
        nC.insertVariable(new MyVariable( "a", 123.0));
        nC.insertVariable(new MyVariable( "b", 2.0));
        nC.insertFunction(new MyFunction("sqr","x*x", Arrays.asList( new String("x").split(","))));
    }

}