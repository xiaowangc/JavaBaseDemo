package com.chige.optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

/**
 * @author wangyc
 * @date 2021/6/13 17:38
 */
public class OptionalDemo {

    /**
     *  empty方法
     */
    public void empty() {
        List<Person> personList = Arrays.asList(new Person("s1",1),new Person("s2",2));
        Optional optional = Optional.empty();

        Optional.ofNullable(personList)
                .map(person -> {
                    List<Person> personList1 = new ArrayList<>();
                    person.forEach( t -> {
                        if (t.getAge() > 50) {
                            personList1.add(t);
                        }
                    });
                    return personList1;
                })
                .ifPresent(consumer -> consumer.forEach(t -> System.out.println("t = " + t)));
        // flatMap方法
        Optional.ofNullable(personList)
                .map(person -> {
                    List<Person> personList1 = new ArrayList<>();
                    person.forEach( t -> {
                        if (t.getAge() > 50) {
                            personList1.add(t);
                        }
                    });
                    return Optional.of(personList1);
                });
    }
    public void orElseGet() {
        List<Person> personList = Arrays.asList(new Person("s1",1),new Person("s2",2));
        Optional<List<Person>> optional = Optional.of(personList);
        optional.orElseGet(() -> new ArrayList<>());

    }

    public static void main(String[] args) {
        List<Person> personList = Arrays.asList(new Person("s1",1),new Person("s2",2));
        Object obj = Optional.ofNullable(personList)
                             .map(person -> {
                                 if (person != null) {
                                     System.out.println("不为空");
                                     throw new RuntimeException("异常");
                                 }
                                 return null;
                             }).orElse(Collections.emptyList());
        System.out.println(obj);
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Person {
        private String name;
        private Integer age;
    }

}
