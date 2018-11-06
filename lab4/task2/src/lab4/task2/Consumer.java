package lab4.task2;

import java.util.Random;

public class Consumer extends Thread{
    private int consumerId;
    private Buffer buffer;
    private Random random = new Random();
    private String[] data;


    public Consumer(int consumerId, Buffer buffer){
        this.consumerId = consumerId;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        for(int i=0;i<100;i++) {
            Long startTime = System.nanoTime();
            buffer.take((consumerId % (buffer.getSize() / 2)) + 1, consumerId);
            //buffer.take(random.nextInt(buffer.getSize()/2)+1, consumerId);
            Long time = System.nanoTime() - startTime;
            this.data = new String[]{String.valueOf(time), "PUT"};
        }
    }

    public String[] getData() {
        return data;
    }
}
