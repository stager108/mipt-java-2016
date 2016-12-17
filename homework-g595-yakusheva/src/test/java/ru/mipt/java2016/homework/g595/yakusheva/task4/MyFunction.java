package ru.mipt.java2016.homework.g595.yakusheva.task4;

import java.util.List;

/**
 * Created by Софья on 17.12.2016.
 */
public class MyFunction {
    String name;
    String function;
    List<String> arguments;


    public MyFunction(String name) {
        this.name = name;
    }
    public MyFunction(String name, String function, List<String> arguments){
        this.name = name;
        this.function = function;
        this.arguments = arguments;
    }

    public String getFunction() {
        return function;
    }

    public List<String> getArgs() {
        return arguments;
    }

    public int getNumberOfArguments() {
        return arguments.size();
    }

    public String getName() {
        return name;
    }
}
