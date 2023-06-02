package com.chige.io.buffer;


import java.nio.Buffer;

/**
 * @author wangyc
 * @date 2022/4/12
 */
public abstract class BufferDemo {

    int capacity;

    /**
     * 创建Buffer实例，初始化为写模式
     */
    abstract Buffer allocate(int capacity);

    /**
     * 从Buffer中读取数据
     */
    abstract int get();

    /**
     * 往Buffer中写入数据
     */
    abstract void put();

    /**
     * 写模式 -> 读模式
     */
    abstract void flip();

    /**
     * 清空缓冲区，读模式 -> 写模式
     */
    abstract void clear();

    /**
     * 保存当前的position位置
     */
    abstract void mark();


}
