package com.chige.test;

import java.io.IOException;
import java.util.Random;

/**
 * @author wangyc
 * @date 2023/6/9
 */
public class ExceptionTest {

    public static void main(String[] args) {
        ExceptionTest exceptionTest = new ExceptionTest();
        try {
            exceptionTest.noException();
        } catch (IOException e) {
            System.out.println("主方法捕捉1" + e);
        }
        try {
            exceptionTest.catchException();
        } catch (IOException e) {
            System.out.println("主方法捕捉2" + e);
        }
    }

    private void noException() throws IOException {
        boolean a = true;
        try {
            if (a) {
                throw new IOException("文件io异常");
            }
        } finally {
            System.out.println("关闭文件流");
        }
    }

    private void catchException() throws IOException {
        boolean a = true;
        try {
            if (a) {
                throw new IOException("文件io异常");
            }
        } catch (IOException e) {
            System.out.println("捕捉文件异常" + e);
            throw e;
        } finally {
            System.out.println("关闭文件流");
        }
    }
}
