package com.chige.test;

import java.util.concurrent.atomic.AtomicLong;

public class SnowflakeIdGenerator {

    private static final long EPOCH = 1609459200000L; // 2021-01-01 00:00:00 UTC

    private static final int SEQUENCE_BITS = 12;
    private static final int WORKER_BITS = 10;
    private static final int MAX_SEQUENCE = (1 << SEQUENCE_BITS) - 1;

    private static final int WORKER_SHIFT = SEQUENCE_BITS;
    private static final int TIMESTAMP_SHIFT = SEQUENCE_BITS + WORKER_BITS;

    private static final AtomicLong workerIdCounter = new AtomicLong(0);

    private final long workerId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    private SnowflakeIdGenerator(Long workerId) {
        if (workerId != null && (workerId < 0 || workerId >= (1 << WORKER_BITS))) {
            throw new IllegalArgumentException("Worker ID must be between 0 and " + ((1 << WORKER_BITS) - 1));
        }
        if (workerId == null) {
            this.workerId = workerIdCounter.getAndIncrement() % (1 << WORKER_BITS);
        } else {
            this.workerId = workerId;
        }
    }

    public synchronized static SnowflakeIdGenerator getInstance(Long workerId) {
        return new SnowflakeIdGenerator(workerId);
    }

    public synchronized long nextId(String key) {
        long timestamp = System.currentTimeMillis();

        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == 0) {
                // Sequence overflow, wait until next millisecond.
                timestamp = waitNextMillis(timestamp);
            }
        } else {
            sequence = 0;
        }

        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate id.");
        }

        lastTimestamp = timestamp;

        return ((timestamp - EPOCH) << TIMESTAMP_SHIFT) |
                (workerId << WORKER_SHIFT) |
                sequence;
    }

    private long waitNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

    public static void main(String[] args) {
        // Example usage
        String key = null;
        SnowflakeIdGenerator generator = null;
        for (int i = 0; i < 10; i++) {
            if (i == 0 || i == 2 || i == 6) {
                key = "123";
                generator = SnowflakeIdGenerator.getInstance(Long.parseLong(key));
            } else if (i == 4 || i == 8) {
                key = "234";
                generator = SnowflakeIdGenerator.getInstance(Long.parseLong(key));
            } else {
                key = null;
                generator = SnowflakeIdGenerator.getInstance(null);
            }
            long id = generator.nextId(key);
            System.out.println("i:" + i + ",Generated ID: " + id);
        }

        for (int i = 0; i < 10; i++) {
            SnowflakeIdGenerator generator2 = SnowflakeIdGenerator.getInstance(123L);
            long id = generator2.nextId("123");
            System.out.println("i:" + i + ",Generated ID: " + id);
        }
    }
}
