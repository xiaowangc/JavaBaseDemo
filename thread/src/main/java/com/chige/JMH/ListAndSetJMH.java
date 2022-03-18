package com.chige.JMH;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.util.ListStatistics;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/** 对list和set两个集合的各种相同操作进行压测对比
 * @author wangyc
 * @date 2022/3/4
 */
@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.SECONDS)
@Threads(Threads.MAX)
public class ListAndSetJMH {

    private final int SIZE = 10000;
    private List<Integer> list = new ArrayList<>(1000);
    private Set<Integer> set = new HashSet<>(1024);

    @Setup
    public void setUp() {
        for (int i = 0; i < SIZE; i++) {
            list.add(i);
            set.add(i);
        }
    }
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Measurement(iterations = 3, time = 10)
    @Warmup(iterations = 3, time = 10, timeUnit = TimeUnit.SECONDS)
    public void listContainBench() {
        for (int j = 0; j < SIZE; j++) {
            if (list.contains(j)) {
                System.out.println("");
            }
        }

    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void setContainBench() {
        for (int j = 0; j < SIZE; j++) {
            if (set.contains(j)) {
                System.out.println("");
            }
        }
    }


    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(ListAndSetJMH.class.getSimpleName())
                .warmupIterations(3)
                .measurementIterations(3)
                .forks(1)
                .output("/Users/yongchiwang/temp/listAndSetJMH2.log")
                .build();
        new Runner(opt).run();
    }
}
