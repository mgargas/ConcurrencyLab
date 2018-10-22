package company;

public class Incrementer extends Thread {
    private Counter counter;
    private BinarySemaphore binarySemaphore;
    @Override
    public void run() {
        for(int i=0; i < 1000000; i++){
            this.binarySemaphore.p();
            this.counter.increment();
            this.binarySemaphore.v();
        }
    }

    public Incrementer(Counter counter, BinarySemaphore binarySemaphore){
        this.binarySemaphore = binarySemaphore;
        this.counter = counter;
    }
}
