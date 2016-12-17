package ru.mipt.java2016.homework.g595.yakusheva.task4;

/**
 * Created by Софья on 16.12.2016.
 */
public class MyVariable {
    String name;
    Double value;


    public MyVariable(String name) {
        this.name = name;
    }
    public MyVariable(String name, Double value) {
        this.name = name;
        this.value = value;
    }
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        if (!super.equals(object)) {
            return false;
        }
        MyVariable that = (MyVariable) object;
        return java.util.Objects.equals(name, that.name) &&
                java.util.Objects.equals(value, that.value);
    }

    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), name, value);
    }

    public java.lang.String toString() {
        return "MyVariable{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }

    public String getName(){
        return new String(name);
    }

    public Double getValue() {
        return value;
    }
}

