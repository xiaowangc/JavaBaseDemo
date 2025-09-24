package com.chige.generator.demo1;

/** 范型类
 * @author wangyc
 * @date 2023/12/23
 */
public class GeneratorClass<T> {

    private T data;



    public GeneratorClass(T a) {
        this.data = a;
    }

    public static <T> void t(T a) {
        System.out.println(a.getClass());
    }

    private Integer code;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }


}
