package com.chige.cache.caffeine.demo;

import com.github.benmanes.caffeine.cache.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.IntStream;

/**
 * @author wangyc
 * @date 2022/8/9
 */
public class PopulationDemo {

    /**
     * 常见写法
     */
    public static void test1() {
        Cache<String, String> stringCache = Caffeine.newBuilder()
                // 5秒没有读写自动删除
                .expireAfterAccess(5, TimeUnit.SECONDS)
                // 最大容量1024，超过会自动清除空间
                .maximumSize(1024)
                // 清理通知：key-value健值对、cause清理原因
                .removalListener(((key, value, cause) -> {

                }))
                .build();

        // 添加值
        stringCache.put("张三", "老六了");
        // 获取值
        stringCache.getIfPresent("张三");
        // 等同remove
        stringCache.invalidate("张三");
    }


    /**
     * 填充策略：指如何在key不存在的情况下，如何创建一个对象进行返回，主要分为下面四种：
     * 手动(Manual)
     * 自动(Loading)
     * 异步手动(Asynchronous Manual)
     * 异步自动(Asynchronously Loading)
     */
    public static void populationTest() {
        // 手动填充
        Cache<String, Object> manualCache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .maximumSize(100)
                .build();

        // 自动填充


        //异步手动填充

        //异步自动填充
    }

    // 填充策略-手动
    public static void testManual() {
        Cache<String, Integer> stringIntegerCaffeine = Caffeine.newBuilder().build();

        Integer integer = stringIntegerCaffeine.getIfPresent("张三");
        System.out.println(integer); //null

        //当k不存在时，会立即创建对象出来
        String key = "张三";
        Integer integer1 = stringIntegerCaffeine.get(key, k -> {
            System.out.println("k=" + k); //k=张三
            return 18;
        });
        System.out.println(integer1);//18

        // 总结：使用get方法优于getIfPresent方法。
        //      原因：get方法是以阻塞的方式执行调用的，可以避免与其他线程的写入竞争。


    }

    // 填充策略-自动
    public static void testLoading() {
        //自定义CacheLoader
        CacheLoader<String, Integer> cacheLoader = k -> {
            System.out.println("自动填充k=" + k); //自动填充k=张三
            return IntStream.range(1,10).sum();
        };
        LoadingCache<String, Integer> loadingCache = Caffeine.newBuilder().build(cacheLoader);

        String key = "张三";
        System.out.println(loadingCache.getIfPresent(key)); //null

        //当key不存在的时候，会根据给定的CacheLoader自动填充进去
        System.out.println(loadingCache.get(key)); // 18
        System.out.println(loadingCache.get("里斯")); // 18
        System.out.println(loadingCache.get("里斯1")); // 18

    }

    // 填充策略-异步自动
    public static void testAsynchronouslyLoading() throws ExecutionException, InterruptedException, TimeoutException {
        //自定义CacheLoader
        CacheLoader<String, Integer> cacheLoader = k -> {
            System.out.println("自动填充k=" + k); //自动填充k=张三
            return 18;
        };
        AsyncLoadingCache<String, Integer> asyncLoadingCache = Caffeine.newBuilder().buildAsync(cacheLoader);

        String key = "张三";
        CompletableFuture<Integer> completableFuture = asyncLoadingCache.get(key);
        Integer age = completableFuture.get(1, TimeUnit.SECONDS);
        System.out.println("age=" + age);
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        testLoading();
    }
}
