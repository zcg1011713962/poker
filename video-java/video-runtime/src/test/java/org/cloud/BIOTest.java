package org.cloud;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class BIOTest {
    private final static int PORT = 8080;
    public static void main(String[] args) {
        // 使用ServerSocket类创建了一个服务器端的Socket对象
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started...");
            while (true) {
                // 通过accept()方法来等待客户端的连接请求
                Socket socket = serverSocket.accept();
                System.out.println("Client connected: " + socket.getInetAddress().getHostAddress());
                handleConnection(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleConnection(Socket socket) {
        try (InputStream inputStream = socket.getInputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            // 此处使用的是阻塞I/O方式实现（BIO），即在接受客户端连接和处理数据时均是阻塞的，如果客户端长时间没有反应，则服务端将被阻塞，并影响其他客户端连接
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                String message = new String(buffer, 0, bytesRead);
                System.out.println("Received message: " + message.trim());
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
