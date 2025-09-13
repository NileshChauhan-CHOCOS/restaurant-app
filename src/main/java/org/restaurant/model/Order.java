package org.restaurant.model;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;

public class Order implements Closeable {
    
    private Long orderId;
    
    private String item;
    
    private Float amount;
    
    private final PrintWriter out;
    
    private final BufferedReader in;
    
    public Order(Long orderId, String item, Float amount,
                 PrintWriter out, BufferedReader in){
        this.orderId = orderId;
        this.item = item;
        this.amount = amount;
        this.out = out;
        this.in = in;
    }
    
    public Long getOrderId() {
        return orderId;
    }
    
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    
    public String getItem() {
        return item;
    }
    
    public void setItem(String item) {
        this.item = item;
    }
    
    public Float getAmount() {
        return amount;
    }
    
    public void setAmount(Float amount) {
        this.amount = amount;
    }
    
    public PrintWriter getOut() {
        return out;
    }
    
    public BufferedReader getIn() {
        return in;
    }
    
    @Override
    public void close() throws IOException {
        out.close();
        in.close();
    }
}
