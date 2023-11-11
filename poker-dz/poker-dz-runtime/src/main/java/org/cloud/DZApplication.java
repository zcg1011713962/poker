package org.cloud;


import lombok.extern.slf4j.Slf4j;
import org.cloud.enums.SocketPort;
import org.cloud.protobuf.ProtobufServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@SpringBootApplication(scanBasePackages = {"org.cloud.protobuf.config","org.cloud.protobuf.handler","org.cloud.poker.game.handler"})
public class DZApplication {
    public static void main(String[] args) {
        SpringApplication.run(DZApplication.class, args);
    }

    @Component
    @Slf4j
    private static class ProtobufListenerPort implements CommandLineRunner {
        @Override
        public void run(String... args) throws Exception {
            new ProtobufServer.Builder().setPort(SocketPort.WEBSOCKET_PROTOBUF_DZ.getPort()).build().exceptionally(e ->{
                log.error("{}", e);
                return null;
            }).get();
        }
    }

}
