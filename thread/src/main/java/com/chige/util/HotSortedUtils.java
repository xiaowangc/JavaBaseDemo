package com.chige.util;

import cn.hutool.core.util.RandomUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 热度排序算法
 *
 * @author wangyc
 * @date 2021/6/2 20:08
 */
public class HotSortedUtils {

    /** 用来控制指定笔记的热度排序的，可以直接影响笔记的排序 */
    public static final float k = 1.0f;
    /** 重力加速度，用来控制热度与发布时间的占比 */
    public static final float gravity = 1.8f;

    /**
     * 基于Hacker News的排序算法改造
     *
     * @param p 热度值
     * @param k k默认值为1
     * @param t 时间差 单位：小时
     * @param g 重来加速度，默认值1.8
     * @return 返回计算后的热度值Score
     */
    public static double hottestSort(final int p, final float k, final float t, final float g) {
        return BigDecimal.valueOf(((p - 1) * k) / Math.pow(t + 2, g))
                .setScale(7, RoundingMode.HALF_UP)
                .doubleValue();
    }

    public static double hottestSort(final float p, final float k, final float t, final float g) {
        return ((p - 1) * k) / Math.pow(t + 2, g);
    }

    /** TODO
     *  是否可以设计一个以更新时间为热度的排序方法
     */
    public static double newestSort(final float p, final float k, final float t, final float g) {
        return  k / (Math.pow(t + 2, g) - p);
    }

    public static float getDateHoursDis(Date date) {
        LocalDateTime localDateTime = LocalDateTime.now();
        long currentSecond = localDateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
        long dateSecond = date.toInstant().atZone(ZoneId.systemDefault()).toEpochSecond();
        System.out.println("now is " + currentSecond +", date is " + dateSecond);
        long dis = currentSecond - dateSecond;
        float hour = dis / 3600f;
        return hour;
    }

    public static void main(String[] args) {
        float k = 1;
        float g = 1.3f;
        List<Integer> pList = new ArrayList<>();
        List<Integer> timeList = new ArrayList<>();
        int count = 30;
        while(count-- > 0) {
            pList.add(RandomUtil.randomInt(0,10));
            timeList.add(RandomUtil.randomInt(1000,5000));
        }
        for (int i = 0; i < timeList.size(); i++) {
            Integer time = timeList.get(i) / 60;
            Integer p = pList.get(i);
            double newScore = newestSort(p, k, time, g);
            double hottestSort = hottestSort(p, k, time, g);
            System.out.println("likeNum = "+ p +", time = "+ time +", hotScore = " + hottestSort + ", newSocore = " + newScore);
        }
    }
}
