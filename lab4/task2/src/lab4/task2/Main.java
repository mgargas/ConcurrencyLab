package lab4.task2;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){
        int size = 10;
        int producerAmount = 5;
        int consumerAmount = 5;

        Buffer buffer = new Buffer(size);
        List<Producer> producersList = new ArrayList<>();
        List<Consumer> consumersList = new ArrayList<>();
        for(int i=0; i<producerAmount; i++) producersList.add(new Producer(i, buffer));
        for(int i=0; i<consumerAmount; i++) consumersList.add(new Consumer(i, buffer));
        for(Consumer consumer : consumersList) consumer.start();
        for(Producer producer : producersList) producer.start();

    }
}
