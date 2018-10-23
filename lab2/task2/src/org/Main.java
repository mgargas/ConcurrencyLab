package org;

public class Main {
    public static void main(String[] args){
        CountingSemaphore countingSemaphore = new CountingSemaphore(2);
        Shop shop = new Shop(countingSemaphore);
        for(int i=0; i<10; i++){
            Customer customer = new Customer(shop, i);
            customer.start();
        }
    }
}
