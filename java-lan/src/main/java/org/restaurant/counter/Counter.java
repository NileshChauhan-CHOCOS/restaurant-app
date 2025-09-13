package org.restaurant.counter;

import org.restaurant.counter.factory.WaiterThreadFactory;
import org.restaurant.model.Food;
import org.restaurant.model.Order;
import org.restaurant.pass.KitchenPass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class Counter {
    private static final Logger Log = LoggerFactory.getLogger(Counter.class);
    
    private final KitchenPass pass;
    
    private final ExecutorService waiters;
    
    private final ReentrantLock lock;
    
    private OrderDelegator orderDelegator;
    
    public Counter(KitchenPass pass){
        this.pass = pass;
        this.waiters = Executors.newFixedThreadPool(2, new WaiterThreadFactory());
        this.lock = new ReentrantLock();
    }
    
    public void takeOrder(Socket socket){
        waiters.submit(()-> {
            Log.info("{} has collected your order ", Thread.currentThread().getName());
            try{
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String food = in.readLine();
                Log.info("Food request {}", food);
                Order order = new Order(12L, food,485.2383f, out, in);
                lock.lock();
                pass.getOrders().add(order);
                orderDelegator.newOrder();
                lock.unlock();
            }catch (IOException e) {
                Log.error("IoException while receiving order {}",e.getMessage(), e);
            }
        });
    }
    
    public void deliverOrder() {
        waiters.submit(()->{
            try{
                Log.info("{} is delivering your order ", Thread.currentThread().getName());
                lock.lock();
                Food food = pass.getFoods().poll();
                if (food == null){
                    lock.unlock();
                    Log.info("Invalid food");
                    return;
                }
                Order order = food.getOrder();
                lock.unlock();
                String response = "Your food " + food.getCuisine() + " is prepared!!!. Enjoy your delicious food";
                PrintWriter out = order.getOut();
                Log.info("Serving food {} ", response);
                out.println(response);
                order.close();
            }catch (Exception e){
                Log.error("exception on serving food -> e {} ", Arrays.toString(e.getStackTrace()));
            }
            finally {
                lock.unlock();
            }
        });
    }
    
    public void setOrderDelegator(OrderDelegator orderDelegator) {
        this.orderDelegator = orderDelegator;
    }
}
