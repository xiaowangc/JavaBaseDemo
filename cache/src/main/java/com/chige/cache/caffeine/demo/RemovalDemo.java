package com.chige.cache.caffeine.demo;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.stream.Stream;

/**
 * @author wangyc
 * @date 2022/8/10
 */
public class RemovalDemo {

    // 手动移除缓存
    public static void manualRemove() {
        Cache<Object, Object> cache = Caffeine.newBuilder().build();
        Object o = "key";
        // 手动删除指定key
        cache.invalidate(o);

        // 删除一批缓存
        Iterable<Object> iterable = () -> Stream.of(o).iterator();
        cache.invalidateAll(iterable);

        // 删除所有缓存
        cache.invalidateAll();
    }

    // 监听器
    public static void removal() {
        Caffeine.newBuilder().removalListener((key, o2, removalCause) -> {
            String format = String.format("key [%s] was removed,the cause is %s", key, removalCause);
            System.out.println(format);
        }).build();
    }

    public static void main(String[] args) {
        int startId = 141157616;
//        int endId = 590435021;
        int endId = 140157616;
        int size = 5000000;
//        int startId = 846409;
//        int endId = 22141917;
//        int size = 1;
        String space4 = "    ";
        String space8 = space4 + space4;
        String space10 = space8 + "  ";
        for (int i = startId; i <= endId; i+=size) {
            StringBuilder sb = new StringBuilder();
            sb.append(space4 + "{\n" + space8 + "name: follow\n");
            sb.append(space8 + "type: {\n");
            sb.append(space10 + "source: mysql\n");
            sb.append(space10 + "sink: client\n");
            sb.append(space8 + "}\n");
            sb.append(space8 + "host:10.1.2.200\n");
            sb.append(space8 + "port:4000\n");
            sb.append(space8 + "database:\"person_relation\"\n");
            sb.append(space8 + "table:\"person_relation\"\n");
            sb.append(space8 + "user:\"yanfa\"\n");
            sb.append(space8 + "password:\"yanfa!@#\"\n");

            sb.append(space8 + "sentence:\"SELECT CONCAT_WS('_',id_type,person_id) as VID,CONCAT_WS('_',link_id_type,link_person_id) as linkVID,create_time,upd_time,remark_name from person_relation where id BETWEEN ");
            sb.append(i + " and " + (i + size - 1) + " and relation_type = 1 and state = 1\"\n");
            sb.append(space8 + "fields: [create_time,upd_time,remark_name]\n");
            sb.append(space8 + "nebula.fields: [create_time,upd_time,remark_name]\n");
            sb.append(space8 + "source: {\n" + space10 + "field: VID\n" + space8 + "}\n");
            sb.append(space8 + "target: {\n" + space10 + "field: linkVID\n" + space8 + "}\n");
            sb.append(space8 + "batch: 5000\n");
            sb.append(space8 + "partition: 10\n");
            sb.append(space4 + "}\n");
            System.out.println(sb);
        }
    }
}
