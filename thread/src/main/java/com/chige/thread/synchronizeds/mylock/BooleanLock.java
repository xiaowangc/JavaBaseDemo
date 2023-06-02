package com.chige.thread.synchronizeds.mylock;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeoutException;

/** 实现自定义显示锁
 * @author wangyc
 * @date 2022/7/16
 */
public class BooleanLock implements Lock {
    /**
     * 持有锁的当前线程
     */
    private Thread currentThread;
    /**
     * 表示锁是否被持有
     */
    private boolean locked = false;
    /**
     * 存放阻塞的线程队列
     */
    private List<Thread> blockedThreads = new LinkedList<>();

    @Override
    public void lock() throws InterruptedException {
        synchronized (this) {
            while (locked) { //表示持有锁
                if (!blockedThreads.contains(Thread.currentThread())) {
                    blockedThreads.add(Thread.currentThread());
                }
                this.wait();
            }
            blockedThreads.remove(Thread.currentThread());
            this.currentThread = Thread.currentThread();
            this.locked = true;
        }

    }

    @Override
    public void lock(long mill) throws InterruptedException, TimeoutException {
        synchronized (this) {
            if (mill < 0) {
                this.lock();
            }else {
                long resultMill = System.currentTimeMillis() + mill;
                while (locked) {
                    if (resultMill <= 0) {
                        throw new TimeoutException("can not get the lock during " + mill + " ms.");
                    }
                    if (!blockedThreads.contains(Thread.currentThread())) {
                        blockedThreads.add(Thread.currentThread());
                    }
                    this.wait(mill);
                    resultMill = resultMill - System.currentTimeMillis();
                }
                blockedThreads.remove(Thread.currentThread());
                this.currentThread = Thread.currentThread();
                this.locked = true;
            }

        }
    }

    @Override
    public void unlock() {
        synchronized (this) {
            if (currentThread == Thread.currentThread()) {
                this.locked = false; //释放锁
                this.notifyAll();
            }
        }
    }

    /**
     * 尝试获取锁
     */
    @Override
    public boolean tryLock() {
        synchronized (this) {
            return !locked;
        }
    }

    @Override
    public List<Thread> getBlockedThreads() {
        return null;
    }
}
