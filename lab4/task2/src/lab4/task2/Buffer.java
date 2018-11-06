package lab4.task2;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    private Integer size;
    private Integer counter;
    private final Lock lock = new ReentrantLock();
    private final Condition notEnoughRoom = lock.newCondition();
    private final Condition notEnoughProducts = lock.newCondition();


    public Buffer(int size){
        this.size = size;
        this.counter = 0;
    }


    public void put(int amount, int producerId){
        lock.lock();
        System.out.println("Producer#" + producerId + " got the buffer");
        try{
            System.out.println("Producer#" + producerId + " is trying to put " + amount);
            while(counter + amount > size){
                System.out.println("Producer#" + producerId + " is waiting to put "
                        + amount + " because there's room only for " + (size-counter));
                notEnoughRoom.await();
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        counter += amount;
        System.out.println("Producer#" + producerId + " put " + amount + ". Counter: " + counter);
        notEnoughProducts.signal();
       // notEnoughRoom.signal();
        System.out.println("Producer#" + producerId + " released the buffer");
        lock.unlock();
    }

    public void take(int amount, int consumerId){
        lock.lock();
        System.out.println("Consumer#" + consumerId + " got the buffer");
        try{
            while(counter - amount < 0){
                System.out.println("Consumer#" + consumerId + " is waiting to take "
                        + amount + " because there's only " + counter);
                notEnoughProducts.await();
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        counter -= amount;
        System.out.println("Consumer#" + consumerId + " took " + amount + ". Counter: " + counter);
        notEnoughRoom.signal();
       // notEnoughProducts.signal();
        System.out.println("Consumer#" + consumerId + " released the buffer");
        lock.unlock();
    }

    public int getSize() {
        return size;
    }
}
