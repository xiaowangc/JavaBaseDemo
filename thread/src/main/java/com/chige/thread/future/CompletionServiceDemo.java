package com.chige.thread.future;


import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.RandomUtil;
import com.chige.thread.util.ThreadPoolUtils;

import java.sql.Time;
import java.util.Random;
import java.util.concurrent.*;

public class CompletionServiceDemo {


    public void test() {
        int tmp = 10;
        for (int i = 0; i < tmp; i++) {
            Integer a = i;
            // 直接扔给线程池执行
            ThreadPoolUtils.execute2(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    int aaa = a;
                    int i1 = RandomUtil.randomInt(1, 5);
                    System.out.println(a + "sleep " + i1 + "s");
                    Thread.sleep(i1 * 1000L);
                    if (aaa == 1) {
                        return null;
                    }
                    return a;
                }
            });
        }
        CompletionService completionService = ThreadPoolUtils.getCompletionService();
        int b = 0;
        while(b < tmp) {
            try {
                //如果完成队列中没有数据, 则阻塞; 否则返回队列中的数据
                System.out.println("enter " + b);
                Future<Integer> take = completionService.poll(2,TimeUnit.SECONDS);
                System.out.println("out " + b);
                if (take != null) {
                    Integer aa = take.get(0, TimeUnit.SECONDS);
                    System.out.println("now is: " + aa);
                }else {
                    System.out.println("take is null....");
                }
                b++;
            } catch (InterruptedException exception) {
                System.out.println("the thread is interrupt");
                Thread.currentThread().interrupt();
            } catch (ExecutionException e) {
                System.out.println(e);
                e.printStackTrace();
            } catch (TimeoutException e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }
        System.out.println("exit...");
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        CompletionServiceDemo demo = new CompletionServiceDemo();
        demo.test();
        System.out.println((System.currentTimeMillis() - start) / 1000);
        System.out.println("the method is over");
    }
}
