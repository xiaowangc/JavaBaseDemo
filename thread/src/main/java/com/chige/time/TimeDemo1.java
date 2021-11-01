package com.chige.time;

import cn.hutool.core.date.format.FastDateFormat;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/** java.time.* 时间类的使用
 * @author wangyc
 * @date 2021/6/8 15:44
 *  LocalTime
 *  LocalDate
 *  LocalDateTime
 */
public class TimeDemo1 {

    private static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public void localTimeDemo() {
        // 获取当前时间(不包含日期的)
        LocalTime localTime = LocalTime.now();
        System.out.println("time is " + localTime);
    }
    public void localDateDemo() {
        // 获取当前日期，不包含时间
        LocalDate localDate = LocalDate.now();
        System.out.println("date is " + localDate);
    }
    public void localDateTimeDemo() {
        // 获取当前日期时间
        LocalDateTime dateTime = LocalDateTime.now();
        System.out.println("date time is " + dateTime);
        // 获取当前时间的时间戳/毫秒数
        long milli = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long second = dateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
        System.out.println("get the time unix: " + milli / 1000);
        System.out.println("get the time unix 2: " + second);
        System.out.println("get the millisecond: " + milli);
        System.out.println("now the time unix is " + System.currentTimeMillis());

        // 获取年、月、日 与 星期几
        int year = dateTime.getYear();
        System.out.println("get the year of way one: " + year);
        year = dateTime.get(ChronoField.YEAR);
        System.out.println("get the year of way two: " + year);

        System.out.println();
        // 获取当前月份
        int monthValue = dateTime.getMonthValue();
        System.out.println("get the month of way one: " + monthValue);
        int value = dateTime.getMonth().getValue();
        System.out.println("get the month of way two: " + value);
        int month3 = dateTime.get(ChronoField.MONTH_OF_YEAR);
        System.out.println("get the month of way three: " + month3);

        // 获取今年第几天
        int dayOfYear = dateTime.getDayOfYear();
        System.out.println("get the day of year: " + dayOfYear);

        // 获取本月第几号
        int dayOfMonth = dateTime.getDayOfMonth();
        System.out.println("get the day of month: " + dayOfMonth);

        // 获取星期几
        int dayOfWeek = dateTime.getDayOfWeek().getValue();
        System.out.println("get the day of week: " + dayOfWeek);

    }

    /**
     *  LocalDateTime 、字符串互相转换
     */
    public void localDateTime_string() {
        //获取当前时间
        LocalDateTime localDateTime = LocalDateTime.now();
        // 日期转字符串 调用实例方法format
        String format = localDateTime.format(DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS));
        System.out.println("date transfer string: " + format);
        // 字符串转日期 调用静态方法parse
        String strDate = "2021-06-08 17:01:00";
        LocalDateTime dateTime = LocalDateTime.parse(strDate, DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS));
        System.out.println("string date transfer to date: " + dateTime);
    }

    /**
     *  Date 与 LocalDateTime相互转换
     */
    public void date_localDateTime() {
        // LocalDateTime 转换成Date：之间的转换主要是通过Instant对象和分区对象ZoneDateTime作为中间者实现的
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        Instant instant = zonedDateTime.toInstant();
        Date date = Date.from(instant);
        System.out.println("localDateTime transfer to date: " + date);
        System.out.println("Format the date: " + FastDateFormat.getInstance(YYYY_MM_DD_HH_MM_SS).format(date));

        // Date 转成 LocalDateTime
        Date date1 = new Date();
        Instant instant1 = date1.toInstant();
        ZonedDateTime zonedDateTime1 = instant1.atZone(ZoneId.systemDefault());

        LocalDateTime localDateTime1 = LocalDateTime.ofInstant(instant1, ZoneId.systemDefault());
        LocalDateTime localDateTime2 = zonedDateTime1.toLocalDateTime();
        System.out.println("date transfer to localDateTime way one: " + localDateTime1);
        System.out.println("date transfer to localDateTime way two: " + localDateTime2);

    }

    /***
     *  下个月1号
     */
    public void nextMonth_DayOne() {
        LocalDateTime localDateTime = LocalDateTime.now()
                                                   .plusMonths(1)
                                                   .withDayOfMonth(1)
                                                   .withHour(0)
                                                   .withMinute(0).withSecond(0)
                                                   .withNano(0);
        long nextMonth = localDateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
        long now = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond();
        long expire = 7 * 3600 * 24;
        if (now + 7 * 3600 * 24 > nextMonth) {
            expire = nextMonth - now;
        }
        long between = ChronoUnit.SECONDS.between(LocalDateTime.now(), LocalDateTime.now()
                                                                                    .plusMonths(1)
                                                                                    .withDayOfMonth(1)
                                                                                    .withHour(0)
                                                                                    .withMinute(0).withSecond(0)
                                                                                    .withNano(0));
        System.out.println("expire is :" +  expire);
        System.out.println("between is :" + between);
        System.out.println("seven day is " + 7 * 3600 *24);


        System.out.println(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

    }

    public static void main(String[] args) {
        TimeDemo1 demo1 = new TimeDemo1();
//        demo1.localTimeDemo();
//        demo1.localDateDemo();
        demo1.nextMonth_DayOne();
    }


}
