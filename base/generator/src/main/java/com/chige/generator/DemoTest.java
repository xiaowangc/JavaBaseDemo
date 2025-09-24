package com.chige.generator;

import com.chige.generator.demo1.GeneratorClass;
import com.chige.generator.demo2.GeneratorInterface;
import com.chige.generator.demo2.GeneratorInterfaceIntegerImpl;
import com.chige.generator.demo2.GeneratorInterfaceStringImpl;
import com.chige.generator.demo3.GeneratorMethod;

/**
 * @author wangyc
 * @date 2023/12/23
 */
public class DemoTest {

    public void test1() {
        GeneratorClass generatorClass = new GeneratorClass<>(1);
        GeneratorClass generatorClass2 = new GeneratorClass<>("2");
        System.out.println(generatorClass.getCode());
        System.out.println(generatorClass2.getCode());
    }

    public void test2() {
        GeneratorInterface<String> generatorInterface = new GeneratorInterfaceStringImpl();
        System.out.println(generatorInterface.createData());

        GeneratorInterface<Integer> generatorInterface2 = new GeneratorInterfaceIntegerImpl();
        System.out.println(generatorInterface2.createData());

    }

    public void test3() {
        GeneratorMethod generatorMethod = new GeneratorMethod();
        generatorMethod.create(new GeneratorClass<>(1));
        generatorMethod.create(new GeneratorClass<>(1.2d));
        generatorMethod.create(new GeneratorClass<>(1.0f));
    }

    public static void main(String[] args) {

    }

}
