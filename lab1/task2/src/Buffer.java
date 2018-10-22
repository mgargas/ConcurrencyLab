public class Buffer {
    private String message;
    Buffer(String message){
        this.message = message;
    }

    public synchronized void put(String message) {
        while (!this.message.equals("")){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.message = message;
        notify();
    }

    public synchronized String take() {
        while (this.message.equals("")){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String m = this.message;
        this.message = "";
        notify();
        return m;
    }
}
