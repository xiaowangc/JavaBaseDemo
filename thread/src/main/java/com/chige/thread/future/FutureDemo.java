package com.chige.thread.future;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class FutureDemo {

    private AtomicInteger atomicInteger = new AtomicInteger(0);
    private Integer tmp = 5;
    /** Future类相关操作
     *  线程回调练习
     */
    public void test1() throws InterruptedException, ExecutionException, TimeoutException {
        for (int i = 0; i < 20; i++) {
            Future<User> future = ThreadUtil.execAsync(new Callable<User>() {

                /**
                 * Computes a result, or throws an exception if unable to do so.
                 * call方法内部主要是用来执行业务逻辑的
                 * @return computed result
                 * @throws Exception if unable to compute a result
                 */
                @Override
                public User call() throws Exception {
                    User user = new User();
                    int i1 = atomicInteger.incrementAndGet();
                    user.setAge(i1);
                    int i2 = RandomUtil.randomInt(1, 10);
                    if (i2 == tmp) {
                        tmp = 11;
                        System.out.println("now i2=" + i2 + "Thread sleep 10s1");
                        Thread.sleep(10001);
                        throw new TimeoutException("time out");
                    }
                    return user;
                }
            });
            // 回调函数future的get方法会阻塞当前线程，如果业务逻辑一直未执行完成的话，线程会一直阻塞，因此需要给定一个
            // 一个阻塞最大时间，超出最大时间后需要报异常
            boolean done = future.isDone();
            if (done) {
                User user = future.get();
                System.out.println(JSONUtil.toJsonStr(user));
            }else {
                System.out.println("done is false");
            }
        }
    }

    public static void main(String[] args) {
        FutureDemo demo1 = new FutureDemo();
        try {
            demo1.test1();
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

}
