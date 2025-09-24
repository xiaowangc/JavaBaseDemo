package com.chige.lock.AQS;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @Author wangyc
 * @Description AQS同步器：提供状态管理和线程队列管理，内部使用一个双向队列（CLH队列）来管理线程的阻塞与唤醒。
 * @Date 2025/1/11 16:34
 */
public class AQSTest extends AbstractQueuedSynchronizer {
}
