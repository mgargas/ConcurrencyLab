public class Consumer implements Runnable {
    private Buffer buffer;
    private int counter;
    private int id;

    public Consumer(Buffer buffer, int counter, int id) {
        this.buffer = buffer;
        this.counter = counter;
        this.id = id;
    }

    public void run() {

        for(int i = 0;  i < counter;   i++) {
            System.out.println("Consumer#" + this.id + " is waiting to get the buffer");
            String message = buffer.take(i);
//            System.out.println("Consumer#" + this.id + " got the message: " + message);
        }

    }
}
