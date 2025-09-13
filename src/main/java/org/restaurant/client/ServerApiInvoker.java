package org.restaurant.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerApiInvoker {
    
    private static final Logger Log = LoggerFactory.getLogger(ServerApiInvoker.class);
    
    public static void main(String[] args) {
        try (ExecutorService executors = Executors.newFixedThreadPool(2)){
            for (int i = 0; i < 2; i++){
                executors.submit(()->{
                    try(ApiClient client = new ApiClient("127.0.0.1", 7645)) {
                        client.request("Food_" + Thread.currentThread().getName());
                    }catch (IOException e){
                        Log.error("Exception",e);
                    }
                });
            }
        }catch (Exception e){
            Log.error("Exception on pizza client {}",e.getMessage(), e);
        }
    }
}
