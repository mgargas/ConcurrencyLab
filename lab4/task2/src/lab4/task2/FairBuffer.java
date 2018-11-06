package lab4.task2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class FairBuffer implements IBuffer{
    private Integer size;
    private Integer counter;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition otherConsumers = lock.newCondition();
    private final Condition otherProducers = lock.newCondition();
    private final Condition firstConsumer = lock.newCondition();
    private final Condition firstProducer = lock.newCondition();


    public FairBuffer(int size){
        this.size = size;
        this.counter = 0;
    }


    public void put(int amount, int producerId){
        lock.lock();
        System.out.println("Producer#" + producerId + " got the buffer");
        try{
            System.out.println("Producer#" + producerId + " is trying to put " + amount);
            if(lock.hasWaiters(firstProducer)){
                otherProducers.await();
            }
            while(counter + amount > size){
                System.out.println("Producer#" + producerId + " is waiting to put "
                        + amount + " because there's room only for " + (size-counter));
                firstProducer.await();
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        counter += amount;
        System.out.println("Producer#" + producerId + " put " + amount + ". Counter: " + counter);
        otherProducers.signal();
        firstConsumer.signal();
        System.out.println("Producer#" + producerId + " released the buffer");
        lock.unlock();
    }

    public void take(int amount, int consumerId){
        lock.lock();
        System.out.println("Consumer#" + consumerId + " got the buffer");
        try{
            if(lock.hasWaiters(firstConsumer)){
                otherConsumers.await();
            }
            while(counter - amount < 0){
                System.out.println("Consumer#" + consumerId + " is waiting to take "
                        + amount + " because there's only " + counter);
                firstConsumer.await();
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        counter -= amount;
        System.out.println("Consumer#" + consumerId + " took " + amount + ". Counter: " + counter);
        otherConsumers.signal();
        firstProducer.signal();
        System.out.println("Consumer#" + consumerId + " released the buffer");
        lock.unlock();
    }

    public int getSize() {
        return size;
    }
}
