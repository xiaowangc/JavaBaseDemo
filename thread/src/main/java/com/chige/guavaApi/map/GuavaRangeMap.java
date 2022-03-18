package com.chige.guavaApi.map;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;

/** guava - RangeMap
 * @author wangyc
 * @date 2022/3/19
 */
public class GuavaRangeMap {
    // 实现根据分数对考试成绩进行分类
    public static String getRank(int score){
        if (0<=score && score<60)
            return "fail";
        else if (60<=score && score<=90)
            return "satisfactory";
        else if (90<score && score<=100)
            return "excellent";
        return null;
    }

    // 使用guava - rangeMap简化上面的操作
    public static void guavaRangeMap() {
        RangeMap<Integer, String> rangeMap = TreeRangeMap.create();
        rangeMap.put(Range.closedOpen(0,60), "fail");
        rangeMap.put(Range.closed(60,90), "satisfactory");
        rangeMap.put(Range.openClosed(90, 100), "excellent");
        System.out.println(rangeMap.get(1));
        System.out.println(rangeMap.get(59));
        System.out.println(rangeMap.get(70));
        System.out.println(rangeMap.get(93));
        // 移除一段空间
        rangeMap.remove(Range.closed(70,80));
        System.out.println(rangeMap.get(70));
        System.out.println(rangeMap.get(77));
    }

    public static void main(String[] args) {
        guavaRangeMap();
    }
}
