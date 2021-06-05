# JavaBaseDemo
与Java基础、多线程、网络编程等相关

## 多线程相关
### 1.Future
Future相关的方法有get(),isDone(),isCancelled()
T get()方法: 获取回调方法call()的结果
boolean isDone()方法：可能由于正常结束，异常或者取消任务等操作会返回ture,其他情况为false
boolean isCanceled()方法：在任务执行完成前如果取消执行，则返回ture

