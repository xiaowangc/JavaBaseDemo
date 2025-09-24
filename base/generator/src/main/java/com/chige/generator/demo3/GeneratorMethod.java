package com.chige.generator.demo3;

import com.chige.generator.demo1.GeneratorClass;

import java.util.HashMap;
import java.util.Map;


/** 泛型方法
 * @author wangyc
 * @date 2023/12/23
 */
public class GeneratorMethod {

    public void create(GeneratorClass<? extends Number> generatorClass) {
        System.out.println(generatorClass.getCode());
    }

    public <T, K> T test(K a, T b) {
        Map<K, T> map = new HashMap<>();
        map.put(a, b);
        return map.get(a);
    }

    public <T extends Number> void test2(GeneratorClass<T> tGeneratorClass) {
        System.out.println(tGeneratorClass.getCode());
    }

    public <T extends Number> void test2() {
        System.out.println();
    }

    public static void main(String[] args) {
        GeneratorMethod generatorMethod = new GeneratorMethod();
        System.out.println(generatorMethod.test(1, 2));
        System.out.println(generatorMethod.test("2", "b"));
    }

}

/**
 * 如何声明方法为泛型方法：增加<T>标识即可
 * 如 public <T> void test();
 */

/**
 * 静态方法有一种情况需要注意一下，那就是在类中的静态方法使用泛型：
 * 静态方法无法访问类上定义的泛型；如果静态方法操作的引用数据类型不确定的时候，必须要将泛型定义在方法上。
 * 如 public static <T> void test(T t);
 */

/**
 * 泛型的上下边界添加，必须与泛型的声明在一起 。
 * <泛型类>的上下边界添加，声明为 class GeneratorClass<T extend Number>{}
 * <泛型方法>的上下边界添加，声明为 public <T extends Number> void test(GeneratorClass<T> tGeneratorClass){}
 */