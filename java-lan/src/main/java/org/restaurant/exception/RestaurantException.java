package org.restaurant.exception;

public class RestaurantException extends RuntimeException{
    
    public RestaurantException(String message, Throwable throwable){
        super(message, throwable);
    }
}
