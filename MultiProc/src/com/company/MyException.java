package com.company;

public class MyException extends Exception {
    MyException(String errorMessage, Throwable callableException) {
        super(errorMessage, callableException);
    }
}
