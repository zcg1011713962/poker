package org.cloud;


import org.cloud.websocket.WebSocket;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

public class WebSocketTest extends BaseTest{
    @Test
    public void test() throws ExecutionException, InterruptedException {
        new WebSocket.Builder().setPort(1000).build().get();
    }

}
