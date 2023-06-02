package com.chige.collection.queue.demo;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/** 延迟队列抽象类的子类
 * @author wangyc
 * @date 2022/6/25
 */
public class DelayQueueImpl extends AbstractDelayQueue<String> {


    @PostConstruct
    public void init() {
        super.init("抽象类子类1");
        super.executorService.submit(this);
    }


    /**
     *
     */
    @Override
    public void run() {
        while (!isClose.get()) {
            try {
                // 获取延迟队列中的数据并进行消费
                DelayElement<String> delayElement = delayQueue.take();
                String data = delayElement.getData();
                //消费数据的逻辑

            } catch (Exception e) {
                System.out.println("重试消费出错");
            }
        }
    }


    @Override
    Thread createThread(String threadName) {
        Thread thread = new Thread(this, threadName);
        thread.setDaemon(true);
        return thread;
    }

    @PreDestroy
    public void shutDown() {
        super.shutDown();
    }
}
