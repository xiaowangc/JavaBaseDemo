package com.chige.test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 排序测试
 *
 * @author wangyc
 * @date 2023/6/9
 */
public class CompareTest {

    public static void main(String[] args) {
        List<Obj> list = new ArrayList<>();
        list.add(new Obj(1, "a"));
        list.add(new Obj(15, "aa"));
        list.add(new Obj(30, "c"));
        list.add(new Obj(20, "bd"));
        List<Obj> collect = list.stream().sorted(Comparator.comparingInt(Obj::getA).reversed()).collect(Collectors.toList());
        int maxDayRange = list.stream().mapToInt(Obj::getA).max().orElse(0);
        System.out.println(maxDayRange);
        collect.forEach(System.out::println);
    }

    private static class Obj {
        private Integer a;
        private String b;

        public Obj(Integer a, String b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public String toString() {
            return "Obj{" +
                    "a=" + a +
                    '}';
        }

        public Integer getA() {
            return a;
        }

        public void setA(Integer a) {
            this.a = a;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }
    }

}

