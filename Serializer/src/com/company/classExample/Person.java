package com.company.classExample;

import java.util.ArrayList;

public class Person {
    private ArrayList<Integer> arrayList;
    private Integer age;
    String name;

    public Person(String name, ArrayList<Integer> arrayList, Integer age) {
        this.name = name;
        this.arrayList = arrayList;
        this.age = age;
    }
}
