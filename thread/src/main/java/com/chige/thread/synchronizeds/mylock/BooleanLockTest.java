package com.chige.thread.synchronizeds.mylock;

import org.apache.commons.lang3.RandomUtils;
import org.apache.poi.ss.formula.functions.T;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author wangyc
 * @date 2022/7/16
 */
public class BooleanLockTest {

    private final Lock lock = new BooleanLock();

    //1、多个线程通过lock方法争抢锁
    public void syncMethod() {
        try {
            lock.lock();
            int randomInt = RandomUtils.nextInt(0,5);
            System.out.println("线程" + Thread.currentThread().getName() + " get the lock.");
            TimeUnit.SECONDS.sleep(randomInt);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }


    //2、可中断被阻塞的线程
    public static void interruptMultiThread() throws InterruptedException {
        BooleanLockTest booleanLockTest = new BooleanLockTest();
        new Thread(booleanLockTest::syncMethod,"T1").start();
        TimeUnit.MILLISECONDS.sleep(2);
        Thread t2 = new Thread(booleanLockTest::syncMethod, "T2");
        t2.start();
        TimeUnit.MILLISECONDS.sleep(10);
        t2.interrupt();
    }

    //3、阻塞的线程可超时

    public static void main(String[] args) throws InterruptedException {
//        BooleanLockTest booleanLockTest = new BooleanLockTest();
//        IntStream.range(0,10).mapToObj(i -> new Thread(booleanLockTest::syncMethod, "MyThread-" + i)).forEach(Thread::start);
        interruptMultiThread();
    }
}
