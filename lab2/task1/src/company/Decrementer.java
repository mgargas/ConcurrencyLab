package company;


public class Decrementer extends Thread{
    private Counter counter;
    private BinarySemaphore binarySemaphore;

    @Override
    public void run() {
        for(int i=0; i < 1000000; i++){
            binarySemaphore.p();
            counter.decrement();
            binarySemaphore.v();
        }
    }

    public Decrementer(Counter counter, BinarySemaphore binarySemaphore){
        this.counter = counter;
        this.binarySemaphore = binarySemaphore;
    }
}
