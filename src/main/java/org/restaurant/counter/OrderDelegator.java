package org.restaurant.counter;

import org.restaurant.kitchen.Kitchen;

public class OrderDelegator {
    
    private final Kitchen kitchen;
    
    public OrderDelegator(Kitchen kitchen){
        this.kitchen = kitchen;
    }
    
    public Kitchen getKitchen() {
        return kitchen;
    }
    
    public void newOrder(){
        kitchen.prepareFood();
    }
}