## 注解的介绍

### @Benchmark
方法级注解，表示该方法是需要进行 benchmark 的对象，用法和 JUnit 的 @Test 类似

### @BenchmarkMode 微基准测试类型
JMH提供了几种类型以供测试：
`Mode.Throughput` 每段时间执行的次数，一般是秒
`Mode.AverageTime` 平均时间，每次操作的平均耗时
`Mode.SampleTime` 在测试中，随机进行采样执行的时间
`Mode.SingleShotTime` 在每次执行中计算耗时
`Mode.All` 所有模式
#### 使用：
@BenchmarkMode({Mode.Throughput, Mode.SingleShotTime})
表示在基准测试是既要测试每秒执行的次数，又要测试每次执行的耗时
该注解是方法级别的，也可以注释到类级别上。

