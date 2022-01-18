package com.chige.collection.list;

import com.google.common.collect.*;

import java.util.*;

/**
 * @author wangyc
 * @date 2021/12/24
 */
public class GuavaListTest {

    /**
     *  Guava 列表集合使用
     */
    public static void test1() {
        ImmutableList<Integer> immutableList = ImmutableList.of(1,2);
        List<Integer> integers = Lists.newArrayList(1, 2, 3);
        Lists.newArrayListWithCapacity(11);
        HashSet<Integer> integers1 = Sets.newHashSet(integers);
        Map<Object, Object> objectObjectHashMap = Maps.newHashMap();
        System.out.println(immutableList);
    }

    public static void main(String[] args) {
        test1();
    }
}
