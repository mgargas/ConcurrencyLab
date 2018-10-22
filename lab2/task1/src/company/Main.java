package company;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        BinarySemaphore binarySemaphore = new BinarySemaphore(true);
        Counter counter = new Counter(0);
        Decrementer decrementer = new Decrementer(counter, binarySemaphore);
        Incrementer incrementer = new Incrementer(counter, binarySemaphore);

        decrementer.start();
        incrementer.start();

        decrementer.join();
        incrementer.join();

        System.out.println(counter.getValue());
    }
}
