package com.chige.lock.AQS;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author wangyc
 * @Description 基于lock实现的可重入锁
 * @Date 2024/12/19 16:14
 */
public class ReentrantLockDemo {

    /**
     * 初始化
     */
    private void test_InitDemo() {
        ReentrantLock lock = new ReentrantLock(true);
        lock.lock();
        try {
            //todo do something
        } finally {
            lock.unlock();
        }
    }

}
