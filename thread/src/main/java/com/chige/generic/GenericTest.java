package com.chige.generic;

import com.sun.corba.se.impl.ior.OldJIDLObjectKeyTemplate;

import java.util.ArrayList;
import java.util.List;

/** 泛型：
 * List 、List<Object>、 List<?> 三者的区别、
 * <? extends> 与 <? super T>的使用场景。
 * @author wangyc
 * @date 2021/9/14
 */
public class GenericTest {

    /**
     *  List<?> 是一个泛型，在没有赋值之前，表示他可以接收任务类型的集合赋值，赋值之后就不能随便往里添加元素了
     */
    public static void main(String[] args) {
        // 第一段： 泛型出现之前的集合定义方式
        List a1 = new ArrayList();
        a1.add(new Object());
        a1.add(new Integer(111));
        a1.add(new String("hello a1"));
        for (Object o : a1) {
            System.out.println("o = " + o);
        }
        // 第二段： 把a1引用赋值给a2,注意a2与a1的区别是增加了泛型限制List<Object>
        List<Object> a2 = a1;
        a2.add(new Object());
        a2.add(new Integer(222));
        a2.add(new String("hello a2"));
        for (Object o : a2) {
            System.out.println("o2 = " + o);
        }
        // 第三段： 把a1引用赋值给a3，注意a3与a1的区别是增加了泛型限制<Integer>
        List<Integer> a3 = a1;
        a3.add(new Integer(333));
//        a3.add(new Object());
//        a3.add(new String("123"));
        for (Integer integer : a3) {
            System.out.println("o3 = " + integer);
        }
        // 第四段 a1赋值给a4, a1与a4的区别是增加了通配符
        List<?> a4 = a1;
        for (Object o : a4) {
            System.out.println("o4 = " + o);
        }
        a1.remove(0);
        a4.clear();
//        a4.add(new Object()); // 编译出错。不允许添加任何元素
        List<?> a5 = new ArrayList<>();

        List<Object> a6 = new ArrayList<>();
        a6.add(new Integer(111));
        List<Integer> a7;

    }


}
