package com.chige.juc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author wangyc
 * @Description 大任务拆分成多个阶段的子任务，下一阶段的开始以来上个阶段任务的结果，可使用该同步器完成并发操作
 * 应用：如并行下载文件、并行计算等场景
 * @Date 2025/1/12 15:47
 */
public class CountDownLatchDemo {

    CountDownLatch countDownLatch = new CountDownLatch(3);
    ExecutorService executorService = Executors.newFixedThreadPool(3);

    void test() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            executorService.execute(() -> {
                //执行任务代码...
                countDownLatch.countDown(); //任务完成时计数器 减1
            });
        }

        countDownLatch.await(); //等待计数器为0

        //所有任务完成后继续执行
    }

}
