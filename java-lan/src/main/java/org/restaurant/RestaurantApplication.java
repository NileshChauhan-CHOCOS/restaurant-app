package org.restaurant;

import org.restaurant.exception.RestaurantException;
import org.restaurant.server.RestaurantServer;

import java.util.concurrent.CountDownLatch;

public class RestaurantApplication {
    
    public static void main(String[] args) throws RestaurantException {
        RestaurantServer server = RestaurantServer.open("Suzy's Restaurant", 7645);
        server.serve();
        try{
            CountDownLatch latch = new CountDownLatch(1);
            latch.await();
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}