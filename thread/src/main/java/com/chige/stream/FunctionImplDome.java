package com.chige.stream;

/**
 * @author wangyc
 * @date 2021/6/5 10:45
 */
public class FunctionImplDome {

    public static void main(String[] args) {
        // 将lambda表达式的行为传递给函数式接口
        FunctionalInterfaceDome dome1 = a -> a.length() + a.length();
        FunctionalInterfaceDome dome2 = a -> a.length();
        plus(dome1);
        plus(dome2);
    }

    //TODO 尝试使用函数式接口实现策略模式，看是否能够这样做

    public static void plus(FunctionalInterfaceDome dome) {
        String a = "1231231231";
        System.out.println("the a's length is " + dome.plus(a));
    }
}
