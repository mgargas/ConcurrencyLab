public class Producer implements Runnable {
    private Buffer buffer;
    private int counter;
    private int id;

    public Producer(Buffer buffer, int counter, int id) {
        this.buffer = buffer;
        this.counter = counter;
        this.id = id;
    }

    public void run() {

        for(int i = 0;  i < counter;   i++) {
            String message = "Producer#" + this.id + "-message#" + i;
            System.out.println("Producer#" + this.id + " is waiting to get the buffer");
            buffer.put(message);
            System.out.println("Producer#" + this.id + " produced the message: " + message);
        }

    }
}