package com.chige.cache.caffeine;

/**
 * @author wangyc
 * @date 2023/1/12
 */
public abstract class AbstractUser {

    public AbstractUser(String name) {
        System.out.println("抽象类构造函数带参数: " + name);
    }

}
