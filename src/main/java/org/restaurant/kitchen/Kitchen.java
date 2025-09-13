package org.restaurant.kitchen;

import org.restaurant.kitchen.factory.ChefThreadFactory;
import org.restaurant.model.Food;
import org.restaurant.model.Order;
import org.restaurant.pass.KitchenPass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class Kitchen {
    
    private static final Logger Log = LoggerFactory.getLogger(Kitchen.class);
    
    private final KitchenPass pass;
    
    private final ExecutorService chefs;
    
    private final ReentrantLock lock;
    
    private FoodDelegator delegator;
    
    public Kitchen(KitchenPass kitchenPass){
        this.pass = kitchenPass;
        this.chefs = Executors.newFixedThreadPool(2, new ChefThreadFactory());
        this.lock = new ReentrantLock();
    }
    
    public void prepareFood() {
        chefs.submit(()-> {
            try {
                lock.lock();
                Log.info("{} is preparing food",Thread.currentThread().getName());
                Order order = pass.getOrders().poll();
                lock.unlock();
                if (order != null){
                    Thread.sleep(5000);
                    lock.lock();
                    pass.getFoods().offer(new Food(order, order.getItem() + " Cuisine"));
                    lock.unlock();
                }
                delegator.foodPrepared();
            }catch (Exception e){
                Thread.currentThread().interrupt();
                Log.error("Interrupted on preparing food -> {}", Arrays.asList(e.getStackTrace()));
            }finally {
                lock.unlock();
            }
        });
    }
    
    public KitchenPass getPass() {
        return pass;
    }
    
    public ExecutorService getChefs() {
        return chefs;
    }
    
    public ReentrantLock getLock() {
        return lock;
    }
    
    public FoodDelegator getDelegator() {
        return delegator;
    }
    
    public void setDelegator(FoodDelegator delegator) {
        this.delegator = delegator;
    }
}
