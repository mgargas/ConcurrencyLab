package org;

public class Shop {
    private CountingSemaphore countingSemaphore;

    Shop(CountingSemaphore countingSemaphore){
        this.countingSemaphore = countingSemaphore;
    }

    public void doTheShopping(int customerID){
        System.out.println("Customer#" + customerID + " is waiting for the basket. Number of baskets: " + countingSemaphore.getCounter());
        countingSemaphore.p();
        System.out.println("Customer#" + customerID + " is doing the shopping. Number of baskets: " + countingSemaphore.getCounter());
        countingSemaphore.v();
        System.out.println("Customer#" + customerID + " is putting back the basket. Number of baskets: " + countingSemaphore.getCounter());

    }
}
