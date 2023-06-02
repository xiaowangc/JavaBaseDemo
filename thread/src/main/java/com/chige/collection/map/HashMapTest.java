package com.chige.collection.map;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wangyc
 * @date 2021/9/13
 */
public class HashMapTest {

    public static void merge() {
        Map<String, Integer> map = new HashMap();
        map.merge("a", 1, Integer::max);
        map.merge("a", 4, Integer::max);
        map.merge("a", 3, Integer::max);
        System.out.println(map.get("a"));
    }

    public static void create1() {
        AtomicInteger atomicInteger = new AtomicInteger();
        int j = 9999;
        StringBuilder sb = new StringBuilder("INSERT INTO sms_record (user_id,sms_type_id,STATUS,mobile,content,global_id,channel_id,received_time,sms_count,task_id,param_a,param_b,type_code,tpl_id,area_code,channel_type,biz_type) VALUES \n");
        for (int i = 1; i <= 1000; i++) {
            sb.append("(");
            sb.append("1,103,1,");

            sb.append("'1367272" + (j - i) + "'");
            sb.append(",'rApQqt8cw7mq7XG5KXaEGngWTmLvoQ1j3fgpxChXUgFzJ','16563211561947f0000010000000002',521,now(),1,'16563211558887f0000010000000001','114','','marketing',0,86,NULL,1),\n");
        }
        System.out.println(sb.toString());
    }

    public static void create2() {
        AtomicInteger atomicInteger = new AtomicInteger();
        int j = 9999;
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 1000; i++) {
            sb.append("\"");
            sb.append("1367123" + (j - i));
            sb.append("\",");
        }
        System.out.println(sb.toString());
    }

    public static void main(String[] args) {
        create2();
    }

}
