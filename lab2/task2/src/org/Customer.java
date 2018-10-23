package org;

public class Customer extends Thread{
    private Shop shop;
    private int customerID;

    public void run(){
        shop.doTheShopping(this.customerID);
    }

    Customer(Shop shop, int customerID){
        this.shop = shop;
        this.customerID = customerID;
    }

}
