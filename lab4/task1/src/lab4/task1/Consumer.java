package lab4.task1;

public class Consumer extends Worker {

    Consumer(int consumerId, Buffer buffer, int productsAmount){
        super(consumerId, buffer, productsAmount);
    }


    @Override
    public void run() {
        for(int i=0;i<getProductsAmount(); i++) getBuffer().consume();
    }
}
