package com.chige.thread_local;

import java.lang.reflect.Field;

/**
 * @Author wangyc
 * @Description TODO
 * @Date 2024/12/14 14:32
 */
public class ThreadLocalTest {

    public static void main(String[] args) throws Exception {
        ThreadLocalTest test = new ThreadLocalTest();
        test.getThreadLocalHashCode_test();

    }

    /**
     * 通过反射来获取ThreadLocal中的 threadLocalHashCode 属性
     */
    private void getThreadLocalHashCode_test() throws NoSuchFieldException, IllegalAccessException {
        for (int i = 0; i < 5; i++) {
            ThreadLocal<String> t1 = new ThreadLocal<>();
            Field threadLocalHashCode = t1.getClass().getDeclaredField("threadLocalHashCode");
            threadLocalHashCode.setAccessible(true);
            System.out.println("threadLocalHashCode: " + threadLocalHashCode.get(t1));
        }
    }
}
