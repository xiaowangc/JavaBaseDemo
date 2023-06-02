package com.chige.thread.myThread;

/** 自定义线程，继承Thread类
 * @author wangyc
 * @date 2022/4/11
 */
public class MyThread extends Thread {

    @Override
    public void run() {
        System.out.println(">>> 自定义线程");
        this.setName("自定义线程-");
    }

    public static void main(String[] args) {
//        MyThread myThread = new MyThread();
//        myThread.start();
//        System.out.println("线程组：" + myThread.getThreadGroup().getName() + "\n" + "当天线程" + Thread.currentThread());
//        System.out.println(myThread.getName() == Thread.currentThread().getName());

        System.out.println(Character.isDigit('1'));
        System.out.println(Character.isDigit('3'));
    }

}
