package com.chige.io.demo;

import com.chige.io.IOConstant;
import org.eclipse.jetty.util.IO;
import sun.nio.ch.FileChannelImpl;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.OpenOption;

/** 案例一：文件复制
 * @author wangyc
 * @date 2022/4/14
 */
public class FileNIOCopyDemo {

    /**
     * 方式一：利用文件io流 + buffer + channel实现文件复制
     * 效率：不是很高
     */
    public static void copyOne() throws IOException {
        Long start = System.currentTimeMillis();
        String sourceFilePath = IOConstant.SOURCE_FILE_PATH;
        String targetFilePath = IOConstant.TARGET_FILE_PATH;
        // 1、创建文件输出、输入流
        FileInputStream fis = new FileInputStream(sourceFilePath);
        FileOutputStream fos = new FileOutputStream(targetFilePath);
        // 2、获取通道对象
        FileChannel fisChannel = fis.getChannel();
        FileChannel fosChannel = fos.getChannel();
        // 3、新建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(2048);
        int fileLength = -1;
        // 从通道channel中读取，然后写入buffer中
        while ((fileLength = fisChannel.read(byteBuffer)) != -1) {
            System.out.println("读取通道的字节数为：" + fileLength);
            byteBuffer.flip();
            int writeLength = 0;
            // 从buffer中读取，然后write到channel中
            while((writeLength = fosChannel.write(byteBuffer)) != 0) {
                System.out.println("写入通道的字节数为：" + writeLength);
            }
            byteBuffer.clear();
        }
        fosChannel.force(true);
        fis.close();
        fos.close();
        fisChannel.close();
        fosChannel.close();
        System.out.println("copyOne耗时: " + (System.currentTimeMillis() - start) + "ms");

    }

    /**
     * 方式二：利用文件通道的transferFrom方法，提高文件复制的效率
     */
    public static void copyTwo() throws IOException {
        Long start1 = System.currentTimeMillis();
        String sourceFilePath = IOConstant.SOURCE_FILE_PATH;
        String targetFilePath = IOConstant.TARGET_FILE_PATH_2;
        FileReader fileReader = new FileReader(sourceFilePath);
        FileWriter fileWriter = new FileWriter(targetFilePath);
        CharBuffer charBuffer = CharBuffer.allocate(2048);
        int readLength = -1;
        while ((readLength = fileReader.read(charBuffer)) != -1) {
            System.out.println("写入字符缓冲区的字符数为：" + readLength);
            charBuffer.flip();
            int i = 0;
            while (charBuffer.hasRemaining()) {
                char c = charBuffer.get();
                fileWriter.write(c);
                i++;
            }
            System.out.println("从缓冲区中读取的字符数为：" + i);
            charBuffer.clear();
        }
        fileWriter.flush();
        fileReader.close();
        fileWriter.close();
        System.out.println("copyTwo耗时: " + (System.currentTimeMillis() - start1) + "ms");
    }

    /**
     * 方式三：利用FileChannel通道对象的transferFrom方法实现高效复制文件
     */
    public static void copyThree() throws IOException {
        RandomAccessFile readFile = new RandomAccessFile(IOConstant.SOURCE_FILE_PATH, "r");
        RandomAccessFile writeFile = new RandomAccessFile(IOConstant.TARGET_FILE_PATH_2, "rw");
        FileChannel fisChannel = readFile.getChannel();
        FileChannel fosChannel = writeFile.getChannel();
        // 实现方式一：从输入通道中读取数据，然后通向输出通道fosChannel
        fosChannel.transferFrom(fisChannel, 0L, fisChannel.size());
        // 或者
//        fisChannel.transferTo(0L, fisChannel.size(), fosChannel);
        fosChannel.force(true);
        fisChannel.close();
        fosChannel.close();
        readFile.close();
        writeFile.close();
    }


    public static void main(String[] args) throws IOException {
        copyThree();
    }
}
