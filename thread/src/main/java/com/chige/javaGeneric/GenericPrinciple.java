package com.chige.javaGeneric;

import java.util.ArrayList;
import java.util.List;

/** 泛型实现原理：擦除
 * @author wangyc
 * @date 2022/3/27
 */
public class GenericPrinciple {

    public static void main(String[] args) {
        List<String> strList = new ArrayList<>();
        List<Integer> intList = new ArrayList<>();
        System.out.println(strList.getClass() == intList.getClass());

        GenericClass<Long> stringGenericClass = new GenericClass<>();
        GenericClass<Integer> integerGenericClass = new GenericClass<>();
        System.out.println(stringGenericClass.getClass() == integerGenericClass.getClass());


    }
}
