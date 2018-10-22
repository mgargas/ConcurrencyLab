public class Buffer {
    private String message;
    Buffer(String message){
        this.message = message;
    }

    public synchronized void put(String message, int who) {
        while (!this.message.equals("")){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.message = message;
        System.out.println("Produce#" + who + " produced the message: " + this.message);
        notify();
    }

    public synchronized String take(int who) {
        while (this.message.equals("")){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String m = this.message;
        this.message = "";
        System.out.println("Consumer#" + who + " got the message: " + m);
        notify();
        return m;
    }
}
