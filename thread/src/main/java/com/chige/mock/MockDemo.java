package com.chige.mock;

import org.mockito.Answers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * @author wangyc
 * @date 2021/12/19
 */
public class MockDemo {
    /**
     *  使用验证方法
     */
    public static void test1() {
        List mockList = mock(LinkedList.class);

        when(mockList.get(0)).thenReturn("hello");
        when(mockList.get(1)).thenThrow(new IndexOutOfBoundsException("索引位置超出界限"));
        when(mockList.get(2)).then(Answers.CALLS_REAL_METHODS);
        System.out.println(mockList.get(0));
        System.out.println(mockList.get(1));
        System.out.println(mockList.get(2));


    }

    public static void main(String[] args) {
        test1();
    }

}
