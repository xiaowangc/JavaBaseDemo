package com.chige.JMH;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author wangyc
 * @date 2022/3/4
 */
@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.SECONDS)
@Threads(Threads.MAX)
public class LinkedListForeachJMH {
    //以测试LinkedList 通过index 方式迭代和foreach 方式迭代的性能差距为例子
    final int SIZE = 100;

    private List<String> linkedList = new LinkedList<>();


    @Setup
    public void setUp() {
        for (int i = 0; i < SIZE; i++) {
            linkedList.add(String.valueOf(i));
        }
    }

    // 压测Index方法遍历
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void benchIndex() {
        for (int i = 0; i < linkedList.size(); i++) {
            linkedList.get(i);
            System.out.println("");
        }
    }
    // 压测foreach方法遍历
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void benchForeach() {
        for (String s : linkedList) {
            System.out.println("");
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(LinkedListForeachJMH.class.getSimpleName())
                .warmupIterations(3)
                .measurementIterations(3)
                .forks(1)
                .output("/Users/yongchiwang/temp/Benchmark3.log")
                .build();
        new Runner(opt).run();
    }

}
