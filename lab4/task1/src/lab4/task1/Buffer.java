package lab4.task1;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    private Integer[] buffer;
    private final Lock bufferLock = new ReentrantLock();
    private final Condition[] noElementsReady;
    private Integer userPointer[];
    private Integer userJobCounter[];
    private int processorsAmount;

    public Buffer(int size, int processorsAmount){
        this.processorsAmount = processorsAmount;
        buffer = new Integer[size];
        for(int i=0; i<size; i++) buffer[i] = -1;
        noElementsReady = new Condition[processorsAmount+2];
        userPointer = new Integer[processorsAmount+2];
        userJobCounter = new Integer[processorsAmount+2];
        for(int i=0; i<processorsAmount+2; i++){
            noElementsReady[i] = bufferLock.newCondition();
            userPointer[i] = 0;
            userJobCounter[i] = 0;
        }
    }

    public void produce(){
        bufferLock.lock();
        System.out.println("Producer got the buffer");
        System.out.println(Arrays.toString(buffer));
        try{
            while(userJobCounter[0] == buffer.length) {
                System.out.println("Producer is waiting for the consumer");
                noElementsReady[processorsAmount+1].await();
            }
            System.out.println("Producer is working");
            userJobCounter[processorsAmount+1]--;
            buffer[userPointer[0]] = 0;
            userPointer[0] = (userPointer[0] + 1) % buffer.length;
            userJobCounter[0]++;
            noElementsReady[0].signal(); // notify the first processor
        } catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            System.out.println("Producer released the buffer");
            System.out.println(Arrays.toString(buffer));
            bufferLock.unlock();
        }
    }

    public void process(int processorId){
        bufferLock.lock();
        System.out.println("Processor#" + processorId + " got the buffer");
        System.out.println(Arrays.toString(buffer));
        try{
            while (userJobCounter[processorId - 1] == 0) {
                System.out.println("Processor#" + processorId + " is waiting for Processor#" + (processorId-1));
                noElementsReady[processorId - 1].await();
            }
            System.out.println("Processor#" + processorId + " is working.");
            Thread.sleep((long)(Math.random() * 1000));
            userJobCounter[processorId - 1]--;
            buffer[userPointer[processorId]]++;
            userPointer[processorId] = (userPointer[processorId] + 1) % buffer.length;
            userJobCounter[processorId]++;
            noElementsReady[processorId].signal(); // notify the next processor
        } catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            System.out.println("Processor#" + processorId + " released the buffer");
            System.out.println(Arrays.toString(buffer));
            bufferLock.unlock();
        }
    }

    public void consume(){
        bufferLock.lock();
        System.out.println("Consumer got the buffer");
        System.out.println(Arrays.toString(buffer));
        try{
            while(userJobCounter[processorsAmount] == 0) {
                System.out.println("Consumer is waiting for the producer");
                noElementsReady[processorsAmount].await();
            }
            System.out.println("Consumer is working.");
            userJobCounter[processorsAmount]--;
            buffer[userPointer[processorsAmount+1]] = -1; //consume
            userPointer[processorsAmount+1] = (userPointer[processorsAmount+1] + 1) % buffer.length;
            userJobCounter[processorsAmount+1]++;
            noElementsReady[processorsAmount+1].signal(); //notify the producer
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            System.out.println("Consumer released the buffer");
            System.out.println(Arrays.toString(buffer));
            bufferLock.unlock();
        }
    }






}
