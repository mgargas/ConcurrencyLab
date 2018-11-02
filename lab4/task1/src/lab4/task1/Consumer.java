package lab4.task1;

public class Consumer extends Thread {
    private int consumerId;
    private Buffer buffer;
    Consumer(Buffer buffer, int consumerId){
        this.consumerId = consumerId;
        this.buffer = buffer;
    }

    public int getConsumerId() {
        return consumerId;
    }

    @Override
    public void run() {
        buffer.consume();
    }
}
