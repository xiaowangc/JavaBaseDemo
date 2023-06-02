package com.chige.cache.caffeine;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author wangyc
 * @date 2023/1/12
 */
public class UserImpl extends AbstractUser {


    public UserImpl() {
        super("默认-明");
    }

    public UserImpl(String name) {
        super(name);
        System.out.println("子类构造函数...");
    }

    public static void main(String[] args) {
        Map map = new HashMap();
        Object o = map.get(null);
        System.out.println(o);

    }
}
