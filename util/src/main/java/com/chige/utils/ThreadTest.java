package com.chige.utils;

import java.util.concurrent.*;

/**
 * @Author wangyc
 * @Description TODO
 * @Date 2025/3/12 19:14
 */
public class ThreadTest {

    static BlockingQueue blockingQueue = new LinkedBlockingQueue<>(5000);
    // 线程池定义
    static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 20, 10, TimeUnit.SECONDS, blockingQueue);

    public static void main(String[] args) {
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "1";
        }, threadPoolExecutor);


        CompletableFuture<Integer> stringCompletableFuture2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1400);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 2;
        }, threadPoolExecutor);

        long start1 = System.currentTimeMillis();
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(stringCompletableFuture, stringCompletableFuture2);
        try {
            Person person = new Person();
            voidCompletableFuture.thenRun(() -> {
                try {
                    String s = stringCompletableFuture.get();
                    Integer i = stringCompletableFuture2.get();
                    person.setSex(s);
                    person.setAge(i);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).get();
            System.out.println("person sex=" + person.getSex() + "age=" + person.getAge() + "耗时:" + (System.currentTimeMillis() - start1));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        //使用main进程需要手动释放线程池资源，不然会一直在运行
        threadPoolExecutor.shutdown();
        System.out.println("结束");
    }

    static class Person {
        String sex;
        Integer age;

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }
    private static String apiOne(String param) {
        return "接口返回值";
    }
    private static String apiTwo(String param) {
        return "接口返回值";
    }
}
