package com.chige.optional;

import com.google.common.base.Optional;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/** Guava - Optional的使用
 * @author wangyc
 * @date 2021/12/24
 */
public class GuavaOptionalTest {
    /**
     *  Optional 静态类的使用
     */
    public static void test1() {
        /**
         *  Optional.of(T) 创建指定引用的Optional实例，若引用为null 则快速失败
         */
        Optional<Integer> possible = Optional.of(5);
        System.out.println(possible.isPresent());
        System.out.println(possible.get());
        Optional<String> o2 = Optional.of(null); // 直接抛出空指针异常

        /**
         *  Optional.absent() 创建引用缺失的Optional实例
         */
        Optional<Object> absent = Optional.absent();

        /**
         *  Optional.fromNullable(T) 创建指定引用的Optional实例，若引用为null，则表示缺失
         *  相当于of方法 和 absent方法的结合，如果引用为null, 则等价于Optional.absent()， 如果不为null, 则等价于Optional.of(T);
         */
        Optional<Object> objectOptional = Optional.fromNullable(null);
    }

    /**
     *  Optional 非静态方法使用
     */
    public static void test2() {
        /**
         *  boolean isPresent()方法: 如果Optional包含非null的引用，返回true
         */
        Optional<Integer> o1 = Optional.of(1);
        Optional<Object> o2 = Optional.fromNullable(null);
        Optional<Object> o3 = Optional.absent();
        Optional<List<Integer>> o4 = Optional.of(Arrays.asList(1, 2, 3));
        System.out.println(o1.isPresent()); // 返回true
        System.out.println(o2.isPresent()); // 返回false
        System.out.println(o3.isPresent()); //返回false

        /**
         * T get() : 返回Optional所包含的引用，若引用缺失，则抛出java.lang.IllegalStateException
         */
        System.out.println(o1.get()); // 返回值： 1
//        System.out.println(o2.get()); // 返回值引用缺失，抛出异常
//        System.out.println(o3.get()); // 返回值引用缺失，抛出异常

        /**
         *  T or(T) 返回Optional所包含的引用，若引用缺失，返回指定的值
         */
        System.out.println(o1.or(2)); // 引用存在，返回 1
        System.out.println(o2.or(3)); // 引用缺失，返回指定值 3
        System.out.println(o3.or(4)); // 引用缺失，返回指定值 4

        /**
         *  T orNull() 返回Optional所包含的引用，若引用缺失，则返回null
         */
        System.out.println(o1.orNull()); // 返回值：1
        System.out.println(o2.orNull()); // 返回值：null
        System.out.println(o3.orNull()); // 返回值：null

        /**
         *  Set<T> asSet() 返回Optional所包含引用的单例不可变集，如果引用存在，返回一个只有单一元素的集合，如果引用缺失，返回一个空集合
         */
        Set<Integer> s1 = o1.asSet();
        Set<Object> s2 = o2.asSet();
        Set<Object> s3 = o3.asSet();
        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);
        Set<List<Integer>> lists = o4.asSet();
        System.out.println(lists);
    }
    public static void test3() {
        String s = "";
        String or = "随机值";
        String uuid = Optional.fromNullable(s).or(or);
        System.out.println(uuid);
    }

    public static void main(String[] args) {
        test3();

    }


}

