package com.chige.thread;

import java.util.concurrent.CountDownLatch;

/**
 * @Author wangyc
 * @Description 线程间通信
 * @Date 2025/5/6 20:15
 */
public class ThreadCommunicationTest {


    /**
     * countDownLatch实现按顺序打印 T1、T2、T3
     */
    public void countDownLatchTest() throws InterruptedException {
        CountDownLatch latch1 = new CountDownLatch(1);
        CountDownLatch latch2 = new CountDownLatch(1);
        CountDownLatch latch3 = new CountDownLatch(1);

        CountDownLatchThread thread1 = new CountDownLatchThread(latch1);
        CountDownLatchThread thread2 = new CountDownLatchThread(latch2);
        CountDownLatchThread thread3 = new CountDownLatchThread(latch3);

        new Thread(thread1).start();
        latch1.await();

        new Thread(thread2).start();
        latch2.await();

        new Thread(thread3).start();
        latch3.await();
    }

    /**
     * CyclicBarrier 实现按顺序打印 T1、T2、T3
     */
    public void cyclicBarrierTest() {

    }

    /**
     * Semaphore 实现按顺序打印 T1、T2、T3
     */
    public void semaphoreTest() {

    }


    public static void main(String[] args) throws InterruptedException {
        ThreadCommunicationTest test = new ThreadCommunicationTest();
        test.countDownLatchTest();
//        test.cyclicBarrierTest();
//        test.semaphoreTest();
    }

    static class CountDownLatchThread implements Runnable {

        private CountDownLatch countDownLatch;

        public CountDownLatchThread(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + ":" + "isRunning");
            }catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                //完成一个线程，计数减1
                countDownLatch.countDown();
            }
        }
    }

}
