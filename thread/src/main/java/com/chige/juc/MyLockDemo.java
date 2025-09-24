package com.chige.juc;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @Author wangyc
 * @Description 自定义同步器
 * @Date 2025/2/21 14:10
 */
public class MyLockDemo {

    private static class Sync extends AbstractQueuedSynchronizer {

        /**
         * 判断当前线程是否持有锁
         * @return
         */
        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        /**
         * 尝试获取锁
         */
        @Override
        protected boolean tryAcquire(int arg) {
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }


        /**
         * 尝试释放锁
         */
        @Override
        protected boolean tryRelease(int arg) {
            if (getState() == 0) {
                throw new IllegalMonitorStateException("Lock is already released");
            }
            //释放锁流程
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }
    }

    private final Sync sync = new Sync();

    private void lock() {
        sync.acquire(1);
    }

    private void unLock() {
        sync.release(1);
    }

}
