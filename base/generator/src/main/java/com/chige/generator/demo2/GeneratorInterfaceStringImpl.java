package com.chige.generator.demo2;

/** 泛型接口
 * @author wangyc
 * @date 2023/12/23
 */
public class GeneratorInterfaceStringImpl implements GeneratorInterface<String> {


    @Override
    public String createData() {
        return "create!";
    }
}
