package com.company;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter(0);
        Decrementer decrementer = new Decrementer(counter);
        Incrementer incrementer = new Incrementer(counter);

        decrementer.start();
        incrementer.start();

        decrementer.join();
        incrementer.join();

        System.out.println(counter.getValue());
    }
}
