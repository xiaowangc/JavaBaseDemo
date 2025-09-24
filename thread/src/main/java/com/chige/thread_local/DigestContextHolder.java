package com.chige.thread_local;

import java.util.UUID;

/**
 * @author wangyc
 * @date 2023/6/6
 */
public class DigestContextHolder {

    private static final ThreadLocal<DigestContext> digestContextHolder = new ThreadLocal<>();

    public static DigestContext init() {
        return init(null);
    }

    public static DigestContext init(String requestId) {
        String theRequestId = requestId == null ? UUID.randomUUID().toString() : requestId;
        DigestContext ctx = digestContextHolder.get();
        if (ctx != null) {
            digestContextHolder.remove();
        }
        ctx = new DigestContext(theRequestId);
        digestContextHolder.set(ctx);
        return ctx;
    }

    public static DigestContext getDigestContext() {
        return digestContextHolder.get();
    }

    public static void clear() {
        digestContextHolder.remove();
    }

}
