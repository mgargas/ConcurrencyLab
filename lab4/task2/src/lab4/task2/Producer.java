package lab4.task2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Producer extends Thread{
    private int producerId;
    private IBuffer buffer;
    private Random random = new Random();
    private String[] data;
    List<Long> measurementsList = new ArrayList<>();

    public Producer(int producerId, IBuffer buffer){
        this.producerId = producerId;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        int portion = producerId % (buffer.getSize() / 2) + 1;
        for(int i=0;i<100;i++) {
            Long startTime = System.nanoTime();
            buffer.put(portion, producerId);
            //buffer.put(random.nextInt(buffer.getSize()/2)+1, producerId);
            Long time = System.nanoTime() - startTime;
            measurementsList.add(time);
        }
        double average = measurementsList.stream().mapToLong(a -> a).average().getAsDouble();
        this.data = new String[]{String.valueOf(portion), String.valueOf(average), "TAKE"};
    }

    public String[] getData() {
        return data;
    }
}


