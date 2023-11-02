package org.cloud;


import org.cloud.rtp.RtpServer;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

public class UdpServerTest extends BaseTest {

    @Test
    public void udpServerTest() throws ExecutionException, InterruptedException {
        new RtpServer.Builder().setPort(10999).build().get();
    }

}
