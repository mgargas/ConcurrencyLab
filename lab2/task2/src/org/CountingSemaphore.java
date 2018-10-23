package org;

public class CountingSemaphore {
    private int counter;
    int MAX = 2;

    public CountingSemaphore(int counter){
        this.counter = counter;
    }

    public synchronized void v(){
        if(this.counter < MAX){
            counter++;
        }
        notifyAll();
    }

    public synchronized void p(){
        while (this.counter == 0){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        counter--;
    }

    public synchronized int getCounter(){
        return this.counter;
    }
}
