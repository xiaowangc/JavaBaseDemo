package com.chige.funcation;


import java.util.function.*;

/** 基本函数式
 * @author wangyc
 * @date 2023/12/23
 */
public class FunctionDemo {

    //断言:该函数输入一个表达式，输出一个布尔类型
    public void predicateTest() {
        Predicate<Integer> predicate = integer -> integer > 0;
        Predicate<String> stringPredicate = str -> str.equals("a");

        IntPredicate intPredicate = value -> value > 0;
        System.out.println(predicate.test(5));
        System.out.println(intPredicate.test(-1));
        System.out.println(stringPredicate.test("b"));
    }
    //消费型函数：输入变量/对象，返回void
    public void consumerTest() {
        // 这里只是声明创建消费型函数，并不会去执行到方法
        Consumer<String> consumer = s -> {
            System.out.println("=======accept(" + s + ")===========");
            System.out.println("消费者1：输出:" + s);
        };
        //consumer 实例对象会执行上面定义好的accept方法
        consumer.accept("ab");

        //声明创建consumer2并定义accept方法
        Consumer<String> consumer2 = s -> {
            s = s + "-consumer2";
            System.out.println("消费者2：输出:" + s);
        };
        //consumer3由 consumer1和consumer2通过andThen方法生成
        // 并且定义的accept方法 = consumer1.accept();consumer2.accept();
        Consumer<String> consumer3 = consumer.andThen(consumer2);
        consumer3.accept("c");

        Consumer<String> consume4 = consumer3.andThen(consumer2);
        consume4.accept("d");
    }

    /**
     * 提供型函数：提供一个数据
     */
    public void supplier() {
        Supplier<String> supplier = () -> "我是一个提供者";
        System.out.println(supplier.get());
    }

    //一元函数式function:输入、输出不同
    // 输入类型T，返回类型K
    public void function() {
        //基本类型
        Function<Integer,String> function = x -> "数字是："+ x;
        System.out.println(function.apply(88));
        //对象类型
        Function<Person, User> functionClass = person -> {
            User user = new User();
            user.setAge(person.getPersonAge());
            return user;
        };
        System.out.println(functionClass.apply(new Person(1)).getAge());
    }



    /**
     * 一元函数：输入输出类型相同
     */
    public void unaryOperator(){
        UnaryOperator<Integer> unaryOperator = x -> ++x;
        System.out.println(unaryOperator.apply(1));
    }

    /**
     * 二元函数：输入、输出不同
     * 提供两个入参T、K，返回一个参数V
     */
    public void biFunction(){
        BiFunction<Integer,Double,Double> biFunction = (x,y) -> {
            ++x;
            ++y;
            return x+y;
        };
        System.out.println(biFunction.apply(1,2.3d));
    }

    /**
     * 二元函数：输入输出类型相同
     * 如f(x) = x + y
     */
    public void binaryOperator() {
        BinaryOperator<String> binaryOperator = (s, s2) -> s + s2;
        System.out.println(binaryOperator.apply("a", "b"));


        IntBinaryOperator intBinaryOperator = (x,y) -> x + y;
        System.out.println(intBinaryOperator.applyAsInt(2,3));
    }

    public static void main(String[] args) {
        FunctionDemo functionDemo = new FunctionDemo();

//        functionDemo.predicateTest();
        functionDemo.binaryOperator();
    }

    //

    class Person {
        Integer personAge;

        public Person(Integer personAge) {
            this.personAge = personAge;
        }

        public Integer getPersonAge() {
            return personAge;
        }

        public void setPersonAge(Integer personAge) {
            this.personAge = personAge;
        }
    }

    class User {
        Integer age;

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }

}



//断言函数Predicate 在实际场景中有什么应用呢？
