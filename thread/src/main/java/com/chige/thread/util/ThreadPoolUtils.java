package com.chige.thread.util;

import java.util.concurrent.*;

public class ThreadPoolUtils {
    private ThreadPoolUtils() {}
    private static BlockingDeque<Runnable> workQueue = new LinkedBlockingDeque<>(1000);

    private static ThreadPoolExecutor THREAD_POLL = new ThreadPoolExecutor(
            15, 100, 600000, TimeUnit.MILLISECONDS, workQueue);
    private static final CompletionService completionService = new ExecutorCompletionService(THREAD_POLL);
    public static void execute(Runnable task) {
        THREAD_POLL.execute(task);
    }
    public static <T> Future<T> execute(Callable<T> callable){
        return THREAD_POLL.submit(callable);
    }
    public static <T> CompletionService execute2(Callable<T> callable) {
        completionService.submit(callable);
        return completionService;
    }
    public static CompletionService getCompletionService() {
        return completionService;
    }

}
