package com.chige.objects;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @author wangyc
 * @date 2021/12/24
 */
public class GuavaObjects {

    /**
     *  Guava Objects工具类的使用
     */
    public static void test1() {
        String a = "a";
        String b = "b";
        boolean equal = Objects.equal("a", "b");
        int i1 = Arrays.hashCode(a.getBytes(StandardCharsets.UTF_8));
        int i = Objects.hashCode(a, b);
        System.out.println(equal);
        System.out.println(i1);
        System.out.println(i);
        Person p = new Person();

    }

    public static void main(String[] args) {
        test1();
    }

    public static class Person {
        private String personId;
        private Integer idType;


        public int compareTo(Person that) {
            return ComparisonChain.start().compare(that.personId, this.personId).compare(that.idType, this.idType).result();
        }

    }


}
