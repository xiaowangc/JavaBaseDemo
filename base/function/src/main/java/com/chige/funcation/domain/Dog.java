package com.chige.funcation.domain;



/** 基本函数式
 * @author wangyc
 * @date 2023/12/23
 */
public class Dog {

    private Integer weight = 10;

    public Dog() {
    }
    public Integer eat(Integer a) {
        System.out.println("狗吃东西，吃了" + a + "斤");
        return weight - a;
    }
    public Dog bark() {
        System.out.println("狗吠！");
        return this;
    }

}



