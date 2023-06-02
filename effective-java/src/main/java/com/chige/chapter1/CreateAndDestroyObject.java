package com.chige.chapter1;


/** 创建和销毁对象
 * @author wangyc
 * @date 2022/3/26
 */
public class CreateAndDestroyObject {
    //第一条：考虑使用静态方法而不是构造器
    public static void one() {
        boolean flag = Boolean.TRUE;
    }

    //
    //第二条：遇到多个构造参数时，考虑用构建者Builder
    public static void two() {
        // 自定义建造者模式
        House myBuilderHouse = House.builder()
                .floor("2")
                .safa("3")
                .watchTV("4")
                .computer("5")
                .build();
        System.out.println(myBuilderHouse);

        // 建造者注解
        HouseBuilder build = HouseBuilder.builder()
                .floor("1")
                .safa("2")
                .computer("3")
                .tv("4")
                .build();
        System.out.println(build);
    }


    public static void main(String[] args) {
        two();
    }
}
