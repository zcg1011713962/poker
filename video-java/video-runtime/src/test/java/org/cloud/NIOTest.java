package org.cloud;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NIOTest {
    private static final int BUFFER_SIZE = 1024;

    private ServerSocketChannel serverSocketChannel;
    private ExecutorService threadPool;

    public NIOTest(int port, int maxThreads) throws IOException {
        // 打开ServerSocketChannel
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);
        threadPool = Executors.newFixedThreadPool(maxThreads);
    }

    public void start() throws IOException {
        while (true) {
            // 监听客户端连接
            SocketChannel socketChannel = serverSocketChannel.accept();
            if (socketChannel != null) {
                threadPool.submit(new ClientHandler(socketChannel));
            }
        }
    }

    private static class ClientHandler implements Runnable {

        private final SocketChannel socketChannel;

        public ClientHandler(SocketChannel socketChannel) {
            this.socketChannel = socketChannel;
        }

        @Override
        public void run() {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            try {
                // 读取数据
                while (socketChannel.read(buffer) > 0) {
                    /**
                     *  flip 切换读写模式
                     * 当 ByteBuffer 被创建时，它的读写模式都是写模式。
                     * 在写模式下，可以通过 put 方法向缓冲区中写入数据。当
                     * 写入了部分数据后，可以通过调用 flip 方法切换到读模式，以便从缓冲区中读取数据。
                     * 在读模式下，可以通过 get 方法从缓冲区中读取数据。
                     * 一旦读取完所有数据，可以通过调用 clear 或 compact 方法清空缓冲区，然后重新切换回写模式。
                     */
                    buffer.flip();
                    System.out.print(new String(buffer.array()).trim());
                    buffer.clear();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        NIOTest server = new NIOTest(8888, 10);
        server.start();
    }
}
