package com.chige.juc;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Exchanger;

/**
 * @Author wangyc
 * @Description TODO
 * @Date 2025/1/12 15:47
 */
public class ExchangerDemo {

    CompletableFuture future = new CompletableFuture();

    void test() {
        Exchanger exchanger = new Exchanger();
    }

}
