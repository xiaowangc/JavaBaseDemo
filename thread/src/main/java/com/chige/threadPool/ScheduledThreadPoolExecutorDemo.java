package com.chige.threadPool;

import cn.hutool.core.thread.ThreadFactoryBuilder;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author wangyc
 * @Description TODO
 * @Date 2025/1/12 14:18
 */
public class ScheduledThreadPoolExecutorDemo {


    private ScheduledThreadPoolExecutor poolExecutor = new ScheduledThreadPoolExecutor(2);

    private void testScheduleWithFixedDelay() {


        poolExecutor.scheduleWithFixedDelay(() -> {
            try {
                System.out.println("scheduleWithFixedDelay ...");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 1, 1, TimeUnit.SECONDS);
    }


}
