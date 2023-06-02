package com.chige.guavaApi.map;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 双向map
 *
 * @author wangyc
 * @date 2022/3/17
 */
public class BitMap {
    // jdk中通过value查找对应的key
    public List<String> findKey(Map<String, String> map, String value) {
        List<String> keyList = new ArrayList<>();
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            if (map.get(key).equals(value)) {
                keyList.add(key);
            }
        }
        return keyList;
    }

    // 使用guava来简化上面的做法
    public static void guavaBitMap() {
        // 1、创建双向map
        HashBiMap<String, Integer> biMap = HashBiMap.create();
        biMap.put("A", 1);
        biMap.put("B", 2);
        biMap.put("C", 3);

        System.out.println(">>> 获取指定key的值：" + biMap.get("A"));
        // 2、获取反向的BitMap
        BiMap<Integer, String> inverseBitMap = biMap.inverse();
        System.out.println(">>> 反向后的取值操作,获取指定value的key值：" + inverseBitMap.get(1));
        // 3、反转后的操作影响：对反转后的bitMap存值时会影响到原先的bitMap
        inverseBitMap.put(2,"BB");
        System.out.println(">>> 原先的bitMap为：" + biMap);

        // 4、value值不可重复，与key在map中的特点一样，都是唯一的
        biMap.put("D", 4);
//        biMap.put("E", 4);//当再往map中新增已存在的value时，会抛出异常
        biMap.forcePut("F", 3); //强制把key映射到已有的value上面,此时key="F"的会替换掉key="C"的位置
        biMap.forcePut("A",3); //当key和value都已存在时，原先的key会被覆盖或者去除
        System.out.println(">>> 覆盖操作：" + biMap);

        // 5、获取所有的key
        Set<String> strings = biMap.keySet();
        Set<Integer> values = biMap.values();
        System.out.println(">>> keys=" + strings + ";\n values=" + values);

        Set<Integer> integers = inverseBitMap.keySet();
        Set<String> values1 = inverseBitMap.values();

    }

    public static void main(String[] args) {
        guavaBitMap();
    }
}
