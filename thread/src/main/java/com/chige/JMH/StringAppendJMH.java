package com.chige.JMH;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/** 不同字符串拼接方法性能压测
 * @author wangyc
 * @date 2022/3/8
 */

@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.SECONDS)
@Threads(Threads.MAX)
public class StringAppendJMH {

    private final int SIZE = 200;
    @Benchmark
    @BenchmarkMode({Mode.Throughput, Mode.SingleShotTime})
    public void append() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < SIZE; i++) {
            sb.append("a");
            sb.append("_");
            sb.append(i);
            sb.append("_");
        }
        String result = sb.toString();
    }
    @Benchmark
    @BenchmarkMode({Mode.Throughput, Mode.SingleShotTime})
    public void plus() {
        String a = "start:";
        for (int i = 0; i < SIZE; i++) {
            a = a + "a_" + i + "_";
        }
    }
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .exclude("Prf")
                .include(StringAppendJMH.class.getSimpleName())
                .warmupIterations(2)
                .measurementIterations(2)
                .forks(1)
                .output("/Users/yongchiwang/temp/StringAppendJMH4.log")
                .build();
        new Runner(options).run();
    }
}

/**
 *  最终结果报告：
 *  比较一：循环次数为SIZE=1000时
 *  Benchmark                    Mode  Cnt       Score      Error  Units
 * JMH.StringAppendJMH.append  thrpt    9  263358.152 ± 4093.204  ops/s
 * JMH.StringAppendJMH.plus    thrpt    9    7277.405 ±  235.287  ops/s
 * JMH.StringAppendJMH.append     ss    9       0.001 ±    0.001   s/op
 * JMH.StringAppendJMH.plus       ss    9       0.023 ±    0.027   s/op
 *
 * 比较二：循环次数为SIZE=100时
 * Benchmark                    Mode  Cnt        Score   Error  Units
 * JMH.StringAppendJMH.append  thrpt    2  1319849.506          ops/s
 * JMH.StringAppendJMH.plus    thrpt    2   377372.818          ops/s
 * JMH.StringAppendJMH.append     ss    2       ≈ 10⁻⁴           s/op
 * JMH.StringAppendJMH.plus       ss    2        0.002           s/op
 *
 * 结论：字符串拼接性能比较中，使用StringBuilder的append拼接方法要远比使用字符串"+"进行拼接的性能高。
 */
