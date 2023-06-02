package com.chige.io.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author wangyc
 * @date 2022/4/11
 */
public class NIODome {

    /**
     * Buffer与Channel结合使用
     * 从通道中读取数据写入缓冲区中
     */
    public static void testReadChannelToBuffer() throws FileNotFoundException {
        // 创建文件输入流
        String pathName = "/Users/yongchiwang/JavaDataBase/ideaproject/JavaBaseDemo/thread/src/main/java/com/chige/io/temp.txt";
        FileInputStream fis = new FileInputStream(pathName);
        // 从流对象中获取通道
        FileChannel fileChannel = fis.getChannel();
        // 创建缓冲区，并处于写模式
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        System.out.println("初始化: buffer=" + byteBuffer);
        // 从通道中读取数据写入
        try {
            fileChannel.read(byteBuffer);
            System.out.println(">>> 写入数据至缓冲区，buffer=" + byteBuffer);

            // 模式切换, 写模式 -> 读模式
            byteBuffer.flip();
            System.out.println("第一次调用file()，buffer =" + byteBuffer);

            while (byteBuffer.hasRemaining()) {
                byte b = byteBuffer.get();
                System.out.println("b=" + b);
            }
            System.out.println(">>> 从缓冲区读取完数据, buffer=" + byteBuffer);

            byteBuffer.flip();
            System.out.println(">>> 第二次切换模式，调用flip(), buffer=" + byteBuffer);
        } catch (IOException e) {
            System.out.println("写入数据至缓冲区时异常，原因: " + e);
            return;
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                System.out.println("文件流关闭失败,原因：" + e);
            }
        }

    }


    public static void main(String[] args) throws FileNotFoundException {
        testReadChannelToBuffer();
    }
}
