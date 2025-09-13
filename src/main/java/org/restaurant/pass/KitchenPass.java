package org.restaurant.pass;

import org.restaurant.model.Food;
import org.restaurant.model.Order;

import java.util.ArrayDeque;
import java.util.Queue;

public class KitchenPass {
    
    private final Queue<Order> orders;
    
    private final Queue<Food> foods;
    
    public KitchenPass(){
        this.orders = new ArrayDeque<>(8);
        this.foods = new ArrayDeque<>(8);
    }
    
    public Queue<Order> getOrders() {
        return orders;
    }
    
    public Queue<Food> getFoods() {
        return foods;
    }
    
}
