package com.chige.util;

import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wangyc
 * @date 2021/9/11
 */
public class StringTest {

    public static void testJoin() {
        List<Integer> idList = Arrays.asList(1,3,4);
        String join = StringUtils.join(idList, ",");
        System.out.println("join = " + join);
        List<String> idStrList = Arrays.asList("1","a","f");
        String join2 = StringUtils.join(idStrList, ",");
        System.out.println("join2 = " + join2);
        System.out.println("join3 = " + String.join(",", idStrList));
    }
    public static void byteArrayToString() {
        byte[] bytes = {52,57,52};
        String s = StringUtils.toEncodedString(bytes, Charset.defaultCharset());
        System.out.println(s);

    }

    public static void main(String[] args) {
//        String arg = "0,12_,_23_";
//        arg = arg.replaceAll("_","");
//        System.out.println("arg=" + arg);
//        String arg1 = "0,23";
//        arg1 = arg1.replaceAll("_","");
//        System.out.println("arg1=" + arg1);
//        byteArrayToString();
        Integer totalNum = 19;
        Integer pageSize = 20;
        System.out.println(countTotalPage(totalNum, pageSize));
    }

    /**
     * 计算总页数
     * @param totalNum
     * @param pageSize
     * @return
     */
    public static Integer countTotalPage(Integer totalNum, Integer pageSize) {
        if (totalNum == 0) {
            return 0;
        }
        int page = totalNum / pageSize;
        if (totalNum % pageSize != 0) {
            return page + 1;
        }
        return page;
    }
}
