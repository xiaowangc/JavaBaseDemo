package com.chige.thread.state;

import java.util.ArrayList;
import java.util.List;

/**
 * 线程状态简单演示
 *
 * @author wangyc
 * @date 2022/5/3
 */
public class ThreadStateDemo {

    //每个线程执行的轮次
    public static final long MAX_TURN = 5;
    //线程编号
    static int threadSeqNumber = 0;
    //全局的静态线程列表
    static List<Thread> threadList = new ArrayList<>();

    private static void printThreadStatus() {
        for (Thread thread : threadList) {
            System.out.println("thread name is " + thread.getName() + ",the state is " + thread.getState());
        }
    }

    //向全局的静态线程列表加入线程
    private static void addStatusThread(Thread thread) {
        threadList.add(thread);
    }

    static class StatusDemoThread extends Thread {
        public StatusDemoThread() {
            super("statusPrintThread " + (++threadSeqNumber));
            //将自己加入到全局的静态线程列表
            addStatusThread(this);
        }

        public void run() {
            System.out.println(getName() + ", 状态为：" + this.getState());
            for (long i = 0; i < MAX_TURN; i++) {
                //线程睡眠
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //输出所有线程的状态
                printThreadStatus();
            }
            System.out.println(getName() + " 执行结束。");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        //将 main 线程加入全局列表
        addStatusThread(Thread.currentThread());
        Thread t1 = new StatusDemoThread();
        System.out.println(t1.getName() + ",状态为：" + t1.getState());
        Thread t2 = new StatusDemoThread();
        System.out.println(t2.getName() + ",状态为：" + t2.getState());
        Thread t3 = new StatusDemoThread();
        System.out.println(t3.getName() + ",状态为：" + t3.getState());

        t1.start();
        Thread.sleep(500);
        t2.start();
    }

}
