package com.chige.javabase;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/** 对象的克隆
 * @author wangyc
 * @date 2022/4/24
 */
public class ObjectCloneTest {

    public static void print(Object[] objects) {
        System.out.println("开始打印：====");
        for (Object object : objects) {
            System.out.println(object);
        }
        System.out.println("结束打印+======");
    }

    public static void main(String[] args) {
        Person person = new Person(1,"王1", Arrays.asList(1,2,3));
        Object[] objects = new Object[3];
        objects[0] = person.getAge();
        objects[1] = person.getName();
        objects[2] = person.getList();
        Object[] clone = new Object[objects.length];
        for (int i = 0; i < objects.length; i++) {
            Object object = objects[i];
            clone[i] = object;
        }
        print(clone);

        int i = person.getAge() + 3;
        person.setName("王二");
        person.getList().remove(1);

        print(clone);
        print(objects);
    }



    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Person {
        private Integer age;
        private String name;
        private List<Integer> list;
    }
}


