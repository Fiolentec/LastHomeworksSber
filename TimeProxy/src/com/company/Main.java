package com.company;

public class Main {

    public static void main(String[] args) {
        Proger proger = TimeProxy.create(new Programmer("Egueniy", 7));
        proger.writeBugs();
    }
}
