package com.chige.io.buffer;

import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * @author wangyc
 * @date 2022/4/12
 */
public class ByteBufferDemo extends BufferDemo {

    private Buffer byteBuffer;

    public ByteBufferDemo(int capacity) {
        byteBuffer = this.allocate(capacity);
    }

    /**
     * 创建Buffer实例，初始化为写模式
     *
     * @param capacity
     */
    @Override
    Buffer allocate(int capacity) {
        return ByteBuffer.allocate(capacity);
    }

    /**
     * 从Buffer中读取数据
     */
    @Override
    int get() {
        return 0;
    }

    /**
     * 往Buffer中写入数据
     */
    @Override
    void put() {

    }

    /**
     * 写模式 -> 读模式
     */
    @Override
    void flip() {

    }

    /**
     * 清空缓冲区，读模式 -> 写模式
     */
    @Override
    void clear() {

    }

    /**
     * 保存当前的position位置
     */
    @Override
    void mark() {

    }
}
