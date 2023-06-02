package com.chige.thread.synchronizeds;


import java.util.LinkedList;

/** 多线程通信
 * 多线程间通信需要用到Object的notifyAll方法，可以唤醒所有执行wait方法后阻塞的线程，被唤醒后的线程需要争抢monitor的控制权
 * 而notify只能唤醒单个线程
 * @author wangyc
 * @date 2022/7/16
 */
public class MultiThreadCommunication {

    /** 当前队列长度 */
    private int num;
    /** 队列最大长度*/
    private static final int MAX_NUM = 10;

    /**
     * 存放event事件的队列
     */
    private LinkedList<Event> eventQueue = new LinkedList<>();

    static class Event {

    }

    public void offer(Event event) {
        synchronized (eventQueue) { //尝试获取eventQueue对象的monitor控制权
            while (eventQueue.size() >= MAX_NUM) { // 队列满了
                try {
                    System.out.println("queue is full");
                    eventQueue.wait(); //调用wait方法时会阻塞，并释放monitor控制权
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("producer offer event: " + event);
            eventQueue.addLast(event);
            eventQueue.notifyAll(); //唤醒一个阻塞等待的线程
        }
    }

    public Event take() {
        synchronized (eventQueue) {
            while (eventQueue.isEmpty()) {
                try {
                    System.out.println("consumer stop, cause the queue is empty.");
                    eventQueue.wait();
                } catch (InterruptedException e) {
                    System.out.println("可中断wait方法捕捉到中断信号，当前线程的状态:" + Thread.currentThread().getState());
                }
            }
            System.out.println("判断当前队列长度:" + eventQueue.size());
            Event event = eventQueue.removeFirst(); //抛出的异常如果没有捕捉的话，会导致当前线程退出

            System.out.println("consumer take event: " + event);
            eventQueue.notifyAll();
            return event;
        }
    }

    //SingleThreadCommunication 类中的案例当存在多个线程同时执行offer或者take方法时，可能会出现两种情况：
    //1、LinkedList为空时执行removeFirst方法，导致出现NoSuchElementException异常
    //2、LinkedList满时继续addLast方法，导致出现
    public static void testMultiThread() {
        MultiThreadCommunication dome = new MultiThreadCommunication();
        new Thread(()->{
            for (; ;) {
                dome.offer(new Event());
            }
        },"producer1").start();
        new Thread(()->{
            for (; ;) {
                dome.offer(new Event());
            }
        },"producer2").start();

        new Thread(()->{
            for (; ;) {
                dome.take();
            }
        },"consumer1").start();

        new Thread(()->{
            for (; ;) {
                dome.take();
            }
        },"consumer2").start();

    }


    public static void main(String[] args) {
        testMultiThread();
    }
}
