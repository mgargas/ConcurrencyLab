package lab4.task2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Consumer extends Thread{
    private int consumerId;
    private IBuffer buffer;
    private Random random = new Random();
    private String[] data;
    List<Long> measurementsList = new ArrayList<>();


    public Consumer(int consumerId, IBuffer buffer){
        this.consumerId = consumerId;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        int portion = consumerId % (buffer.getSize() / 2) + 1;
        for(int i=0;i<100;i++) {
            Long startTime = System.nanoTime();
            buffer.take(portion, consumerId);
            //buffer.take(random.nextInt(buffer.getSize()/2)+1, consumerId);
            Long time = System.nanoTime() - startTime;
            measurementsList.add(time);
        }
        double average = measurementsList.stream().mapToLong(a -> a).average().getAsDouble();
        this.data = new String[]{String.valueOf(portion), String.valueOf(average), "PUT"};
    }

    public String[] getData() {
        return data;
    }
}
