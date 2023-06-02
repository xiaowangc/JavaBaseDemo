package com.chige.cache.caffeine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wangyc
 * @date 2022/8/10
 */
@SpringBootApplication
public class CaffeineAppStart {
    private static final Logger log = LoggerFactory.getLogger(CaffeineAppStart.class);
    public static void main(String[] args) {
        SpringApplication.run(CaffeineAppStart.class, args);
        log.info("Caffeine程序启动成功");
    }
}
