package com.chige.collectors;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangyc
 * @date 2021/6/28 13:00
 */
public class CollectorsTest {

    public void test1() {
        // Collectors.toList方法
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> collect = integers.stream().collect(Collectors.toList());
        // Collectors.toCollect()方法

        // Collectors.toConcurrentMap()方法
        // Collectors.toMap()方法
        // Collectors.toSet()方法

    }

    public static void main(String[] args) {
        String s1 = "";
        String s2 = null;
        String s3 = " ";
        System.out.println(StringUtils.isBlank(s1));
        System.out.println(StringUtils.isEmpty(s1));
        System.out.println(StringUtils.isEmpty(s2));
        System.out.println(StringUtils.isBlank(s3));
        System.out.println(StringUtils.isEmpty(s3));

    }
}
