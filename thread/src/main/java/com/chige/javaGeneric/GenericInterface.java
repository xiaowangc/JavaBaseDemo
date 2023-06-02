package com.chige.javaGeneric;

/** 泛型接口
 * @author wangyc
 * @date 2022/3/27
 */
public interface GenericInterface<T> {
    // 泛型参数 - 抽象方法
    void abstractMethod(T t);

    // 返回参数类型为泛型类型
    T abstractMethod2(T t);

}

/**
 * 实现一个泛型接口：未指定参数类型，具体实现类也为泛型类
 * @param <T>
 */
class GenericImpl<T> implements GenericInterface<T> {

    @Override
    public void abstractMethod(T t) {

    }

    @Override
    public T abstractMethod2(T t) {
        return null;
    }
}

/**
 *  实现一个泛型接口：指定参数类型，具体实现类为普通类
 */
class GenericImpl2 implements GenericInterface<String> {

    @Override
    public void abstractMethod(String s) {

    }

    @Override
    public String abstractMethod2(String s) {
        return null;
    }
}
