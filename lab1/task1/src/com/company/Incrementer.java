package com.company;

public class Incrementer extends Thread {
    private Counter counter;
    @Override
    public void run() {
        for(int i=0; i < 1000000; i++){
            this.counter.increment();
        }
    }

    public Incrementer(Counter counter){
        this.counter = counter;
    }
}
