package com.chige.spi.service.impl;

import com.chige.spi.service.MyService;

/**
 * @author wangyc
 * @date 2022/7/5
 */
public class MyServiceAImpl implements MyService {
    @Override
    public void print(String s) {
        System.out.println("A class load." + s);
    }
}
