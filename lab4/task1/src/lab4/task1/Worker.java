package lab4.task1;

public class Worker extends Thread{
    private int workerId;
    private Buffer buffer;
    private int productsAmount;

    public Worker(int workerId, Buffer buffer, int productsAmount){
        this.workerId = workerId;
        this.buffer = buffer;
        this.productsAmount = productsAmount;
    }

    public Buffer getBuffer() {
        return buffer;
    }

    public int getWorkerId() {
        return workerId;
    }

    public int getProductsAmount() {
        return productsAmount;
    }
}
