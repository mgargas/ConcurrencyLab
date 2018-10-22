public class Main {
    public static void main(String[] args) throws InterruptedException {
        Buffer buffer = new Buffer("");
        int n = 2;
        int m = 3;
        for(int i=0; i<n; i++){
            Producer producer = new Producer(buffer, m, i);
            Consumer consumer = new Consumer(buffer, m, i);

            Thread p = new Thread(producer);
            Thread c = new Thread(consumer);

            p.start();
            c.start();
        }
    }
}
