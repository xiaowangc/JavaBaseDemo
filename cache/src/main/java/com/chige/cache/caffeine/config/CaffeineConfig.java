package com.chige.cache.caffeine.config;

import com.github.benmanes.caffeine.cache.*;
import com.google.common.testing.FakeTicker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author wangyc
 * @date 2022/8/10
 */
@Configuration
public class CaffeineConfig {

    @Value("${caffeine.initialCapacity}")
    private Integer initialCapacity;
    @Value("${caffeine.maximumSize}")
    private Long maximumSize;
    @Value("${caffeine.maximumWeight}")
    private Long maximumWeight;
    @Value("${caffeine.expireAfterAccess}")
    private Long expireAfterAccess;
    @Value("${caffeine.expireAfterWrite}")
    private Long expireAfterWrite;
    @Value("${caffeine.refreshAfterWrite}")
    private Long refreshAfterWrite;
    @Value("${caffeine.weakKeys}")
    private Boolean weakKeys;
    @Value("${caffeine.weakValues}")
    private Boolean weakValues;
    @Value("${caffeine.softValues}")
    private Boolean softValues;

    /**
     * 创建本地缓存方式一
     * @return
     */
    @Bean("manualCaffeine")
    public Cache<String, String> manualCaffeine() {
        return Caffeine.newBuilder().expireAfterWrite(expireAfterWrite, TimeUnit.MINUTES).build();
    }

    /**
     * 创建本地缓存方式二
     * @return
     */
    @Bean("loadingCaffeine")
    public Cache<String, String> loadingCaffeine() {
        CacheLoader<String, String> cacheLoader = k -> "自定义值";
        return Caffeine.newBuilder().expireAfterWrite(expireAfterWrite, TimeUnit.MINUTES).build(cacheLoader);
    }

    /**
     * 创建本地缓存方式三
     * @return
     */
    @Bean("asyncLoadingCaffeine")
    public AsyncLoadingCache<String, String> asyncLoadingCaffeine() {
        CacheLoader<String, String> cacheLoader = k -> "自定义值";
        FakeTicker ticker = new FakeTicker();
        AsyncLoadingCache<String, String> asyncLoadingCache =
                Caffeine.newBuilder().expireAfterWrite(expireAfterWrite, TimeUnit.MINUTES).ticker(ticker::read).buildAsync(cacheLoader);

        return asyncLoadingCache;
    }



}
