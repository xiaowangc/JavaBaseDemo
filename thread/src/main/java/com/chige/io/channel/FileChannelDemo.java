package com.chige.io.channel;

import com.chige.io.IOConstant;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/** 文件通道，用于文件数据的读取，为阻塞模式
 * @author wangyc
 * @date 2022/4/12
 */
public class FileChannelDemo {

    public void testFileChannel() throws IOException {
        /** 1、获取通道的三种方式 */
        // 方式一：从写入流中获取通道对象
        File file = new File(IOConstant.SOURCE_FILE_PATH);
        FileInputStream fis = new FileInputStream(file);
        FileChannel fisChannel = fis.getChannel();
        // 方式二：从读取流中获取通道对象
        FileOutputStream fos = new FileOutputStream(file);
        FileChannel fosChannel = fos.getChannel();

        // 方式三：从随机文件访问类中获取通道对象
        RandomAccessFile randomAccessFile = new RandomAccessFile(IOConstant.SOURCE_FILE_PATH, "rw");
        FileChannel accessFileChannel = randomAccessFile.getChannel();

        /** 2、【读取】从通道读取数据，使用通道的read(buffer)方法，会从通道读取数据并写入缓冲区*/
        ByteBuffer buffer = ByteBuffer.allocate(10); //创建缓冲区实例，写模式
        int length = -1;
        //调用通道的 read 方法，读取数据并买入字节类型的缓冲区
        while ((length = accessFileChannel.read(buffer)) != -1) {
            // buffer处理数据
            System.out.println("读取的字节数：" + length);
        }

        /** 3、【写入】写入数据进通道,使用通道的write(buffer)方法，会从缓冲区中读取数据，然后写入通道*/
        // 调用write(buffer)方法前，缓冲区需要处于读取模式
        buffer.flip();
        int writeLength = 0;
        while ((writeLength = accessFileChannel.write(buffer)) != 0) {
            System.out.println("写入的字节数:" + writeLength);
        }
        /** 4、【关闭】通道关闭*/
        fisChannel.close();
        fosChannel.close();
        /** （5）、强制刷新到磁盘：从缓冲区写入数据到通道时，如果需要保证数据能落地写入磁盘，可以在写入后调用通道的force()方法*/
        accessFileChannel.force(true);
        accessFileChannel.close();
    }


    public static void main(String[] args) {

    }

}
