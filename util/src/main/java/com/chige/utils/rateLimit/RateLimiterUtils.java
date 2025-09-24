package com.chige.utils.rateLimit;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.TimeUnit;

/**
 * @Author wangyc
 * @Description 限流算法
 * @Date 2024/12/31 22:32
 */
public class RateLimiterUtils {

    public static void main(String[] args) throws InterruptedException {
        RateLimiterUtils utils = new RateLimiterUtils();
        utils.test_RateLimiter();
    }

    /**
     * 令牌桶算法
     */
    private void test_RateLimiter() throws InterruptedException {
        RateLimiter rateLimiter = RateLimiter.create(13d);
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                if (rateLimiter.tryAcquire(1500, TimeUnit.MILLISECONDS)) {
                    double acquire = rateLimiter.acquire();
                    System.out.println("获取到当前令牌: " +  acquire);
                } else {
                    System.out.println("error 获取不到当前令牌");
                }
            }).start();
            sleep(10000);
        }
    }

    private static void sleep(Integer time) throws InterruptedException {
        Thread.sleep(time);
    }
}
