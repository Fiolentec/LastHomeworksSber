package com.company;

import com.company.classExample.ComplexPerson;
import com.company.classExample.Person;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        ArrayList<Person> people = new ArrayList<>(Arrays.asList(new Person("Name1",new ArrayList<>(Arrays.asList(1,2,3,45)),12),
                new Person("Name1",new ArrayList<>(Arrays.asList(1,2,3,45)),12)));
        ComplexPerson complexPerson = new ComplexPerson(people,2);
//        System.out.println(serializer.getDeclaredFields(complexPerson));
        Serializer serializer = new Serializer(new JsonFormatter(3));
        System.out.println(serializer.serialize(complexPerson));
    }
}
