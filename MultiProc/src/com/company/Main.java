package com.company;

import java.util.concurrent.Callable;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        int n = 1000000;
        int num = 3;
        Callable<Integer> callable = new Maker(n, false);
        Task<Integer> task = new Task<>(callable);

        Thread[] threads = new Thread[num];
        for (int i = 0; i < num; ++i) {
            threads[i] = new Thread(() -> {
                try {
                    System.out.println(task.get());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        for (int i = 0; i < num; ++i) {
            threads[i].start();
        }
        for (int i = 0; i < num; ++i) {
            threads[i].join();
        }
    }
}
