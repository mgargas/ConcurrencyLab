package lab4.task1;

public class Processor extends Thread {
    private int processorId;
    private Buffer buffer;
    public Processor(Buffer buffer, int processorId){
        this.processorId = processorId;
        this.buffer = buffer;
    }

    public int getProcessorId() {
        return processorId;
    }

    @Override
    public void run() {
        buffer.process(processorId);
    }
}
