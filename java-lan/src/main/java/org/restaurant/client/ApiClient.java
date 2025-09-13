package org.restaurant.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

@SuppressWarnings("unused")
public class ApiClient implements Closeable {
    
    private static final Logger Log = LoggerFactory.getLogger(ApiClient.class);
    
    private final Socket socket;
    
    private final PrintWriter out;
    
    private final BufferedReader in;
    
    private final String ipAddress;
    
    private final int port;
    
    public ApiClient(String ipAddress, int port) throws IOException {
        this.ipAddress = ipAddress;
        this.socket = new Socket();
        this.port = port;
        InetSocketAddress socketAddress = new InetSocketAddress(ipAddress, port);
        socket.connect(socketAddress, 6000);
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
    
    
    public void request(String food) {
        try{
            Log.info("Requesting {} from the restaurant {}:{}",food, ipAddress,port);
            out.println(food);
            out.flush();
            String message = in.readLine();
            Log.info("Message from the restaurant {}", message);
        }catch (IOException e){
            Log.error("Exception on requesting pizza to server {} ", ipAddress, e);
        }
    }
    
    public Socket getSocket() {
        return socket;
    }
    
    public PrintWriter getOut() {
        return out;
    }
    
    public BufferedReader getIn() {
        return in;
    }
    
    public String getIpAddress() {
        return ipAddress;
    }
    
    @Override
    public void close() throws IOException {
        this.out.close();
        this.in.close();
        this.socket.close();
    }
}
