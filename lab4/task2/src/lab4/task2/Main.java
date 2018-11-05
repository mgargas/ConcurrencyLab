package lab4.task2;

import com.opencsv.CSVWriter;
import lab3.task1.Printer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {


    public static void main(String[] args) throws IOException {
        int M = 1000;
        int producerAmount;
        int consumerAmount;

        File file = new File("./results.csv");
        FileWriter outputFile = new FileWriter(file);
        CSVWriter writer = new CSVWriter(outputFile);
        String[] header = { "bufferSize", "producersAmount", "consumersAmount", "time", "ID" };
        writer.writeNext(header);


        for(int size = 2 * M; M<1000000; M*=10) {
            for(producerAmount = consumerAmount = 10;
                producerAmount < 10000 && consumerAmount < 10000; producerAmount *= 10, consumerAmount *= 10) {
                Buffer buffer = new Buffer(size);
                List<Producer> producersList = new ArrayList<>();
                List<Consumer> consumersList = new ArrayList<>();
                for (int i = 0; i < producerAmount; i++) producersList.add(new Producer(i, buffer));
                for (int i = 0; i < consumerAmount; i++) consumersList.add(new Consumer(i, buffer));
                for (Consumer consumer : consumersList) consumer.start();
                for (Producer producer : producersList) producer.start();
                try {
                    for (Consumer consumer : consumersList) consumer.join();
                    for (Producer producer : producersList) producer.join();
                }catch (InterruptedException ie){
                    ie.printStackTrace();
                }
                for(Consumer consumer : consumersList){
                    String[] data = consumer.getData();
                    String[] csvRecord = {String.valueOf(size), String.valueOf(producerAmount),
                            String.valueOf(consumerAmount), data[0], data[1]};
                    writer.writeNext(csvRecord);
                }
                for(Producer producer : producersList){
                    String[] data = producer.getData();
                    String[] csvRecord = {String.valueOf(size), String.valueOf(producerAmount),
                            String.valueOf(consumerAmount), data[0], data[1]};
                    writer.writeNext(csvRecord);
                }

            }
        }
        writer.close();
    }

}
