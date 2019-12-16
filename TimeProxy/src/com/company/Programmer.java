package com.company;

public class Programmer implements Proger {
    String name;
    Integer grade;

    public Programmer(String name, Integer grade) {
        this.name = name;
        this.grade = grade;
    }

    public void writeBugs(){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException("Can't dance anymore");
        }
    }
}
