package com.chige.reflect;

import cn.hutool.core.collection.CollectionUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.tools.ant.util.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangyc
 * @date 2022/4/19
 */
public class ReflectDemo {

    public void getMethodInfo() throws InvocationTargetException, IllegalAccessException {
        Method[] methods = ReflectDemo.class.getMethods();
        Map<String, Method> methodMap = new HashMap<>();
        for (Method method : methods) {
            methodMap.put(method.getName(), method);
        }
        Method method = methodMap.get("getList1");
        Method method2 = methodMap.get("getList2");
        Object result = method.invoke(this, null);
        Object result2 = method2.invoke(this, null);
        Class<?> returnType = method.getReturnType();
        System.out.println("是否为数组类型：" + returnType.isArray());
        System.out.println("是否为列表类型：" + returnType);
        if (returnType == java.util.List.class) {
            ParameterizedType genericReturnType = (ParameterizedType) method.getGenericReturnType();
            Type actualTypeArgument = genericReturnType.getActualTypeArguments()[0];
            if (actualTypeArgument == java.lang.Integer.class) {
                System.out.println("元素类型为：Integer.class");
            }else if (actualTypeArgument == Person.class) {
                List list = (List) result;
                List list2 = (List) result2;
                System.out.println(list.size() == list2.size() && list.containsAll(list2));
            }
        }else {
            System.out.println("方法结果不为列表类型");
        }

    }

    public List<Person> getList1() {
        List<Person> personList = new ArrayList<>();
        personList.add(new Person(411,"王1"));
        personList.add(new Person(18,"王2"));
        personList.add(new Person(null,"王5"));
        personList.add(new Person(11,"王4"));
        personList.add(new Person(14,"王13"));
        return personList;
    }
    public List<Person> getList2() {
        List<Person> personList = new ArrayList<>();
        personList.add(new Person(18,"王2"));
        personList.add(new Person(15,"王13"));
        personList.add(new Person(411,"王1"));
        personList.add(new Person(11,"王4"));
        personList.add(new Person(null,"王5"));
        return personList;
    }


    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
       ReflectDemo reflectDemo = new ReflectDemo();
       reflectDemo.getMethodInfo();
    }

    @AllArgsConstructor
    @ToString
    @EqualsAndHashCode
    @Data
    public static class Person {
        private Integer age;
        private String name;
    }

}
