package com.company;

import java.util.concurrent.Callable;

public class Task<T> {

    private volatile boolean ready;
    private volatile boolean callAssigned;
    private final Callable<? extends T> callable;
    private T result;
    private MyException taskException;

    public Task(Callable<? extends T> callable) {
        this.ready = false;
        this.callAssigned = false;
        this.result = null;
        this.taskException = null;
        this.callable = callable;
    }

    public T get() throws MyException {
        boolean doCall = false;
        if (!callAssigned) {
            synchronized (this) {
                if (!callAssigned) {
                    doCall = callAssigned = true;
                }
            }
        }

        if (doCall) {
            call();
            synchronized (this) {
                notifyAll();
            }
        }

        return getResult();
    }

    private T getResult() throws MyException {
        while (!ready) {
            sleep();
        }

        if (taskException != null) {
            throw taskException;
        }
        return result;
    }

    private void call() {
        try {
            result = callable.call();
        } catch (Exception e) {
            taskException = new MyException("Exception came from call", e);
        } finally {
            ready = true;
        }
    }

    private synchronized void sleep() {
        try {
            this.wait();
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
    }
}
