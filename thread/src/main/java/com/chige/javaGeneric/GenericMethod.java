package com.chige.javaGeneric;


import java.util.List;

/** 泛型方法
 * 判断一个方法是否是泛型方法关键看: 方法返回值前面有没有使用<>标记的类型，有就是，没有就不是
 * @author wangyc
 * @date 2022/3/27
 */
public class GenericMethod {
    /**
     * 不是泛型方法
     */
    public static void notGenericMethod(List<? extends Number> list) {

    }

    /**
     *  无返回参数的泛型方法
     *
     */
    public static <T> void method(T t) {

    }
    /**
     *  带返回参数的泛型方法 - 返回参数类型为泛型类型
     */
    public static <T> T method2(T t) {
        return t;
    }
    /**
     *  带返回参数的泛型方法 - 返回参数类型为具体类型
     */
    public static <T> Integer method3(List<? extends Number> t) {
        return Integer.MIN_VALUE;
    }

    public static void main(String[] args) {

    }

}
