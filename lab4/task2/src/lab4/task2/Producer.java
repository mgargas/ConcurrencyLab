package lab4.task2;

import java.util.Random;

public class Producer extends Thread{
    private int producerId;
    private Buffer buffer;
    private Random random = new Random();
    private String[] data;

    public Producer(int producerId, Buffer buffer){
        this.producerId = producerId;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        Long startTime = System.nanoTime();
        buffer.put(random.nextInt((buffer.getSize()/2)), producerId);
        Long time = System.nanoTime() - startTime;
        this.data = new String[]{String.valueOf(time), "P#"+String.valueOf(producerId)};
    }

    public String[] getData() {
        return data;
    }
}


