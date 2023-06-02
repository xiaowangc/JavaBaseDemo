package com.chige.io.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/** 服务器套接字通道（或服务器监听通道），允许我们监听TCP连接请求，
 * 为每个监听到的请求，创建一个SocketChannel套接字通道；
 * 只能存在服务端
 * @author wangyc
 * @date 2022/4/12
 */
public class ServerSocketChannelDemo {

    /**
     * 获取服务端套接字连接ServerSocketChannel
     */
    public static void newServerSocketChannel() throws IOException {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress("localhost", 8008));
        // 获取新连接的套接字通道,未监听到连接之前，处于阻塞等待状态
        SocketChannel socketChannel = serverSocketChannel.accept();
        // 设置非阻塞模式
        socketChannel.configureBlocking(false);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int readLength = -1;
        // 从通道读取数据到buffer

        // 从buffer读取数据写入通道

        // 调用终止输出方法，向对方发送一个输出的结束标志
        socketChannel.shutdownOutput();
        socketChannel.close();
    }

    public static void main(String[] args) throws IOException {
        newServerSocketChannel();
    }
}
