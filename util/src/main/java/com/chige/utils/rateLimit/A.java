package com.chige.utils.rateLimit;

/**
 * @Author wangyc
 * @Description TODO
 * @Date 2025/3/1 15:17
 */
public class A {


    public static void main(String[] args) {
        while (true) {
            System.out.println(1);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
