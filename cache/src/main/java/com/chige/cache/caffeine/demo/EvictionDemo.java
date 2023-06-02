package com.chige.cache.caffeine.demo;


import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/** 驱逐（Eviction）：基于大小、基于时间、基于引用
 * @author wangyc
 * @date 2022/8/9
 */
public class EvictionDemo {

    //基于大小：基于缓存大小和权重
    public static void sizeBased() {
        //基于缓存的计数进行驱逐
        Caffeine.newBuilder().maximumSize(100).build();

        //基于缓存权重进行驱逐
        Caffeine.newBuilder().maximumWeight(100).build();

        //maximumWeight与maximumSize不可以同时使用
    }

    //基于时间
    public static void timeBased() {
        //基于固定的到期策略进行退出
        Caffeine.newBuilder().expireAfterAccess(10, TimeUnit.SECONDS).build();
        Caffeine.newBuilder().expireAfterWrite(10, TimeUnit.SECONDS).build();

        //基于不固定的到期策略进行退出
        Caffeine.newBuilder().expireAfter(new Expiry<String, Object>() {
            @Override
            public long expireAfterCreate(String s, Object o, long currentTime) {
                return TimeUnit.SECONDS.toNanos(5);
            }

            @Override
            public long expireAfterUpdate(String s, Object o, long l, long l1) {
                System.out.println("调用了expireAfterUpdate:" + TimeUnit.NANOSECONDS.toMillis(l1));
                return l1;
            }

            @Override
            public long expireAfterRead(String s, Object o, long l, long l1) {
                System.out.println("调用了expireAfterRead:" + TimeUnit.NANOSECONDS.toMillis(l1));
                return l1;
            }
        }).build();
    }

    // 基于引用
    public static void referenceBased() {
        // 当key和value都没有引用时驱逐
        Cache<Object, Object> cache = Caffeine.newBuilder().weakKeys().weakValues().build();
        // 当内存不足时驱逐
        Caffeine.newBuilder().softValues().build();
    }



    public static void main(String[] args) {

    }
}
