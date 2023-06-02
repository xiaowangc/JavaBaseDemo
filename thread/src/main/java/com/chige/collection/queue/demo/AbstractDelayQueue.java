package com.chige.collection.queue.demo;


import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author wangyc
 * @date 2022/6/25
 */
public abstract class AbstractDelayQueue<T> implements Runnable,LifeCycle {
    private final long DEFAULT_DELAY = 3000;
    protected AtomicBoolean isClose = new AtomicBoolean(false);
    protected DelayQueue<DelayElement<T>> delayQueue = new DelayQueue<>();
    protected ExecutorService executorService;

    /**
     * 初始化线程池信息
     * TODO 优化空间：每个子类共享一个线程池，但会有各自的线程
     */
    public void init(String threadName) {
        executorService = Executors.newSingleThreadExecutor(r -> createThread(threadName));
    }

    abstract Thread createThread(String threadName);

    /**
     * 关闭线程任务
     */
    public void shutDown() {
        if (isClose.compareAndSet(false, true)) {
            executorService.shutdown();
        }
    }

    /**
     * 加入队列
     */
    public void add(T data) {
        delayQueue.add(new DelayElement<>(data, DEFAULT_DELAY, TimeUnit.MILLISECONDS));
    }

    public void add(T data, int seconds) {
        delayQueue.add(new DelayElement<>(data, seconds, TimeUnit.SECONDS));
    }

    public void add(T data, long delay, TimeUnit timeUnit) {
        delayQueue.add(new DelayElement<>(data, delay, timeUnit));
    }

    /**
     * 队列元素对象
     */
    static class DelayElement<T> implements Delayed {
        // 延迟时间
        private final long delay;
        // 过期时间：毫秒
        private final long expire;
        // 队列元素
        private T data;

        public DelayElement(T data, long delay, TimeUnit timeUnit) {
            if (data == null) {
                throw new IllegalArgumentException("队列元素不能为空");
            }
            this.data = data;
            this.delay = TimeUnit.MILLISECONDS.convert(delay, timeUnit);
            this.expire = delay + System.currentTimeMillis();
        }
        /**
         * @param unit  时间单位
         * @return 返回剩余延迟时间(纳秒)： 到期时间 - 当前时间
         */
        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(expire - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }


        @Override
        public int compareTo(Delayed o) {
            return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
        }

        public long getDelay() {
            return delay;
        }

        public long getExpire() {
            return expire;
        }

        public T getData() {
            return data;
        }

        @Override
        public String toString() {
            return "DelayElement{" +
                    "delay=" + delay +
                    ", expire=" + expire +
                    ", data=" + data +
                    '}';
        }
    }



}
