package com.chige.thread.interrupt;

import org.apache.poi.ss.formula.functions.T;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/** 线程中断理解
 * @author wangyc
 * @date 2022/7/14
 */
public class ThreadInterrupt {

    public static void main(String[] args) throws InterruptedException {
        ThreadInterrupt test = new ThreadInterrupt();
        test.testInterrupt_join();

    }


    private void testInterrupt() throws InterruptedException {
        Thread thread = new Thread(new Work());
        thread.setName("A线程");
        thread.setDaemon(true);
        thread.start();
        TimeUnit.MILLISECONDS.sleep(10);
        thread.interrupt(); // 用来设置中断状态=true
        System.out.println("Main thread 停止了");
    }

    /**
     *
     * @throws InterruptedException
     */
    public void testInterrupt_join() throws InterruptedException {
        Thread t = new MyThread();
        t.setName("[线程A]");
        t.start();
        Thread.sleep(1000);
//        t.interrupt(); // 中断t线程
        t.join(1000); // 阻塞等待t线程结束，超过1000毫秒后停止等待，但t线程还会继续执行不被中断
        System.out.println("end");
    }

    class MyThread extends Thread {
        public void run() {
            Thread hello = new HelloThread();
            hello.setName("hello线程");
            hello.start(); // 启动hello线程
            try {
                hello.join(); // 等待hello线程结束，如果MyThread线程中断则抛出异常
            } catch (InterruptedException e) {
                System.out.println("由于当前线程:" + Thread.currentThread().getName() + "被中断所以抛出InterruptedException!");
            }
            System.out.println("hello线程是否中断:" + hello.isInterrupted());
            hello.interrupt();
            System.out.println("执行中断方法，hello线程是否中断:" + hello.isInterrupted() + "当前线程结束！" + Thread.currentThread().getName());

        }

    }

    class HelloThread extends Thread {
        public void run() {
            int n = 0;
            while (!isInterrupted() && n < 100) {
                n++;
                System.out.println(n + " hello!");
                try {
                    Thread.sleep(100); //sleep中如果HelloThread线程执行中断方法则抛出中断异常
                } catch (InterruptedException e) {
                    System.out.println("当前线程" + Thread.currentThread().getName() + "被中断所以抛出InterruptedException!");
                    break;
                }
            }
            if (n >= 100) {
                System.out.println("当前线程执行结束:" + Thread.currentThread().getName());
            }
        }
    }

    static class Work implements Runnable {

        @Override
        public void run() {

            try {
                System.out.println("线程名称：" + Thread.currentThread().getName() + ",当前线程的中断状态:" + Thread.currentThread().isInterrupted());
                TimeUnit.SECONDS.sleep(1);
                // 阻塞中的线程捕捉到中断信号，抛出异常，jvm会清除调用interrupt方法的线程对象的中断状态，中断状态为false

            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("work线程 - 捕捉到中断异常：当前线程名称:"+ Thread.currentThread().getName() + ",是否中断: " + Thread.currentThread().isInterrupted());
            }
            System.out.println("work 线程停止了,是否中断：" + Thread.currentThread().isInterrupted());
        }
    }

    public static void testSleep() {
        Thread sleepThread = new Thread(new Sleep());
        sleepThread.setDaemon(true);
        sleepThread.start();
    }

    static class Sleep implements Runnable {

        @Override
        public void run() {
            while (true) {
                System.out.println(Thread.interrupted());
            }
        }
    }

    /**
     * 测试一个线程在没有执行可中断方法之前就被中断了，那么接下来执行可中断方法（比如执行sleep）时，会发生什么
     * 当 InterruptedException 被抛出时，线程的中断标识会被清除（即中断标识会被重置为 false）
     */
    public static void testInterruptBeforeSleep() {
        // 判断当前线程是否被中断
        System.out.println("Main Thread is interrupted? " + Thread.interrupted());

        //2、中断当前线程
        Thread.currentThread().interrupt();

        //3、判断当前线程是否已经被中断
        System.out.println("当前线程名: " + Thread.currentThread().getName() + "是否已经被中断:" + Thread.currentThread().isInterrupted());

        //4、执行可中断方法
        try {
            TimeUnit.SECONDS.sleep(1); //main线程已经被中断，再sleep时会抛出InterruptedException
            System.out.println("完成正常的睡眠");
        } catch (InterruptedException e) {
            System.out.println("当前线程名称:" + Thread.currentThread().getName() + "是否已经被中断：" + Thread.currentThread().isInterrupted());
            Thread.currentThread().interrupt(); //重置中断标识
        }

    }

}



