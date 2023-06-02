package com.chige.thread.synchronizeds.mylock;

import java.util.List;
import java.util.concurrent.TimeoutException;

/** 定义锁的接口
 * @author wangyc
 * @date 2022/7/16
 */
public interface Lock {

    void lock() throws InterruptedException;

    void lock(long mill) throws InterruptedException, TimeoutException;

    void unlock();

    boolean tryLock();

    List<Thread> getBlockedThreads();



}
