package com.chige.javaGeneric;

/** 泛型类的声明定义
 * @author wangyc
 * @date 2022/3/27
 */
public class GenericClass<T> {
    private T name;
    private Integer size;

    public void setName(T name) {
        this.name = name;
    }
    public T getName() {
        return name;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}

/**
 *  继承一个泛型类：未指定参数类型，子类也为泛型类
 */
// Son类继承了泛型类，由于继承时未指定泛型的类型，所以Son类也需要声明为泛型类
class Son<T> extends GenericClass<T> {

}

/**
 *  继承一个泛型类：指定了参数类型，子类为普通类
 */
// Dog类继承了泛型类，而在继承泛型类时声明指定了泛型的类型（String类型）
class Dog extends GenericClass<Integer> {

}
