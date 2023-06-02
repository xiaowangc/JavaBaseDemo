package com.chige.util;


import java.util.ArrayList;
import java.util.List;

/** 列表拆分工具
 * @author wangyc
 * @date 2022/4/12
 */
public class ListSplitUtil {

    public static void main(String[] args) {
        List<Integer> list = createList(101);
        final int MAX = 100;
        int listSize = list.size();
        int startIndex = 0;
        int endIndex = MAX;
        while (listSize >= MAX) {
            List<Integer> childList = list.subList(startIndex, endIndex);
            startIndex = endIndex;
            endIndex += MAX;
            listSize -= MAX;
            System.out.println("childList=" + childList);

        }
        if (listSize == 0) {
            return;
        }
        if (endIndex > listSize) {
            endIndex = startIndex + listSize;
        }
        List<Integer> childList = list.subList(startIndex, endIndex);
        System.out.println("childList=" + childList);

    }
    private static List<Integer> createList(int capacity) {
        List<Integer> list = new ArrayList<>(capacity);
        int num = 0;
        while (capacity-- > 0) {
            list.add(num++);
        }
        return list;
    }
}
