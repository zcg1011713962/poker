package org.cloud;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NIOSelectorTest {
    /**
     * 启动服务器
     */
    public void start() throws IOException {
        // 创建一个selector
        Selector selector = Selector.open();

        // 创建一个ServerSocketChannel，并配置为非阻塞模式
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);

        // 将ServerSocketChannel注册到selector上，并设置需要监听的事件为OP_ACCEPT
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(8080));
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        // 循环处理事件
        while (true) {
            // 阻塞等待事件发生
            selector.select();

            // 获取selector上的所有事件
            Iterator ite = selector.selectedKeys().iterator();
            while (ite.hasNext()) {
                SelectionKey key = (SelectionKey) ite.next();

                // 如果是连接事件
                if (key.isAcceptable()) {
                    // 接受连接请求并创建SocketChannel
                    ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);

                    // 注册SocketChannel到selector，并设置需要监听的事件为OP_READ
                    sc.register(selector, SelectionKey.OP_READ);

                    System.out.println("接收了一个新连接：" + sc.getRemoteAddress());
                }

                // 如果是读事件
                if (key.isReadable()) {
                    SocketChannel sc = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int len = sc.read(buffer);
                    if (len == -1) {
                        // 客户端关闭连接
                        System.out.println("客户端关闭连接：" + sc.getRemoteAddress());
                        sc.close();
                        key.cancel();
                        continue;
                    }

                    // 将读取的数据转换为字符串并输出
                    buffer.flip();
                    byte[] bytes = new byte[buffer.remaining()];
                    buffer.get(bytes);
                    String content = new String(bytes);
                    System.out.println("接收到客户端[" + sc.getRemoteAddress() + "]发来的消息：" + content);
                }

                // 从事件集合中删除已处理的事件
                ite.remove();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new NIOSelectorTest().start();
    }
}
