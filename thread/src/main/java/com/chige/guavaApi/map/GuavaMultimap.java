package com.chige.guavaApi.map;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;

import java.util.*;

/** 多值Map
 * @author wangyc
 * @date 2022/3/18
 */
public class GuavaMultimap {
    // 背景：java中的Map维护的是键值一对一的关系，如果要将一个键映射到多个值上，那么就只能把值的内容设为集合形式
    public void jdkMap() {
        Map<String, List<String>> jdkMap = new HashMap<>();
        List<String> value1 = Arrays.asList("a","b","c");
        List<String> value2 = Arrays.asList("1","2","3");

        jdkMap.put("word", value1);
        jdkMap.put("number", value2);
    }

    // guava中的multiMap定义了一个key可以映射多个不同的value，而无需像jdk的map那样内嵌集合
    public static void guavaMultimap() {
        // 1、创建普通multiMap
        Multimap<String, Integer> multimap = ArrayListMultimap.create();
        // 创建指定类型的multimap, 同理，可以创建HashMultimap、TreeMultimap等类型的Multimap。
        ArrayListMultimap<String, Integer> listMultimap = ArrayListMultimap.create();
        HashMultimap<String, String> hashMultimap = HashMultimap.create();
        TreeMultimap<Comparable, Comparable> treeMultimap = TreeMultimap.create();

        multimap.put("day", 1);
        multimap.put("day", 2);
        multimap.put("day", 3);
        multimap.put("month",1);
        listMultimap.put("day",1);
        listMultimap.put("day",2);
        listMultimap.put("day",3);
        listMultimap.put("month",1);
        hashMultimap.put("word","a");
        hashMultimap.put("word","b");
        hashMultimap.put("word","c");
        // 2、普通multiMap取值返回的是一个collection
        Collection<Integer> dayList = multimap.get("day");
        Collection<Integer> monthList = multimap.get("month");
        System.out.println(">>> day=" + dayList + "; month=" + monthList);
        //指定类型的multimap取值返回的是一个list类型的集合
        List<Integer> dayList2 = listMultimap.get("day");
        List<Integer> monthList2 = listMultimap.get("month");
        Set<String> stringSet = hashMultimap.get("word");

        // 3、对get获取到的集合进行操作,原始的multimap对象也会跟着变
        dayList2.add(4);
        //4、转为map, 使用asMap()将multiMap转换为Map<String, Collection>
        Map<String, Collection<Integer>> asMap = multimap.asMap();
        System.out.println(">>> 转换后的map为：" + asMap);
        asMap.get("day").add(5); //对转换后的map进行操作，也会作用到原始的multimap
        System.out.println(">>> multimap跟着变化：" + multimap);

        // 5、数量问题
        System.out.println("multiMap的size=" + multimap.size()); //所有key与单个value的映射
        System.out.println("multiMap的entries size=" + multimap.entries().size()); //同上
        for (Map.Entry<String, Integer> entry : multimap.entries()) {
            System.out.println(">>> entry.key=" + entry.getKey() + " and entry.value=" + entry.getValue());
        }
    }

    public static void main(String[] args) {
        guavaMultimap();
    }
}
