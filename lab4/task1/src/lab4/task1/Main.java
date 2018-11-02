package lab4.task1;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){
        int processorsAmount = 2;
        int size = 2;
        Buffer buffer = new Buffer(size, processorsAmount);
        Producer producer = new Producer(buffer);
        List<Processor> processorsList = new ArrayList<>();
        for(int i=0; i<processorsAmount; i++) processorsList.add(new Processor(buffer, i+1));
        Consumer consumer = new Consumer(buffer, processorsAmount+1);

        consumer.start();
        producer.start();
        for(Processor processor : processorsList) processor.start();
    }
}
