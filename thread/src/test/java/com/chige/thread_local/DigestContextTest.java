package com.chige.thread_local;

/**
 * @author wangyc
 * @date 2023/6/6
 */
public class DigestContextTest {

    public static void main(String[] args) {
        DigestContextTest digestContextTest = new DigestContextTest();
        new Thread(() -> {
            String name = Thread.currentThread().getName();
            DigestContext ctx = DigestContextHolder.init();
            System.out.println(name + ":" + ctx.getRequestId());
            System.out.println(name + ":" + ctx.getStartTime());
            DigestContextHolder.clear();
        }, "线程1").start();

        new Thread(() -> {
            String name = Thread.currentThread().getName();
            DigestContext ctx = DigestContextHolder.init();
            System.out.println(name + ":" + ctx.getRequestId());
            System.out.println(name + ":" + ctx.getStartTime());
            DigestContextHolder.clear();
        }, "线程2").start();
    }

    private void doSomething() {
        DigestContext digestContext = DigestContextHolder.getDigestContext();
        new Thread(() -> {
            System.out.println(digestContext.toString());
            DigestContextHolder.clear();
        }).start();


    }



}
