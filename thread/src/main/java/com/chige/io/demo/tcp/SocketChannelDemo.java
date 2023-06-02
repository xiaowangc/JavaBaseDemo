package com.chige.io.demo.tcp;

import com.chige.io.IOConstant;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/** 使用SocketChannel模拟文件从客户端向服务端发送
 * 具体步骤：（1）使用FileChannel文件通道读取本地文件内容；
 * （2）然后在客户端使用SocketChannel套接字通道，把文件信息和文件内容发送到服务器。
 * @author wangyc
 * @date 2022/4/16
 */
public class SocketChannelDemo {
    /**
     * 模拟从客户端向服务端发送文件
     */
    public static void sendFileToServer() throws IOException {
        FileInputStream fis = new FileInputStream(IOConstant.SOURCE_FILE_PATH);
        FileChannel fileChannel = fis.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int readLength = -1;
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 8008));
        socketChannel.configureBlocking(false);

        while (!socketChannel.finishConnect()) {
            doOther();
        }
        System.out.println("与服务端建立了连接，开始进行文件传输...");

        while ((readLength = fileChannel.read(byteBuffer)) != -1) {
            System.out.println("读取到的字节数为：" + readLength);
            byteBuffer.flip();
            int writeLength = 0;
            while ((writeLength = socketChannel.write(byteBuffer)) != 0) {
                System.out.println("写入通道的字节数为：" + writeLength);
            }
            byteBuffer.clear();
        }

        System.out.println("从本地文件读取完数据并写入传输通道");
        socketChannel.shutdownOutput();
        socketChannel.close();
        fis.close();
        fileChannel.close();
    }

    private static void doOther() {
        System.out.println("未与服务端建立完连接，我在做其他事情...");
    }



    public static void main(String[] args) throws IOException {
        sendFileToServer();
//        int value = SelectionKey.OP_READ | SelectionKey.OP_WRITE | SelectionKey.OP_CONNECT;
//        System.out.println("value =" +value);
//        System.out.println("判断一个值是否在value中：" + ((value & SelectionKey.OP_READ) == SelectionKey.OP_READ));
//        System.out.println("判断一个值是否在value中：" + ((value & SelectionKey.OP_WRITE) == SelectionKey.OP_WRITE));
//        System.out.println("判断一个值是否在value中：" + ((value & SelectionKey.OP_CONNECT) == SelectionKey.OP_CONNECT));
//        System.out.println("判断一个值是否在value中：" + ((value & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT));
//        System.out.println("判断一个值是否在value中：" + (value == SelectionKey.OP_READ));
    }
}
