package com.chige.cache.caffeine.demo;


import org.springframework.util.StringUtils;

/**
 * @author wangyc
 * @date 2022/10/29
 */
public class ListMaxTest {


    public static void main(String[] args) {
        String a = "a,b,c,,";
        String b = "a,b,c";
        System.out.println(a.split(",").length);
        System.out.println(StringUtils.split(a,",").length);
    }
















}
