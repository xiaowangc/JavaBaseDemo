package com.chige.lock.AQS.lockSupport;

import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author wangyc
 * @Description 线程阻塞唤醒类：LockSupport 用来阻塞和唤醒线程，底层实现依赖于 Unsafe 类
 * @Date 2025/1/12 12:24
 */
public class LockSupportDemo {


    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock();
        boolean b = reentrantLock.tryLock();

        Thread mainThread = Thread.currentThread();
        new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                System.out.println(i);
                if (i == 500) {
                    //释放主线程
                    LockSupport.unpark(mainThread);
                    System.out.println(i);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        //主线程阻塞挂起 - 直到释放才执行后续的代码/指令
        LockSupport.park();
        System.out.println("Main thread was unparked.");
    }

}
