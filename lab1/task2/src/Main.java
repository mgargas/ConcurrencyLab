public class Main {
    public static void main(String[] args) throws InterruptedException {
        Buffer buffer = new Buffer("");
        int n = 1;
        int m = 10;
        for(int i=0; i<n; i++){
            Producer producer = new Producer(buffer, m, i);
            Consumer consumer = new Consumer(buffer, m, i);
            producer.run();
            consumer.run();
        }
    }
}
