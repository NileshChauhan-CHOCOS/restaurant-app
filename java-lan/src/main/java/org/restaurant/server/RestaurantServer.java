package org.restaurant.server;

import org.restaurant.counter.Counter;
import org.restaurant.counter.OrderDelegator;
import org.restaurant.exception.RestaurantException;
import org.restaurant.kitchen.FoodDelegator;
import org.restaurant.kitchen.Kitchen;
import org.restaurant.pass.KitchenPass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@SuppressWarnings("unused")
public class RestaurantServer {
    
    private static final Logger Log = LoggerFactory.getLogger(RestaurantServer.class);
    
    private static Long nextId=0L;
    
    private final ServerSocket serverSocket;
    
    private String name;
    
    private final Long id;
    
    private Counter counter;
    
    private boolean isOpen = false;
    
    private final int port;
    
    private RestaurantServer(int port, Long id, String name){
        try{
            this.port = port;
            this.serverSocket = new ServerSocket(port);
            this.name = name;
            this.id = id;
        }catch (IOException e){
            throw new RestaurantException("Server cannot start at port " + port, e);
        }
    }
    
    private static synchronized Long nextId(){
        Long curId = nextId;
        nextId += 1;
        return curId;
    }
    
    public static RestaurantServer open(String name, int port){
        Long id = nextId();
        RestaurantServer server = new RestaurantServer(port, id, name);
        Log.info("\u001B[93mRestaurant server is starting--------\u001B[0m");
        server.start();
        Log.info("\u001B[92mRestaurant server started at port {}\u001B[0m", server.port);
        server.isOpen = true;
        return server;
    }
    
    public void serve() {
        Log.info("{} is open. Happy to serve you !!!",name);
        while (isOpen) {
            try{
                Socket clientSocket = serverSocket.accept();
                counter.takeOrder(clientSocket);
            }catch (IOException e){
                throw new RestaurantException("Restaurant server is not able to serve", e);
            }
        }
        Log.info("Outside the loop");
    }
    
    @SuppressWarnings("unused")
    public void stop() throws IOException {
        Log.info("We are closing. Pleased to server you!!!");
        this.serverSocket.close();
    }
    
    private void start() {
        KitchenPass kitchenPass = new KitchenPass();
        this.counter = new Counter(kitchenPass);
        FoodDelegator foodDelegator = new FoodDelegator(this.counter);
        Kitchen kitchen = new Kitchen(kitchenPass);
        kitchen.setDelegator(foodDelegator);
        OrderDelegator orderDelegator = new OrderDelegator(kitchen);
        this.counter.setOrderDelegator(orderDelegator);
    }
    
    public Long getId(){
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
