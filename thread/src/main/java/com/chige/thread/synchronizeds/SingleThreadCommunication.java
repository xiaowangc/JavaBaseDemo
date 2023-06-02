package com.chige.thread.synchronizeds;


import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

/** 单线程通信: 生产者、消费者的应用
 * wait、notify方法的实践应用
 * @author wangyc
 * @date 2022/7/16
 */
public class SingleThreadCommunication {
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

    /**
     * 生产事件对象
     * 说明：wait方法是需要配合同步方法一起使用的，即可与synchronized结合使用，
     * 因为在执行对象的wait方法之前，需要获取对象的monitor控制权;
     * 调用完wait方法后，会释放对monitor的控制权，然后当前线程会阻塞，直达被别的线程唤醒
     */
    public void offer(Event event) {
        synchronized (eventQueue) { //尝试获取eventQueue对象的monitor控制权
            if (eventQueue.size() >= MAX_NUM) { // 队列满了
                try {
                    System.out.println("queue is full");
                    eventQueue.wait(); //调用wait方法时会阻塞，并释放monitor控制权
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("producer offer event: " + event);
            eventQueue.addLast(event);
            eventQueue.notify(); //唤醒一个阻塞等待的线程
        }
    }

    /**
     * 消费数据
     */
    public Event take() {
        synchronized (eventQueue) {
            if (eventQueue.isEmpty()) {
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
            eventQueue.notify();
            return event;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SingleThreadCommunication demo = new SingleThreadCommunication();
        new Thread(() -> {
            for ( ; ; ) {
                demo.offer(new Event());
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"producer").start();
        TimeUnit.MILLISECONDS.sleep(10);

        Thread t2 = new Thread(() -> {
            for (; ;) {
                try {
                    demo.take();
                }catch (NoSuchElementException e) {
                    System.out.println("队列没有元素可获取,当前线程的状态:" + Thread.currentThread().getState());
                }
            }
        },"consumer");
        t2.start();
        TimeUnit.MILLISECONDS.sleep(10);
        t2.interrupt();
        System.out.println("当前线程的状态: " + t2.getState());
    }

}
