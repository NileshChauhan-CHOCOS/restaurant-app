package org.restaurant.model;

public class Food {
    
    private Order order;
    
    private String cuisine;
    
    public Food(Order order, String cuisine){
        this.order = order;
        this.cuisine = cuisine;
    }
    
    public Order getOrder() {
        return order;
    }
    
    public void setOrder(Order order) {
        this.order = order;
    }
    
    public String getCuisine() {
        return cuisine;
    }
    
    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }
}
