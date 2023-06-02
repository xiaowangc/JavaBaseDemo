package com.chige.spi.loader;

import java.util.ServiceLoader;

/**
 * @author wangyc
 * @date 2022/7/5
 */
public class MyServiceLoad {

    public static  <S> ServiceLoader<S> serviceLoader(final Class<S> clazz) {
        return ServiceLoader.load(clazz);
    }

}
