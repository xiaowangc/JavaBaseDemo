package com.chige.streamFunction_Lambda.Function;

/**
 * @author wangyc
 * @date 2021/6/5 14:39
 */

import java.util.function.Function;

/**
 *  函数式接口Function
 */
public class FunctionDemo {
    /**
     * Function函数式接口的apply方法使用
     */
    public void FunctionApplyMethod1() {
        Function<Dog,String> function1 = (dog) -> {
            String name = dog.name + "had renamed";
            System.out.println("Black is "+ dog.age + " age");
            return name;
        };
        String name = function1.apply(new Dog(1, 2, "Black"));
        System.out.println(name);
    }
    // TODO **接收一个函数式接口作为形参，调用apply方法作为核心功能，具体的行为由Lambda表达式传递
    public void apply_compute(int num, Function<Integer,Integer> function) {
        Integer re = function.apply(num);
        System.out.println(re);
    }
    public void apply_coverToString(int num, Function<Integer,String> function) {
        String re = function.apply(num);
        System.out.println(re);
    }


    public static void main(String[] args) {
        FunctionDemo demo = new FunctionDemo();
        // Lambda表达式用来定义加法运算规则 相乘
        Function<Integer,Integer> plus = value -> value + value;
        // 定义乘法运算规则
        Function<Integer,Integer> multiply = value -> value * value;
        // 算术-加
        demo.apply_compute(3, plus);
        // 算术-乘
        demo.apply_compute(2, multiply);

    }
    public class Dog {
        Integer age;
        Integer weight;
        String name;

        public Dog(Integer age, Integer weight, String name) {
            this.age = age;
            this.weight = weight;
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public Integer getWeight() {
            return weight;
        }

        public void setWeight(Integer weight) {
            this.weight = weight;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Dog{" +
                    "age=" + age +
                    ", weight=" + weight +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
