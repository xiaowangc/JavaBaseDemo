package com.chige.collection.queue.demo;

import javax.annotation.PostConstruct;

/**
 * @author wangyc
 * @date 2022/6/25
 */
public class DelayQueueImpl2 extends AbstractDelayQueue<Integer> {


    @PostConstruct
    public void init() {
        super.init("延迟队列抽象子类2");
    }

    @Override
    Thread createThread(String threadName) {
        Thread thread = new Thread(threadName);
        thread.setDaemon(true);
        return thread;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
    }
}
