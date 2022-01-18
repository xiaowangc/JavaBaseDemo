package com.chige.objects;



import java.util.Objects;

import static java.util.Objects.*;

/**
 * @author wangyc
 * @date 2021/12/24
 */
public class JdkObjectsTest {
    /**
     *  JDK1.7 中 Objects工具类的使用
     */
    public static void test1() {
        String a = null;
        String b = "af";
        boolean equalAB = Objects.equals(a,b);
        boolean aNull = isNull(a);
        String message = "字符串a为Null";
        /**
         *  requireNonNull方法
         */
        String m1 = requireNonNull(b, message);

        System.out.println(equalAB);
        System.out.println(m1);
    }

    /**
     *  hashCode 生成
     */
    public static void test2() {
        String personId = "";
        String idType = null;
        int hash = hash(personId, idType);
        int hash1 = hash(null, null);
        System.out.println(hash);
        System.out.println(hash1);
        System.out.println(hash(null));
        System.out.println(hash("",""));
        System.out.println(hash(1,"2"));
        System.out.println(hash("2",1));
        System.out.println(hash(""));

        System.out.println();
        System.out.println(hash("",""));
        System.out.println(hash(1,"2"));
        System.out.println(hash("2",1));
        System.out.println(hash(""));
    }

    /**
     *  toString()
     */
    public static void test3() {
        Person person = new Person();
        Person p1 = null;
        String s = Objects.toString(person, "Person:{\"personId\":\"1\",\"idType\":\"2\"}");
        String s1 = Objects.toString(p1, "Person:{\"personId\":null,\"idType\":1}");
        System.out.println(s);
        System.out.println(s1);
    }
    public static void main(String[] args) {
        test3();
    }

    public static class Person {
        private String personId;
        private Integer idType;

        @Override
        public String toString() {
            return "personId=" + this.personId + ",idType=" + this.idType;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Person)) return false;
            Person person = (Person) o;
            return Objects.equals(person, person.personId) && Objects.equals(idType, person.idType);
        }

        @Override
        public int hashCode() {
            return Objects.hash(personId, idType);
        }
    }
}
