package com.chige.thread_local;

import java.util.UUID;

/**
 * @author wangyc
 * @date 2023/6/6
 */
public class DigestContext {

    private String requestId;
    private long startTime;

    public DigestContext() {
        this(UUID.randomUUID().toString());
    }

    public DigestContext(String requestId) {
        this.requestId = requestId;
        this.startTime = System.currentTimeMillis();
        System.out.println("初始化DigestContext，当前线程名：" + Thread.currentThread().getName() + "：" + this.requestId);
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "DigestContext-threadName {" +
                "threadName='" + Thread.currentThread().getName() + '\'' +
                "requestId='" + requestId + '\'' +
                ", startTime=" + startTime +
                '}';
    }
}
