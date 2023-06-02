package caffeine;

import com.github.benmanes.caffeine.cache.*;
import org.junit.platform.commons.util.StringUtils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wangyc
 * @date 2022/11/12
 */
public class CaffeineTest {


    public static void test() throws InterruptedException {
        String key = "host_error";
        String a = "10.1.2.222";
        Cache<String, String> cache = Caffeine.newBuilder().expireAfterWrite(3, TimeUnit.SECONDS)
                .removalListener((RemovalListener<String, String>) (id, str, removalCause) -> {
                    System.out.printf("缓存失效, key=%s, val=%s,原因：%s%n", id, str, removalCause);
                }).build();

        String a1 = cache.getIfPresent(key + a);
        System.out.println("是否存在:" + StringUtils.isBlank(a1));

        cache.put(key + a, "1");
        System.out.println(cache.getIfPresent(key + a));

        TimeUnit.SECONDS.sleep(2);
        System.out.println(cache.getIfPresent(key + a) == null);

        TimeUnit.SECONDS.sleep(2);
        System.out.println(cache.getIfPresent(key + a) == null);
//        Object o = "key";
//        // 手动删除指定key
//        cache.invalidate(o);
//
//        // 删除一批缓存
//        Iterable<Object> iterable = () -> Stream.of(o).iterator();
//        cache.invalidateAll(iterable);
//
//        // 删除所有缓存
//        cache.invalidateAll();
    }


    public static void main(String[] args) throws InterruptedException {
        test();
    }

    public static void test2() {
        //测试队列
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        AtomicInteger a = new AtomicInteger(0);
        for (int i = 0; i < 100; i++) {
            int incrementAndGet = a.incrementAndGet();
            System.out.println("初始化队列：" + incrementAndGet);
            queue.add(incrementAndGet);
        }

        for (int i = 0; i < 100; i++) {
            System.out.println("移除队列元素:" + i);
            queue.remove(i);
        }

        int c = 100;
        while (c-- > 0) {
            Integer poll = queue.poll();
            if (poll == null) {
                queue.add(a.incrementAndGet());
                continue;
            }
            System.out.println("队列poll元素为:" + poll);
        }
        while (true) {
            Integer poll = queue.poll();
            if (poll == null) {
                break;
            }
            System.out.println("poll的元素为："+poll);
        }

        System.out.println(queue.size());

    }
}
