package com.chige.thread.synchronizeds;

/** 自定义显示锁
 * 由于synchronized同步块存在缺陷：1、无法控制阻塞时长；2、阻塞不可被中断
 * 基于此背景，自定义一个显示锁，在基本实现synchronized相同功能的同时，自定义锁不仅可控制阻塞时长，还可中断阻塞
 * @author wangyc
 * @date 2022/7/16
 */
public class MyBooleanLock {
}
