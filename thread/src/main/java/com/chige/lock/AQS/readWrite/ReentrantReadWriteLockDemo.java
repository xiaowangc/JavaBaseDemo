package com.chige.lock.AQS.readWrite;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author wangyc
 * @Description 利用锁+非线程安全容器实现 支持并发容器：适用读场景 > 写场景
 * 作用：可代替ConcurrentHashMap
 * @Date 2025/1/12 11:23
 */
public class ReentrantReadWriteLockDemo {

    private final Map<String, Object> map = new HashMap<>();

    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock r = rwLock.readLock();
    private final Lock w = rwLock.writeLock();

    private void put(String key, Object value) {
        r.lock();
        try {
            map.put(key, value);
        }finally {
            r.unlock();
        }
    }

    private Object get(String key) {
        w.lock();
        try {
            return map.get(key);
        }finally {
            w.unlock();
        }
    }


}
