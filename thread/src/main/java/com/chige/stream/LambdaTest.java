package com.chige.stream;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/** Lambda表达式练习
 * @author wangyc
 * @date 2021/6/5 10:18
 */
public class LambdaTest {
    /**
     *  1、lambda表达式可以说是Java的语法糖，用来简化一些代码
     *  2、lambda表达式分为三个部分：参数(a,b)、符号 -> 、主体 {a.length() - b.length()}
     */

    /** 原生代码与lambda表达式使用的比较 */
    public void lambdaLearnOne() {
        //1、 原生写法
        Runnable run1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("原生代码，比较繁琐");
            }
        };

        //2、 java8 写法：lambda的使用例子
        Runnable run2 = () -> {
            System.out.println("使用Lambda表达式，简化了代码");
        };


        //3、Lambda + 函数式接口创建并启动线程
        new Thread(() -> {
            System.out.println("lambda + 函数式接口创建线程");
        }).start();
    }

    public void var() {
        List<Person> personList = Arrays.asList(new Person(1,2),new Person(1,3),new Person(2,2));
        // Comparator是比较器
        Comparator<Person> personComparator = Comparator.comparingInt(Person::getAge);
        Comparator<Person> personComparator3 = Comparator.comparingInt(Person::getWeight);
        personList.sort(personComparator3.thenComparing(personComparator));
        for (Person person : personList) {
            System.out.println(person);
        }
    }

    public static void main(String[] args) {
        LambdaTest lambdaTest = new LambdaTest();
        lambdaTest.var();
    }

    public class Person{
        Integer age;
        Integer weight;
        public Person() {}
        public Person(Integer age, Integer weight) {
            this.age = age;
            this.weight = weight;
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

        @Override
        public String toString() {
            return "Person{" +
                    "age=" + age +
                    ", weight=" + weight +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return Objects.equals(age, person.age) && Objects.equals(weight, person.weight);
        }

        @Override
        public int hashCode() {
            return Objects.hash(age, weight);
        }
    }
}
