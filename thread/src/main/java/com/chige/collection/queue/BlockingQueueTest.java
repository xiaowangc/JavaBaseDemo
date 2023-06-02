package com.chige.collection.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/** 各种高并发编程场景中，由于其本身 FIFO 的特性和阻塞操作的特点，经常被作为Buffer （数据缓冲区）使用。
 * @author wangyc
 * @date 2021/9/13
 */
public class BlockingQueueTest {

    public static void testLinkedQueue() {
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(5);
        for (int i = 0; i < 5; i++) {
            queue.add(i);
        }
    }

    public static void main(String[] args) {
        testLinkedQueue();
        String s1 = "[{\"fansNum\":22,\"doctorId\":\"200060151\"},{\"fansNum\":1,\"doctorId\":\"200008302\"},{\"fansNum\":1,\"doctorId\":\"15330\"}]";
        String s2 = "[{\"fansNum\":22,\"doctorId\":\"200060151\"},{\"fansNum\":1,\"doctorId\":\"15330\"},{\"fansNum\":1,\"doctorId\":\"200008302\"}]";
        System.out.println(s1.equals(s2));
    }
}
