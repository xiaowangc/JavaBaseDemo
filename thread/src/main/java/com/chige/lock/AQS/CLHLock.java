package com.chige.lock.AQS;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @Author wangyc
 * @Description 公平锁的一种实现方式：CLH
 * @Date 2024/12/19 18:36
 */
public class CLHLock implements Lock {

    private final ThreadLocal<CLHLock.Node> prevThreadLocal;
    private final ThreadLocal<CLHLock.Node> nodeThreadLocal;

    /**
     * 类似链表，用于将Node节点进行串联形成链表结构
     */
    private AtomicReference<CLHLock.Node> tail = new AtomicReference<>(new CLHLock.Node());

    public CLHLock() {
        prevThreadLocal = ThreadLocal.withInitial(() -> null);
        nodeThreadLocal = ThreadLocal.withInitial(CLHLock.Node::new);
    }

    @Override
    public void lock() {
        final Node node = this.nodeThreadLocal.get();
        node.locked = true;
        Node prevNode = this.tail.getAndSet(node);
        this.prevThreadLocal.set(prevNode);
        //自旋
        while (prevNode.locked);
    }

    @Override
    public void unlock() {
        final Node node = this.nodeThreadLocal.get();
        node.locked = false;
        this.nodeThreadLocal.set(this.prevThreadLocal.get());
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }



    @Override
    public Condition newCondition() {
        return null;
    }

    public static class Node {
        private volatile boolean locked;
    }
}
