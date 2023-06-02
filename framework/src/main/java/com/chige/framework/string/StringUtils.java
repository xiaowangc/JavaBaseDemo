package com.chige.framework.string;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangyc
 * @date 2023/4/27
 */
public class StringUtils {

    public static void main(String[] args) {
        String str = new String(new byte[]{0x03});
        Map<String, String> map = new HashMap<>();
        map.put("a", "你不");
        map.put("b", new BigDecimal("3.00").toString());
        map.put("c", "你");
        map.put("d", "你不不");
        map.put("e", "你不步步");
        map.put("f", "你不不不不");
        String join = String.join(str, map.values());
        System.out.println(join);
    }

}
