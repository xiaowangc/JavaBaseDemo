package com.chige.util;

import cn.hutool.core.map.MapUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangyc
 * @date 2021/9/28
 */
public class MapUtils {

    public static void test1() {
        Map<String,Object> map = new HashMap<>();
        map.put("a","1");
        map.put("b",1);
        Integer a = MapUtil.getInt(map, "a");
        Integer b = MapUtil.getInt(map, "b");
        System.out.println("a = " + a);
        System.out.println("b = " + b);
    }

    public static void main(String[] args) {
        MapUtils.test1();
    }
}
