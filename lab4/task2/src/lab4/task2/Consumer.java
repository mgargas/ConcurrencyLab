package lab4.task2;

import java.util.Random;

public class Consumer extends Thread{
    private int consumerId;
    private Buffer buffer;
    private Random random = new Random();

    public Consumer(int consumerId, Buffer buffer){
        this.consumerId = consumerId;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        buffer.take(random.nextInt((buffer.getSize()/2)), consumerId);
    }
}
