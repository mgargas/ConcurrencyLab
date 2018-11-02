package lab4.task1;

public class Producer extends Worker {

    public Producer(Buffer buffer, int productsAmount){
        super(0, buffer, productsAmount);
    }

    @Override
    public void run() {
        for(int i=0;i<getProductsAmount(); i++) getBuffer().produce();
    }
}
