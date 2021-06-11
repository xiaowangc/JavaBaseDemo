package com.chige.stream;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** 中间操作-有状态
 * @author wangyc
 * @date 2021/6/8 11:19
 */
public class StreamMidTest_stateFul {

    /**
     *  distinct 流中的元素必须实现hashCode和equals方法
     *  去重之后还是按照原流中的排序顺序输出的，所以是有序的！
     */
    public void distinct() {
        List<String> list = Arrays.asList("a","b","a","c","d","f","b");
        List<String> collect = list.stream().distinct().collect(Collectors.toList());
        collect.forEach(System.out::println);
    }

    /**
     *  sorted 有两种形式 有参和无参，有参的可以传入自定义比较器
     */
    public void sorted() {
        // 无参
        Stream<Integer> stream = Stream.of(1,3,4,6,1,2,67,2);
        // 去重并排序
        stream.sorted().distinct().forEach(System.out::println);

        List<Person> personList = Arrays.asList(new Person(1L,"x1",10),
                new Person(2L,"x2",13),
                new Person(5L,"x3",11));
        // 有参-自定义比较器
        personList.stream().sorted(Comparator.comparing(Person::getAge)).collect(Collectors.toList()).forEach(System.out::println);
    }

    public static void main(String[] args) {
        StreamMidTest_stateFul stateFul = new StreamMidTest_stateFul();
        stateFul.sorted();
    }
    public class Person{
        // 相当于身份证
        private Long idCard;
        private String name;

        @Override
        public String toString() {
            return "Person{" +
                    "idCard=" + idCard +
                    ", name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }

        private Integer age;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return Objects.equals(idCard, person.idCard);
        }

        @Override
        public int hashCode() {
            return Objects.hash(idCard);
        }

        public Long getIdCard() {
            return idCard;
        }

        public void setIdCard(Long idCard) {
            this.idCard = idCard;
        }

        public Person(Long idCard, String name, Integer age) {
            this.idCard = idCard;
            this.name = name;
            this.age = age;
        }

        public Person(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }

}
