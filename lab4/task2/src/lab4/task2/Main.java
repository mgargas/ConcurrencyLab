package lab4.task2;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {


    public static void main(String[] args) throws IOException {
        Integer M = 1000;
        Integer[] bufferSizes = {2*M, 2*M*10, 2*M*100};
        Integer[] workerAmounts = {10, 100, 1000};

        File file = new File("./fair_results.csv");
        FileWriter outputFile = new FileWriter(file);
        CSVWriter writer = new CSVWriter(outputFile);
        String[] header = { "bufferSize","portion", "producersAmount", "consumersAmount", "time", "ID" };
        writer.writeNext(header);


        for(Integer size : bufferSizes) {
            for(Integer workerAmount : workerAmounts) {
                System.out.println("Creating buffer with size: " + size);
                //IBuffer buffer = new NaiveBuffer(size);
                IBuffer buffer = new FairBuffer(size);
                List<Producer> producersList = new ArrayList<>();
                List<Consumer> consumersList = new ArrayList<>();

                for (int i = 0; i < workerAmount; i++) producersList.add(new Producer(i, buffer));
                for (int i = 0; i < workerAmount; i++) consumersList.add(new Consumer(i, buffer));

                for (Consumer consumer : consumersList) consumer.start();
                for (Producer producer : producersList) producer.start();

                try {
                    for (Consumer consumer : consumersList) consumer.join();
                    for (Producer producer : producersList) producer.join();
                }catch (InterruptedException ie){
                    ie.printStackTrace();
                }
                System.out.println("Writing to file. Size: " + size + " WorkersAmount: " + workerAmount);
                for(Consumer consumer : consumersList){
                    String[] data = consumer.getData();
                    String[] csvRecord = {String.valueOf(size),data[0], String.valueOf(workerAmount),
                            String.valueOf(workerAmount), data[1], data[2]};
                    writer.writeNext(csvRecord);
                }

                for(Producer producer : producersList){
                    String[] data = producer.getData();
                    String[] csvRecord = {String.valueOf(size),data[0], String.valueOf(workerAmount),
                            String.valueOf(workerAmount), data[1], data[2]};
                    writer.writeNext(csvRecord);
                }
            }
        }
        writer.close();
    }

}
