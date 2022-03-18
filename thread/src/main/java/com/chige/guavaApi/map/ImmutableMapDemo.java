package com.chige.guavaApi.map;

import com.google.common.collect.*;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/** guava - 不可变Map对象
 * 特点：（1）可以链式编程的Map；
 *      （2）不可变的map： 线程安全（在并发程序中，使用Immutable既保证线程安全性，也大大增强了并发时的效率（跟并发锁方式相比）
 *                       可以当作一个常量来对待，并且这个对象在以后也不会被改变；
 *                       将一个对象复制一份成immutable的，是一个防御性编程技术
 * @author wangyc
 * @date 2022/3/19
 */
public class ImmutableMapDemo {

    public static void guavaImmutableMap() {
        //创建ImmutableMap对象
        ImmutableMap<String, Integer> immutableMap = ImmutableMap.<String, Integer>builder()
                .put("day", 1)
                .put("word", 2)
                .put("month", 3)
                .build();
        Integer day = immutableMap.get("day");
        // ImmutableMap.of(); of提供入参最多5对，超过5对需要使用builder方法。

        // 不可变的bitMap
        ImmutableBiMap<String, String> immutableBiMap = ImmutableBiMap.<String, String>builder()
                .put("day", "one")
                .put("month", "two")
                .put("word", "a")
                .build();
        ImmutableBiMap<String, String> inverse = immutableBiMap.inverse();

        // 不可变的tableMap
        ImmutableTable<String, String, Integer> immutableTable = ImmutableTable.<String, String, Integer>builder()
                .put("name1", "Jan", 1)
                .put("name2", "Joken", 2)
                .build();
        System.out.println(immutableTable.row("name1"));

        // 不可变的多值 multimap
        ImmutableMultimap<String, Integer> immutableMultimap = ImmutableMultimap.<String, Integer>builder()
                .put("name", 1)
                .put("name", 2)
                .put("word", 3)
                .build();
        System.out.println(immutableMultimap.get("name"));

        // 不可变的范围rangeMap
        ImmutableRangeMap<Integer, String> immutableRangeMap = ImmutableRangeMap.<Integer, String>builder()
                .put(Range.closedOpen(0, 60), "fail")
                .put(Range.closed(60, 80), "satisfactory")
                .put(Range.openClosed(90, 100), "excellent")
                .build();
        System.out.println(immutableRangeMap.get(1));

        ImmutableClassToInstanceMap<Map> mapImmutableClassToInstanceMap = ImmutableClassToInstanceMap.<Map>builder()
                .put(Map.class, new HashMap())
                .put(TreeMap.class, new TreeMap())
                .build();
        System.out.println(mapImmutableClassToInstanceMap.getInstance(Map.class));

        // 不可变的原因呢： 当最后调用build()方法创建完ImmutableMap对象之后，就不能再往该对象put元素了
    }

    public static void main(String[] args) {
        guavaImmutableMap();

    }
}
