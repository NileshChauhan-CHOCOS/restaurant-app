package org.restaurant.kitchen;

import org.restaurant.counter.Counter;

public class FoodDelegator {
    
    private final Counter counter;
    
    public FoodDelegator(Counter counter){
        this.counter = counter;
    }
    
    public void foodPrepared(){
        counter.deliverOrder();
    }
}
