package com.chige.generator.demo2;

/**
 * @author wangyc
 * @date 2023/12/23
 */
public class GeneratorInterfaceIntegerImpl implements GeneratorInterface<Integer> {
    @Override
    public Integer createData() {
        return 1;
    }
}
