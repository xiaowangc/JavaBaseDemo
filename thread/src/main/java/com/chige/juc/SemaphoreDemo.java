package com.chige.juc;

import java.util.concurrent.Semaphore;

/**
 * @Author wangyc
 * @Description 信号量：一种计数信号量，可以控制同时访问共享资源的线程数量
 * 内部维护一个许可计数，线程访问共享资源之前需要获取许可acquire()， 操作完成之后需要释放许可release()
 * 应用场景：比如体育馆预约人数的控制、限制对某些共享资源的并发访问数量等
 * 缺点：Semaphore 只是控制线程的数量，并不能像 ReentrantLock 那样灵活地控制临界区内的复杂逻辑
 * @Date 2025/1/12 15:47
 */
public class SemaphoreDemo {


    void test() {
        Semaphore semaphore = new Semaphore(1);
        try {
            semaphore.acquire();

        } catch (InterruptedException e) {

        }
        ;
    }
}
