package com.chige.io.demo.tcp;

import com.chige.io.IOConstant;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/** 模拟服务端，从客户端接受文件数据并写入到本地文件
 * @author wangyc
 * @date 2022/4/16
 */
public class ServerSocketDemo {

    /**
     * 从客户端中接受数据并写入到本地
     */
    public static void acceptFileFromClient() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress("localhost",8008));
        // 监听端口并阻塞直至有新的客户端连接进来，才往下走，否则一直阻塞
        SocketChannel socketChannel = serverSocketChannel.accept();
        
        socketChannel.configureBlocking(false);
        FileOutputStream fos = new FileOutputStream(IOConstant.TARGET_FILE_PATH_2);
        FileChannel fosChannel = fos.getChannel();
        int readLength = 0;
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        while ((readLength = socketChannel.read(byteBuffer)) != -1) {
            System.out.println("从通道中读取到的字节数为：" + readLength);
            byteBuffer.flip();
            int writeLength = 0;
            while ((writeLength = fosChannel.write(byteBuffer)) != 0) {
                System.out.println("从缓冲区读取到的字节数为：" + writeLength);
            }
            byteBuffer.clear();
        }

        fosChannel.force(true);
        fos.close();
        fosChannel.close();
        socketChannel.shutdownInput();
        socketChannel.close();
    }

    public static void main(String[] args) throws IOException {
        acceptFileFromClient();
    }
}
