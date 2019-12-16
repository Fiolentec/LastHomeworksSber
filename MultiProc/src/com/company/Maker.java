package com.company;

import java.util.concurrent.Callable;

public class Maker implements Callable<Integer> {
    private final int res;
    private boolean exception;

    public Maker(int res, boolean exception) {
        this.res = res;
        this.exception = exception;
    }

    @Override
    public Integer call() throws Exception {
        int result = 0;
        for (int i = 0; i < res; i++) {
            result += i;
        }
        if (exception){
            throw new Exception("exception message");
        }
        return result;
    }
}
