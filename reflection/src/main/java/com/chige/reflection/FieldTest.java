package com.chige.reflection;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.binding.StringBinding;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.IntBinaryOperator;

/** 利用反射设置、获取属性值
 * @author wangyc
 * @date 2022/7/24
 */
public class FieldTest {
    static ObjectMapper objectMapper = new ObjectMapper();

    public static void test() throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, JsonProcessingException {
        Person person = new Person();
        Class<? extends Person> aClass = person.getClass();
        Field field = aClass.getDeclaredField("age");
        Method setAge = aClass.getMethod("setAge", field.getType());

        setAge.invoke(person, 1);
        String s = objectMapper.writeValueAsString(person);
        System.out.println(s);
    }

    public static void main(String[] args) throws NoSuchFieldException, JsonProcessingException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
//        FieldTest.test();
//        int num = 3;
//        Integer[][] res = test2(num);
//        System.out.println(objectMapper.writeValueAsString(res));
        sort1();;
    }

    public static void sort1() {
        Double[] a = {1.2,1.3,3.7,0.2};
        Arrays.sort(a, Collections.reverseOrder());
        for (Double aDouble : a) {
            System.out.println(aDouble);
        }
    }

    public static Integer[][] test2(int num) {
        int all = (int) Math.pow(2.0d, num) - 2;
        Integer[][] res = new Integer[all][];
        for (int i = 1; i <= all; i++) {
            String binaryString = Integer.toBinaryString(i);
            int length = binaryString.length();
            int b = num - length;
            StringBuilder sb = new StringBuilder();
            while (b-- > 0) {
                sb.append("0");
            }
            sb.append(binaryString);
            String[] split = sb.toString().split("");

            res[i - 1] = Arrays.stream(split).map(Integer::valueOf).toArray(Integer[]::new);
        }
        return res;
    }
    static class TplArgs {
        private Integer unitId;
        private Integer proxyUnitId;
        private Integer groupId;
        private Integer goodsId;
        private Integer packageTypeId;
        public TplArgs() {
            this.unitId = 0;
            this.proxyUnitId = 0;
            this.groupId = 0;
            this.goodsId = 0;
            this.packageTypeId = 0;
        }
    }

}
