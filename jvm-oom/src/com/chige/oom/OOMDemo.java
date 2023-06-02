package com.chige.oom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangyc
 * @date 2023/3/14
 */
public class OOMDemo {

    private static List<String> s = new ArrayList<>();

    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        while (true) {
            s.add("尼玛oom");
        }

    }
}
