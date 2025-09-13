package org.restaurant.counter.factory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("unused")
public class WaiterThreadFactory implements ThreadFactory {
    
    private final AtomicInteger integer = new AtomicInteger(1);
    
    private final String prefixName = "Waiter-";
    
    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, prefixName + integer.getAndIncrement());
    }
    
    public String getPrefixName() {
        return prefixName;
    }
}
