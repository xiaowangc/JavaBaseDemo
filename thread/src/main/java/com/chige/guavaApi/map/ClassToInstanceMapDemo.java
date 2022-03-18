package com.chige.guavaApi.map;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.*;

/** ClassToInstanceMap - 实例Map
 * @author wangyc
 * @date 2022/3/19
 */
public class ClassToInstanceMapDemo {

    public static void classToInstanceMap() {
        ClassToInstanceMap<Object> classToInstanceMap = MutableClassToInstanceMap.create();
        User user = new User("小红");
        Car car = new Car("BWM");
        classToInstanceMap.putInstance(User.class, user);
        classToInstanceMap.putInstance(Car.class, car);

        User user1 = classToInstanceMap.getInstance(User.class);
        System.out.println(user == user1);


        // 使用普通的Map<Class, Object>
        Map<Class, Object> normalMap = new HashMap<>();
        User user2 = new User("小明");
        normalMap.put(User.class, user2);
        normalMap.put(Car.class, new Car("奔驰"));

        User user3 = (User)normalMap.get(User.class); //使用普通的map需要进行强制转换
        System.out.println(user2 == user3);

        ClassToInstanceMap<Map> instanceMap = MutableClassToInstanceMap.create();
        HashMap<String, Integer> map1 = new HashMap<>();
        TreeMap<String, Integer> treeMap = new TreeMap<>();
        instanceMap.putInstance(HashMap.class, map1);
        instanceMap.putInstance(Map.class, treeMap);
        List<String> list = new ArrayList<>();

//        instanceMap.putInstance(List.class, list); // 编译出错

    }

    public static void main(String[] args) {
        classToInstanceMap();
    }

    @ToString
    @AllArgsConstructor
    @Data
    public static class User {
        private String name;
    }

    @ToString
    @AllArgsConstructor
    @Data
    public static class Car {
        private String brand;
    }

}
