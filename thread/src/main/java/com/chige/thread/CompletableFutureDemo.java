package com.chige.thread;


import com.chige.thread.util.ThreadPoolUtils;

import java.io.IOException;
import java.util.concurrent.*;
import java.util.function.Function;

/**
 * @author wangyc
 * @date 2022/8/13
 */
public class CompletableFutureDemo {

    public static void test1() throws ExecutionException, InterruptedException {
        ApiClient apiClient = new ApiClientImpl();
        Callable callable1 = () -> {
            String userInfo = apiClient.getUserInfo();
            System.out.println("线程名：" + Thread.currentThread().getName() + ",获取用户信息:" + userInfo);
            return 1;
        };
        Callable callable2 = () -> {
            String userAge = apiClient.getUserAge();
            System.out.println("线程名：" + Thread.currentThread().getName() + ",获取用户年龄:" + userAge);
            return 2;
        };
        Callable callable3 = () -> {
            String userName = apiClient.getUserName();
            System.out.println("线程名：" + Thread.currentThread().getName() + ",获取用户姓名:" + userName);
            return 3;
        };

        Future future1 = ThreadPoolUtils.execute(callable1);
        Future future2 = ThreadPoolUtils.execute(callable2);
        Future future3 = ThreadPoolUtils.execute(callable3);

        CompletableFuture completableFuture = CompletableFuture.completedFuture(future1.get());
        System.out.println("获取future1结果：" + completableFuture.get());

        CompletableFuture completableFuture2 = CompletableFuture.completedFuture(future2.get());
        System.out.println("获取future2结果：" + completableFuture2.get());

        CompletableFuture completableFuture3 = CompletableFuture.completedFuture(future3.get());
        System.out.println("获取future3结果：" + completableFuture3.get());
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {

//        taskCallback();
//        testWhenComplete();
        taskCallback();
    }

    /**
     * 任务完成后回调
     */
    public static void taskCallback() {
        long s1 = System.currentTimeMillis();
        ApiClient apiClient = new ApiClientImpl();
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            String userInfo = null;
            try {
                userInfo = apiClient.getUserInfo();
                System.out.println("线程名：" + Thread.currentThread().getName() + ",获取用户信息:" + userInfo);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "1";
        }, ThreadPoolUtils.getThreadPoll());

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            String userAge = null;
            try {
                userAge = apiClient.getUserAge();
                System.out.println("线程名：" + Thread.currentThread().getName() + ",获取用户年龄:" + userAge);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "2";
        });

        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            String userName = null;
            try {
                userName = apiClient.getUserName();
                System.out.println("线程名：" + Thread.currentThread().getName() + ",获取用户名称:" + userName);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "3";
        });
        // 同步动作
        System.out.println("耗时：" + (System.currentTimeMillis() - s1));
        //同步动作执行完
        future1.whenComplete((result, exception) -> System.out.println("当前线程名：{}" + Thread.currentThread().getName() + ",获取future1结果：" + result));
        future2.whenComplete((result, exception) -> System.out.println("当前线程名：{}" + Thread.currentThread().getName() + ",获取future2结果：" + result));
        future3.whenComplete((result, exception) -> System.out.println("当前线程名：{}" + Thread.currentThread().getName() + ",获取future3结果：" + result));
    }

    /**
     * 任务提交
     * supplyAsync表示创建带返回值的异步任务的，相当于ExecutorService submit(Callable<T> task) 方法
     * runAsync表示创建无返回值的异步任务，相当于ExecutorService submit(Runnable task)方法
     * exceptionally(Function)方法指定某个任务执行异常时执行的回调方法，会将抛出异常作为参数传递到回调方法中
     */
    public static void testSupplyAsync_RunAsync() throws ExecutionException, InterruptedException {
        //任务提交：（1）创建带返回值的任务
        CompletableFuture<Integer> supplyAsync = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getName() + "开始执行：" + System.currentTimeMillis());
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("当前线程：" + Thread.currentThread().getName() + "执行完成：" + System.currentTimeMillis());
            return 1;
        });
        supplyAsync.join();
        System.out.println("主线程：" + Thread.currentThread().getName() + "开始执行：" + System.currentTimeMillis());
        Function<Throwable, Integer> function = e -> {
            System.out.println("捕捉到异常：" + e.getMessage());
            return 0;
        };
        //如果执行任务抛出异常，捕捉之后会执行function对象中的逻辑
        CompletableFuture<Integer> exceptionally = supplyAsync.exceptionally(function);
        System.out.println("是否相等：" + (exceptionally == supplyAsync));
        Integer exceptionValue = exceptionally.get();
        System.out.println("异常返回值为:" + exceptionValue);
        try {
            Integer supplyResult = supplyAsync.get();
            System.out.println("带返回值的任务执行完成,返回值为: " + supplyResult + "异常返回值为:" + exceptionValue);
        }catch (Throwable e) {
            System.out.println("存在异常情况");
        }

        //任务提交：（2）创建无返回值的任务
        CompletableFuture.runAsync(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getName() + "开始执行：" + System.currentTimeMillis());
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("当前线程：" + Thread.currentThread().getName() + "执行完成：" + System.currentTimeMillis());
        });

    }

    /**
     * 异步回调：
     * （1）thenApply(Function):表示某个任务执行完成后执行的动作，即回调方法，会将该任务的执行结果即方法返回值作为入参传递到回调方法中；thenApply方法所使用的线程池是创建异步任务时的线程池
     * （2）thenApplyAsync(Function,Executor): 与thenApply相差之处在于可以使用自定义线程池。
     * （3）thenAccept() 同 thenApply 接收上一个任务的返回值作为参数，但是无返回值；
     * （4）thenRun() 没有入参，也没有返回值
     */
    public static void testThenApply_ThenApplyAsync() throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        //1、创建异步执行任务
        CompletableFuture<Integer> supplyAsync = CompletableFuture.supplyAsync(() -> {
            System.out.println("创建任务,当前线程为：" + Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        }, forkJoinPool);
        CompletableFuture thenApply = supplyAsync.thenApply(result -> {
            System.out.println("当前线程为：" + Thread.currentThread().getName() + ",获取到的结果为: " + result + "根据结果处理其他逻辑");
            return result + 1;
        })
            .thenAccept(result2 -> System.out.println("thenAccept方法获取到的入参为:" + result2))
            .thenRun(() -> System.out.println("无入参，无返回参"));
        System.out.println("任务返回值为: " + supplyAsync.get() + ",最终结果为：" + thenApply.get());

        CompletableFuture<Integer> thenApplyAsync = supplyAsync.thenApplyAsync(result -> {
            System.out.println("当前线程为：" + Thread.currentThread().getName() + "，根据结果处理其他逻辑");
            return result + 4;
        });
        System.out.println("任务返回值为：" + supplyAsync.get() + ",使用线程池的最终结果为：" + thenApplyAsync.get());
    }

    /**
     * (1) whenComplete是当某个任务执行完成后执行的回调方法，会将执行结果或者执行期间抛出的异常传递给回调方法，
     * 如果是正常执行则异常信息为null，如果存在异常则返回结果为null。回调方法对应的CompletableFuture的result和该任务一致
     * (2) whenCompleteAsync()：意思同上。
     * 像xxxAsync()的方法都是可使用自定义线程池的。
     * (3) handle: 跟whenComplete基本一致，区别在于handle的回调方法有返回值，且handle方法返回的CompletableFuture的result是回调方法的执行结果或者回调方法执行期间抛出的异常，
     *             与原始CompletableFuture的result无关了.
     */
    public static void testWhenComplete() throws ExecutionException, InterruptedException {
        //1、创建异步执行任务
        CompletableFuture<String> supplyAsync = CompletableFuture.supplyAsync(() -> {
            System.out.println("创建任务,当前线程为：" + Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (true) {
                throw new RuntimeException("手动抛出异常");
            }
            return "haha";
        }, ThreadPoolUtils.getThreadPoll()).whenCompleteAsync((result, e) -> {
            System.out.println("当前线程为：" + Thread.currentThread().getName() + "结果为：" + result + "，异常信息为" + e);
        });

        CompletableFuture<Integer> supplyAsync2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("创建任务,当前线程为：" + Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        });
        long s1 = System.currentTimeMillis();
        supplyAsync2.join();
        System.out.println("中间执行哈哈哈,耗时：" + (System.currentTimeMillis() - s1));
        String r1 = supplyAsync.get();
        System.out.println("后面执行哈哈哈,耗时：" + (System.currentTimeMillis() - s1) + "结果为：" + r1);

        supplyAsync.whenComplete((result, e) -> {
            System.out.println("当前线程为：" + Thread.currentThread().getName() + "结果为：" + result + "，异常信息为" + e);
        });

        supplyAsync2.whenComplete((result, e) -> {
            System.out.println("当前线程为：" + Thread.currentThread().getName() + "结果为：" + result + "，异常信息为" + e);
        });

        System.out.println(supplyAsync.get());
        System.out.println(supplyAsync2.get());

        //如果supplyAsync方法中无异常，则whenComplete.get的结果和supplyAsync.get的结果是一样的
        CompletableFuture<String> whenComplete = supplyAsync.whenComplete((result, throwable) -> {
            System.out.println("当前线程为：" + Thread.currentThread().getName() + "结果为：" + result + "，异常信息为" + throwable);
        });
        CompletableFuture<Integer> newCompletableFuture = supplyAsync.handle((result, throwable) -> {
            System.out.println("当前线程为:" + Thread.currentThread().getName() + "任务执行结果为：" + result + ",异常信息为：" + throwable);
            return 10;
        });
        Integer newValue = newCompletableFuture.get();
        System.out.println("返回新的completableFuture方法，值为：" + newValue);

        //如果任务执行异常，则get方法会抛出异常
        String whenCompleteRes = whenComplete.join();
        System.out.println("异步回调处理结果为：" + whenCompleteRes);
    }

    public static void thenCombine() throws ExecutionException, InterruptedException {
        long s1 = System.currentTimeMillis();

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return " world";
        });

        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return " I'm robot";
        });

        CompletableFuture<String> combineFuture = future1.thenCombine(future2, (resultA, resultB) -> resultA + resultB)
                .thenCombine(future3, (resultAB, resultC) -> resultAB + resultC);

        System.out.println(combineFuture.get());
        System.out.println("耗时:" + (System.currentTimeMillis() - s1));

    }

    public static void thenCompose() throws ExecutionException, InterruptedException {
        long s1 = System.currentTimeMillis();
        CompletableFuture<String> result = CompletableFuture.supplyAsync(() -> {
            // 第一个实例的结果
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        }).thenCompose(resultA -> CompletableFuture.supplyAsync(() -> {
            // 把上一个实例的结果传递到这里
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return resultA + " world";
        })).thenCompose(resultAB -> CompletableFuture.supplyAsync(() -> {
            // 到这里大家应该很清楚了
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return resultAB + ", I'm robot";
        }));

        System.out.println(result.get());
        System.out.println("耗时:" + (System.currentTimeMillis() - s1));
    }

    interface ApiClient {
        String getUserName() throws InterruptedException;

        String getUserAge() throws InterruptedException;

        String getUserInfo() throws InterruptedException;
    }

    static class ApiClientImpl implements ApiClient {

        @Override
        public String getUserName() throws InterruptedException {
            TimeUnit.SECONDS.sleep(1);
            return "王总";
        }

        @Override
        public String getUserAge() throws InterruptedException {
            TimeUnit.SECONDS.sleep(2);
            return "18岁";
        }

        @Override
        public String getUserInfo() throws InterruptedException {
            TimeUnit.SECONDS.sleep(3);
            return "{name:王总,age:18}";
        }
    }

}
