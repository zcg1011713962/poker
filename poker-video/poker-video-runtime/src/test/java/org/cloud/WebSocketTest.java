package org.cloud;


import org.cloud.protobuf.ProtobufServer;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

public class WebSocketTest extends BaseTest{
    @Test
    public void test() throws ExecutionException, InterruptedException {
        new ProtobufServer.Builder().setPort(1000).build().get();
    }

}
