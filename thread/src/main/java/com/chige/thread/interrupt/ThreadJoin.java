package com.chige.thread.interrupt;

/** join方法的一个可中断方法
 * join方法表示让当前线程在等待指定线程结束后再继续执行
 * @author wangyc
 * @date 2022/7/14
 */
public class ThreadJoin {

    public static void main(String[] args) {
        ThreadJoin test = new ThreadJoin();
        try {
            test.test_thread_join();
        } catch (InterruptedException e) {
            System.out.println("主线程捕捉异常：" + e.getMessage());
        }
    }

    /**
     * 测试使用线程的join方法
     */
    private void test_thread_join() throws InterruptedException {
        Thread t = new Thread(() -> {
            System.out.println("hello");
        });
        System.out.println("start");
        t.start(); // 启动t线程
        t.join(); // 此处main线程会等待t结束
        System.out.println("end");
    }


}
