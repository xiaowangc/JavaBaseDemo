package com.chige.collection.queue.demo;

/**
 * @author wangyc
 * @date 2022/6/25
 */
public interface LifeCycle {

 void init(String threadName);
 void shutDown();
}
