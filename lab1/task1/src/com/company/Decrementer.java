package com.company;

public class Decrementer extends Thread{
    private Counter counter;

    @Override
    public void run() {
        for(int i=0; i < 1000000; i++){
            counter.decrement();
        }
    }

    public Decrementer(Counter counter){
        this.counter = counter;
    }
}
