package com.chige.collection.queue;

import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/** 延时队列
 * @author wangyc
 * @date 2022/6/25
 */
public class DelayQueueTest {

    static class DelayedEle implements Delayed {
        private final long delayTime; //延迟时间
        private final long expire; //过期时间
        private String taskName; //任务名称
        /**
         * @param delayTime 延迟时间，毫秒
         * @param taskName 任务名称
         */
        public DelayedEle(long delayTime, String taskName) {
            this.delayTime = delayTime;
            this.expire = delayTime + System.currentTimeMillis();
            this.taskName = taskName;
        }

        /**
         * Returns the remaining delay associated with this object, in the
         * given time unit.
         *
         * @param unit  时间单位
         * @return 返回剩余延迟时间(纳秒)： 到期时间 - 当前时间
         */
        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(expire - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        /**
         * 用于加入队列时元素之间比较构建小顶堆的方法
         * @param o
         * @return 如果返回负数，表示this对象小于，那么this对象将比参数对象优先出队列
         */
        @Override
        public int compareTo(Delayed o) {
            //比较this对象和参数对象剩余时间长短
            //this对象剩余时间大于参数对象剩余时间，则返回大于0。那么将会设置this对象的出队列优先级小于当前对象
            //this对象剩余时间小于参数对象剩余时间，则返回小于0。那么将会设置this对象的出队列优先级大于当前对象
            return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
        }

        @Override
        public String toString() {
            return "DelayedEle{" +
                    "delayTime=" + delayTime +
                    ", expire=" + expire +
                    ", taskName='" + taskName + '\'' +
                    '}';
        }
    }


    public static void main(String[] args) throws InterruptedException {
//        //创建DelayQueue队列
        DelayQueue<DelayedEle> delayQueue = new DelayQueue<>();
        //创建并添加10个任务
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            //每个任务的延迟时间在[0,10000)毫秒之间
            DelayedEle delayedEle = new DelayedEle(random.nextInt(10000), "task " + (i + 1));
            delayQueue.add(delayedEle);
        }

        //循环获取过期任务并打印，可以看到结果就是延迟时间最短的任务最先出队，这和任务添加的顺序无关
        while (true) {
            System.out.println(delayQueue.take());
            if (delayQueue.isEmpty()) {
                break;
            }
        }
        // 将3000毫秒转为秒
        System.out.println(TimeUnit.SECONDS.convert(3000, TimeUnit.MILLISECONDS));
        // 秒转为毫秒
        System.out.println(TimeUnit.MILLISECONDS.convert(10, TimeUnit.SECONDS));

    }
}
