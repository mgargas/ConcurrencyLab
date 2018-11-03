package lab4.task2;

import java.util.Random;

public class Producer extends Thread{
    private int producerId;
    private Buffer buffer;
    private Random random = new Random();

    public Producer(int producerId, Buffer buffer){
        this.producerId = producerId;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        buffer.put(random.nextInt((buffer.getSize()/2)), producerId);
    }
}
