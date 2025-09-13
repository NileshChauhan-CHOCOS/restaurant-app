package org.restaurant.kitchen.factory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("unused")
public class ChefThreadFactory implements ThreadFactory {
    
    private final AtomicInteger threadNumber= new AtomicInteger(1);
    
    private static final String NAME_PREFIX = "Chefs-";
    
    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, NAME_PREFIX + threadNumber.getAndIncrement());
    }
    
    public String getNamePrefix() {
        return NAME_PREFIX;
    }
}
