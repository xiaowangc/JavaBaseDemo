package com.chige.streamFunction_Lambda;

import java.io.IOException;
import java.util.function.Function;

/**
 * @author wangyc
 * @date 2021/6/5 11:15
 */
public class FileReaderMain {

    public static void main(String[] args) throws IOException {
        // Lambda表达式1：读取一行数据 等号左边的表达式相当于【动态地】实现了函数式接口的抽象方法
        FileReadInterface fileReadInterface1 = reader -> reader.readLine();
        // Lambda表达式2：读取两行数据
        FileReadInterface fileReadInterface2 = reader -> reader.readLine() + reader.readLine();
        FileReadImpl fileRead = new FileReadImpl();
        fileRead.processFile(fileReadInterface1);
        fileRead.processFile(fileReadInterface2);
        Function<String,Integer> function;
    }
}
