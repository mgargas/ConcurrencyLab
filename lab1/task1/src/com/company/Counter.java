package com.company;

public class Counter {
    private int value;
    public synchronized void increment(){
        this.value++;
    }
    public synchronized void decrement(){
        this.value--;
    }
    public int getValue(){
        return this.value;
    }

    public Counter(int value){
        this.value = value;
    }
}
