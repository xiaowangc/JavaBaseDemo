package com.chige.utils.xml;

import com.citicbank.baselib.crypto.util.FileUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.*;
import com.thoughtworks.xstream.converters.collections.CollectionConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.mapper.ClassAliasingMapper;

import java.util.ArrayList;
import java.util.List;

/** XStream是Java类库，用来将对象序列化成XML （JSON）或反序列化为对象
 * @author wangyc
 * @date 2023/4/19
 */
public class XStreamUtils {


    public static void main(String[] args) {
        try {
            FileUtil.read4file("");
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<String> hobbies = new ArrayList<>();
        hobbies.add("play");
        hobbies.add("learn");

        List<Person> list = new ArrayList<>();
        Person brother = new Person(1, "哥哥", 22, hobbies, null);
        Person sister = new Person(2, "妹妹", 17, null, null);
        list.add(brother);
        list.add(sister);
        Person p = new Person(3, "小明", 20, hobbies, list);

        List<PersonAlias> paList = new ArrayList<>();
        PersonAlias paBorther = new PersonAlias(1, "哥哥", 22, hobbies, null);
        PersonAlias paSister = new PersonAlias(2, "妹妹", 17, null, null);

        paList.add(paSister);
        paList.add(paBorther);
        PersonAlias pa = new PersonAlias(3, "小明", 20, hobbies, paList);
        User user = new User();
        user.setUserName("测试");
        pa.setUser(user);
        test3(pa);

    }

    /**
     * 简单使用
     */
    public static void test1(Person p) {
        XStream xs = new XStream();
        xs.alias("ROOT", Person.class);
        xs.useAttributeFor(Person.class, "id");
        String xml = xs.toXML(p);
        System.out.println(xml);
    }

    /**
     * 转换器:
     * <hobbies>
     *     <string>play</string>
     *     <string>learn</string>
     *  </hobbies>
     *
     *  将下面的string 转换成hobby
     *  <hobbies>
     *     <hobby>play</hobby>
     *     <hobby>learn</hobby>
     *  </hobbies>
     */
    public static void test2(Person p) {
        XStream xs = new XStream();
        xs.alias("person", Person.class);
        xs.aliasField("code", Person.class, "id");
        xs.useAttributeFor(Person.class, "id");
        ClassAliasingMapper classAliasingMapper = new ClassAliasingMapper(xs.getMapper());
        classAliasingMapper.addClassAlias("hobby", String.class);
        xs.registerLocalConverter(Person.class, "hobbies", new CollectionConverter(classAliasingMapper));
        String xml = xs.toXML(p);
        System.out.println(xml);
    }


    /**
     * 使用注解
     * 别名注解，使用注解 @XStreamAlias
     * 属性注解， @XStreamAsAttribute
     * 注解生效，使用autodetectAnnotations(boolean mode)方法
     */
    public static void test3(PersonAlias p) {
        XStream xs = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        xs.autodetectAnnotations(true);
        ClassAliasingMapper classAliasingMapper = new ClassAliasingMapper(xs.getMapper());
        classAliasingMapper.addClassAlias("ho_bby", String.class);
        xs.registerLocalConverter(PersonAlias.class, "hobbies", new CollectionConverter(classAliasingMapper));
        String xml = xs.toXML(p);
        System.out.println(xml);

    }

    @XStreamAlias("person")
    public static class PersonAlias {
        @XStreamAlias("code")
        @XStreamAsAttribute
        private int id;
        @XStreamAlias("name")
        private String name;
        @XStreamAlias("age")
        private int age;
        @XStreamAlias("hobbies")
        private List<String> hobbies;
        @XStreamAlias("family")
        private List<PersonAlias> family;
        private User user;

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public PersonAlias(int id, String name, int age, List<String> hobbies, List<PersonAlias> family) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.hobbies = hobbies;
            this.family = family;
        }

        @Override
        public String toString() {
            return "Person [id=" + id + ", name=" + name + ", age=" + age + ", hobbies=" + hobbies + ", family=" + family
                    + "]";
        }
    }



    public static class Person {
        private int id;
        private String name;
        private int age;
        private List<String> hobbies;
        private List<Person> family;

        public Person(int id, String name, int age, List<String> hobbies, List<Person> family) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.hobbies = hobbies;
            this.family = family;
        }

        @Override
        public String toString() {
            return "Person [id=" + id + ", name=" + name + ", age=" + age + ", hobbies=" + hobbies + ", family=" + family
                    + "]";
        }
    }
}


