package com.chige.io.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;

/** 数据报通道，用于UDP协议的数据读写
 * @author wangyc
 * @date 2022/4/12
 */
public class DatagramChannelDemo {

    /**
     * UDP协议连接客户端
     */
    public static void client() throws IOException {
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false);
        Scanner scanner = new Scanner(System.in);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        System.out.println("UPD客户端启动成功");
        System.out.println("请输入发送内容：");
        while (scanner.hasNext()) {
            String next = scanner.next();
            // 将输入的内容写入缓冲区中
            byteBuffer.put(next.getBytes(StandardCharsets.UTF_8));
            byteBuffer.flip(); // 写模式 -> 读模式
            // 从缓冲区中读取数据，写入通道中
            datagramChannel.send(byteBuffer, new InetSocketAddress("localhost",8008));
            byteBuffer.clear();
        }
        System.out.println("关闭客户端连接");
        datagramChannel.close();
    }

    /**
     * UDP协议连接服务端
     */
    public static void server() throws IOException {
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false);
        datagramChannel.bind(new InetSocketAddress("localhost", 8008));
        System.out.println("UDP服务端启动成功");
        // 开启一个选择器
        Selector selector = Selector.open();
        // 将通道注册到选择器中
        datagramChannel.register(selector, SelectionKey.OP_READ);
        while (selector.select() > 0) {
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                // 如果查询到"可读"事件
                if (selectionKey.isReadable()) {
                    //读取 DatagramChannel 数据报通道的数据
                    datagramChannel.receive(buffer);
                    buffer.flip();
                    System.out.println("读取到的数据为：" + new String(buffer.array(), 0, buffer.limit()));
                    buffer.clear();
                }
            }
            // 处理完，移除选择键，防止下次循环时重复处理
            iterator.remove();
        }
        System.out.println("关闭服务端连接");
        selector.close();
        datagramChannel.close();
    }

    public static void main(String[] args) throws IOException {
        client();
        server();
    }
}
