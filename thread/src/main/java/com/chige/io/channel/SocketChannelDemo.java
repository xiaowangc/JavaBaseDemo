package com.chige.io.channel;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/** 套接字通道，用于Socket套接字TCP连接的数据读写
 * 可存在服务端和客户端
 * @author wangyc
 * @date 2022/4/12
 */
public class SocketChannelDemo {

    private SocketChannel socketChannel;
    private ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

    /**
     * 获取socketChannel实例
     */
    public void newSocketChannel() throws IOException {
        // 模拟客户端的socketChannel
        socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false); //设置为非阻塞模式
        // 向服务端的IP和端口发起连接
        socketChannel.connect(new InetSocketAddress("localhost", 8008));
        int i = 0;
        while (!socketChannel.finishConnect()) {
            // 当未与服务端建立连接时，处理其他事情
            System.out.println("未与服务端建立连接，我在做其他事情,次数：" + ++i);
        }
        //开始与服务端建立连接
        System.out.println("与服务端建立了连接，处理其他事情...");
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.putChar('a');
        buffer.putChar('b');
        buffer.putChar('c');
        socketChannel.write(buffer);
        socketChannel.shutdownInput();
        this.close();
    }

    /**
     * 读取传输通道的数据
     */
    public void readFromChannel() throws IOException {
        socketChannel.read(byteBuffer);
    }

    /**
     * 向传输通道写入数据
     */
    public void writeToChannel() throws IOException {
        socketChannel.write(byteBuffer);
    }

    /**
     * 关闭通道
     */
    public void close() throws IOException {
        if (socketChannel != null) {
            socketChannel.close();
        }
    }

    public static void main(String[] args) throws IOException {
        SocketChannelDemo demo = new SocketChannelDemo();
        demo.newSocketChannel();
    }
}
