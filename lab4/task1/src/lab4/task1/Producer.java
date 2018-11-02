package lab4.task1;

public class Producer extends Thread {
    private int producerId = 0;
    private Buffer buffer;

    public Producer(Buffer buffer){
        this.buffer = buffer;
    }

    public int getProducerId() {
        return producerId;
    }

    @Override
    public void run() {
        buffer.produce();
    }
}
