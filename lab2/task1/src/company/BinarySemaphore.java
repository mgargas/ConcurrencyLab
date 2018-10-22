package company;

public class BinarySemaphore {
    boolean state;

    public BinarySemaphore(boolean state){
        this.state = state;
    }

    public synchronized void v(){
        if (!state) {
            state = true;
            notifyAll();
        }
    }

    public synchronized void p(){
        while (!state){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        state = false;
    }
}
  