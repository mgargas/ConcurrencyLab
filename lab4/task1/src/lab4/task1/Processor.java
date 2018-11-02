package lab4.task1;

public class Processor extends Worker {

    public Processor(int processorId, Buffer buffer,  int productsAmount){
        super(processorId, buffer, productsAmount);
    }


    @Override
    public void run() {
        for(int i=0;i<getProductsAmount(); i++) getBuffer().process(getWorkerId());
    }


}
