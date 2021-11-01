package com.chige.thread.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/** 自定义线程池，继承线程工厂
 * @author wangyc
 * @date 2021/9/10
 */
public class MyThreadPoolUtils implements ThreadFactory {
    private final String namePrefix;
    private final AtomicInteger nextId = new AtomicInteger(1);

    MyThreadPoolUtils(String whatFeatureOfGroup) {
        // 强调线程一定要有特定意义的名称，方便出错时回溯
        namePrefix = "UserThreadFactory's" + whatFeatureOfGroup + "-Worker-";
    }

    @Override
    public Thread newThread(Runnable task) {
        return null;
//        String name = namePrefix + nextId.getAndIncrement();
//        Thread thread = new Thread(null, task, name, 0, false);
//        System.out.println(thread.getName());
//        return thread;
    }
}
class Task implements Runnable {
    private final AtomicLong count = new AtomicLong(0L);
    @Override
    public void run() {
        System.out.println("running_" + count.getAndIncrement());
    }
}
