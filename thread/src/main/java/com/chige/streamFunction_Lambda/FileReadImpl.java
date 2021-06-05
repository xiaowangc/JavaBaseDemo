package com.chige.streamFunction_Lambda;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author wangyc
 * @date 2021/6/5 11:12
 */
public class FileReadImpl {

    public FileReadImpl() {}


    /**
     *  该方法用来读取一行数据，并不能读取多行数据，可以进行改造
     */
    public static String processFile() throws IOException {
        // Java7新增的语法，try(){}，可自动关闭资源，减少了代码的臃肿
        try( BufferedReader bufferedReader =
                     new BufferedReader(new  FileReader("./test.txt"))){
            return bufferedReader.readLine();
        }
    }

    /**
     *  此方法可满足读取不同“行为”的数据，如行为1：读取一行数据 ； 行为2：读取两行数据
     */
    public String processFile(FileReadInterface fileReadInterface) throws IOException {
        // Java7新增的语法，try(){}，可自动关闭资源，减少了代码的臃肿
        try( BufferedReader bufferedReader =
                     new BufferedReader(new  FileReader("./test.txt"))){
            return fileReadInterface.process(bufferedReader);
        }
    }
}
