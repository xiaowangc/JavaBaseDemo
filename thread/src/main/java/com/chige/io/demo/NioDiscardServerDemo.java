package com.chige.io.demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * 使用NIO实现Discard服务器的实践案例
 * Discard服务器的功能很简单：仅仅读取客户端通道的输入数据，读取完成后直接关闭客户端通道；
 * 并且读取到的数据直接抛弃掉（Discard）
 *
 * @author wangyc
 * @date 2022/4/17
 */
public class NioDiscardServerDemo {

    /**
     * 启动服务端
     * 问题：下面的写法可能存在粘包，拆包的隐藏现象
     */
    public static void startServer() throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        // 绑定服务端地址
        serverSocketChannel.bind(new InetSocketAddress("localhost", 8008));
        // 注册serverSocketChannel的IO事件到选择器上
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        // 注意：当选择器中查询不到事件时，会一直阻塞着
        // select()方法是阻塞调用； select(int timeout);是阻塞指定的时间后返回； selectNow()方法是异步调用，不会造成程序的阻塞
        while (selector.select() > 0) {
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isAcceptable()) { // 可接受IO事件
                    // 监听到一个新通道
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    // 将新通道设置为非阻塞模式
                    socketChannel.configureBlocking(false);
                    // 将新通道的可读IO事件注册到同一个选择器上
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else if (selectionKey.isReadable()) { //服务端获取到的通道具有可读IO事件
                    // 通过选择键集合可获取到当前IO事件的通道类型
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    // 读取数据
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    int readLength;
                    while ((readLength = socketChannel.read(byteBuffer)) != -1) {
                        byteBuffer.flip();
                        System.out.println("从通道中读取到的数据为：" + new String(byteBuffer.array(), 0, byteBuffer.limit()));
                        byteBuffer.clear();
                    }
                    socketChannel.close();
                }
                // 移除选择键集合
                iterator.remove();
            }
        }
        System.out.println("关闭通道");
        serverSocketChannel.close();

    }

    public static void main(String[] args) throws IOException {
        startServer();
    }
}
