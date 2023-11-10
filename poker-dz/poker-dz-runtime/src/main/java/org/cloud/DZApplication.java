package org.cloud;


import org.cloud.websocket.WebSocket;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class DZApplication {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        initSocket();
        SpringApplication.run(DZApplication.class, args);
    }

    private static void initSocket() throws ExecutionException, InterruptedException {
        new WebSocket.Builder().setPort(9999).build().get();
    }

}
