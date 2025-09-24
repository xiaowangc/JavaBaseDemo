package com.chige.funcation;

import com.chige.funcation.domain.Dog;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 使用lambda表达式进行方法引用
 *
 * @author wangyc
 * @date 2023/12/23
 */
public class LambdaMethodRefDemo {

    public static void consumer(){
        Consumer<String> consumer = System.out::println;
        consumer.accept("我是一个消费者");
    }

    //1.方法引用
    public static void callStaticMethod() {
        Consumer<Dog> consumer = Dog::bark;
        consumer.accept(new Dog());
    }

    public static void callMethod() {
        Dog dog = new Dog();
        //Function<Integer,Integer> function = a -> dog.eat(a); 等同下面表达式
        Function<Integer,Integer> function = dog::eat;

        System.out.println("还剩[" + function.apply(3) + "]斤狗粮");
    }

    private static void callMethodByClass() {

        BiFunction<Dog,Integer,Integer> biFunction  = Dog::eat;
        System.out.println("还剩[" + biFunction.apply(new Dog(),4) + "]斤狗粮");
    }

    private static void callMethod2() {

        Dog dog = new Dog();
        Function<Integer,Integer> function = dog::eat; //函数声明
        dog = null;
        System.out.println("还剩[" + function.apply(3) + "]斤狗粮");
    }


    public static void main(String[] args) {
//        LambdaMethodRefDemo.callStaticMethod();
//        LambdaMethodRefDemo.callMethod();
//        LambdaMethodRefDemo.callMethodByClass();
        LambdaMethodRefDemo.callMethod();
    }

}
