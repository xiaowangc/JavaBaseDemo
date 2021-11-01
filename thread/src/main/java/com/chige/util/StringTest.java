package com.chige.util;

/**
 * @author wangyc
 * @date 2021/9/11
 */
public class StringTest {

    public static void main(String[] args) {
        String arg = "0,12_,_23_";
        arg = arg.replaceAll("_","");
        System.out.println("arg=" + arg);
        String arg1 = "0,23";
        arg1 = arg1.replaceAll("_","");
        System.out.println("arg1=" + arg1);

    }
}
