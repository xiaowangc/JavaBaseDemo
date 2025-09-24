package com.chige.lock.AQS.readWrite;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.StampedLock;

/**
 * @Author wangyc
 * @Description 读写锁 + 乐观锁（支持在读锁情况下，保留写锁的获取，但在读取完成后需要验证数据的一致性）
 * 适用场景：读远远大于写，同时对数据一致性要求不高
 * @Date 2025/1/12 12:10
 */
public class StampLockDemo {

    private int count = 0;
    private StampedLock lock = new StampedLock();


    /**
     * 首先尝试获取乐观读锁，并读取计数器的值，
     * 然后通过 validate 方法验证数据的一致性。
     * 如果验证失败，则获取悲观读锁，并重新读取计数器的值
     *
     */
    private int getCount() {
        //获取到乐观读锁
        long stamp = lock.tryOptimisticRead();
        int value = count;
        if (!lock.validate(stamp)) { //校验不通过，则获取悲观读锁
            stamp = lock.readLock();
            try {
                value = count;
            }finally {
                lock.unlockRead(stamp);
            }
        }
        return value;
    }

    private void increment() {
        long stamp = lock.writeLock();
        try {
            count++;
        }finally {
            lock.unlockWrite(stamp);
        }
    }
}
