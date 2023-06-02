package com.chige.spi;


import com.chige.spi.loader.MyServiceLoad;
import com.chige.spi.service.MyService;

import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

/**
 * @author wangyc
 * @date 2022/7/5
 */
public class MyServiceTest {


    public static void main(String[] args) {
        ServiceLoader<MyService> myServices =
                MyServiceLoad.serviceLoader(MyService.class);
        StreamSupport.stream(myServices.spliterator(), false).forEach(s -> s.print("Hello word"));
    }



}
