package com.chige.thread.future;

import org.apache.poi.ss.formula.functions.T;

import java.util.concurrent.*;

/**
 * @author wangyc
 * @date 2022/4/23
 */
public class FutureTaskDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        Callable callable = new Callable() {

            /**
             * Computes a result, or throws an exception if unable to do so.
             *
             * @return computed result
             * @throws Exception if unable to compute a result
             */
            @Override
            public Object call() throws Exception {
                System.out.println("开始执行任务");
                Thread.sleep(1000);
                System.out.println("异步任务执行结果");
                return 1;
            }
        };
        FutureTask futureTask = new FutureTask(callable);
        long l1 = System.currentTimeMillis();
        Thread thread = new Thread(futureTask);
        thread.start();
        long l2 = System.currentTimeMillis();
        System.out.println("耗时" + (l2 - l1));
        if (futureTask.isDone()) {
            try {
                Object result = futureTask.get();
                System.out.println("结果是：" + result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("任务未执行完成");
            long l3 = System.currentTimeMillis();
            Object o = futureTask.get();
            System.out.println("执行结果为：" + o + "耗时:" + (System.currentTimeMillis() - l3) + "ms");
        }
    }
}
