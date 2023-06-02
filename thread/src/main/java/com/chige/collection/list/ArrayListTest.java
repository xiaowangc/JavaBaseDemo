package com.chige.collection.list;

import cn.hutool.core.collection.CollectionUtil;
import shaded.sf.org.apache.commons.collections4.CollectionUtils;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author wangyc
 * @date 2021/9/13
 */
public class ArrayListTest {
    // 模拟调用addAll()方法抛出UnsupportedOperationException异常
    public static void testAddAllThrowUnsupportedOperationException() {
        List<String> aList = Collections.emptyList();
        List<String> bList = Arrays.asList("a","b");
        if (CollectionUtil.isEmpty(aList))
        aList = new ArrayList<>(bList);
        System.out.println(aList);
    }

    public static void testDiff() {
        List<Integer> a = new ArrayList<>();
        a.add(1);
        a.add(2);
        a.add(3);
        a.add(4);
        List<Integer> b = new ArrayList<>();
        b.add(2);
        b.add(3);
        b.add(5);
        b.add(6);
        Collection<Integer> integers = CollectionUtils.removeAll(a, b);
        for (Integer integer : integers) {
            System.out.println(integer);
        }

    }

    public static void main(String[] args) {
        testDiff();;
    }
}
