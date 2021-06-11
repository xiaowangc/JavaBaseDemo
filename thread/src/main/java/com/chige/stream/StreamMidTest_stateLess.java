package com.chige.stream;

import java.util.*;
import java.util.function.Function;
import java.util.stream.*;

/** 流式数据处理-中间操作
 * @author wangyc
 * @date 2021/6/5 10:17
 */
public class StreamMidTest_stateLess {
    /**
     *  流的创建
     *  【常用】1、通过java.util.Collection.stream()方法创建
     *         2、通过java.util.Arrays.stream(T[] array)方法用数组创建流
     *
     */
    public void createStream() {
        //java.util.Collection.stream()方法创建
        List<Integer> list = Arrays.asList(1,2,3,4);
        Stream<Integer> stream = list.stream();
        stream.collect(Collectors.toList()).forEach(System.out::println);
        //java.util.Arrays.stream(T[] array)方法用数组创建流
        Integer[] array = new Integer[]{1,2,3,4};
        Stream<Integer> stream1 = Arrays.stream(array);
        stream1.collect(Collectors.toList()).forEach(System.out::println);

    }

    /**
     *  stream的静态方法：of()、 iterate()、 generate()
     */
    public void StreamStaticMethod() {
        System.out.println("==== of ===");
        // of方法
        List<Person> personList = Arrays.asList(new Person("w1",1),new Person("w2",2),new Person("w3",3));
        Stream<List<Person>> integerStream = Stream.of(personList);
        integerStream.forEach(System.out::print);

        personList.stream().filter(person -> person.age > 1).collect(Collectors.toList()).forEach(System.out::print);
        System.out.println();
        System.out.println("==== iterate ===");
        // iterate方法:需要加短路技巧的限制，否则相当于一个死循环不停地执行
        Stream<Integer> iterate = Stream.iterate(3, x -> x + 2).limit(3);
        iterate.forEach(System.out::println);

        System.out.println("==== generate ===");
        // generate方法：需要加短路技巧的限制
        Stream<Double> limit = Stream.generate(Math::random).limit(3);
        limit.forEach(System.out::println);
    }

    /**
     *  中间操作-无状态操作
     */
    public void stateLess_filter() {
        // 需求：将筛选出字符串列表中大于的2的整形字符串
        List<String> list = Arrays.asList("1", "2", "3", "4");
        list.stream().mapToInt(Integer::parseInt).filter(x -> x > 2).forEach(System.out::println);
        // 需求2：从单词列表中筛选出长度大于5的单词
        System.out.println("===Need 2===");
        List<String> wordList = Arrays.asList("dog","money","orange","origin","house");
        wordList.stream().filter(word -> word.length() > 5).forEach(System.out::println);
    }
    public void stateLess_map() {
        // 需求：将字符串类型转换成整形类型
        List<String> list = Arrays.asList("1", "2", "3", "4");
        list.stream().map(s -> Integer.parseInt(s)).forEach(System.out::println);

        System.out.println("===Need 2===");
        // 需求2：将有序set集合转换成list列表
        Set<String> orderSet = new TreeSet<>();
        orderSet.add("1");
        orderSet.add("2");
        orderSet.add("3");
        List<String> collect = orderSet.stream().collect(Collectors.toList());
        collect.forEach(System.out::println);

        // 需求3：提取某个对象中的属性,如提取person列表中的姓名属性
        List<Person> personList = Arrays.asList(new Person("w1",1),new Person("w2",2),new Person("w3",3));
        // 方法引用-类的实例方法
        Function<Person,String> function = Person::getName;// 方法引用-类的实例方法
        List<String> collect1 = personList.stream().map(function).collect(Collectors.toList());
        collect1.forEach(System.out::println);

        System.out.println("===Need 4===");
        // 需求4：将list列表转换成set集合
        Set<String> set1 = personList.stream().map(Person::getName).collect(Collectors.toSet());
        Map<String,Integer> map1 = personList.stream().collect(Collectors.toMap(Person::getName,Person::getAge));
        TreeSet<String> set2 = personList.stream().map(Person::getName).collect(Collectors.toCollection(TreeSet::new));
        set1.forEach(System.out::println);
        set2.forEach(System.out::println);
    }


    /**
     *  list列表转set
     */
    public void list_to_set() {
        // 提取person列表的所有姓名，并存放到set集合中
        List<Person> personList = Arrays.asList(new Person("w1",1),new Person("w2",2),new Person("w3",3));
        Set<String> set1 = personList.stream().map(Person::getName).collect(Collectors.toSet());
        TreeSet<String> set2 = personList.stream().map(Person::getName).collect(Collectors.toCollection(TreeSet::new));
        set1.forEach(System.out::println);
        set2.forEach(System.out::println);
    }
    /**
     *  list 转 map
     */
    public void list_to_map() {
        // 不对数据格式进一步转换
        List<Person> personList = Arrays.asList(new Person("w1",1),new Person("w2",2),new Person("w3",3));
        Map<String,Integer> map1 = personList.stream().collect(Collectors.toMap(Person::getName,Person::getAge));
        map1.keySet().forEach(System.out::println);
        map1.values().forEach(System.out::println);

        // 将person列表转成map<String,String>的格式
        Map<String, String> collect = personList.stream().collect(Collectors.toMap(person -> person.getAge().toString(), Person::getName));
        collect.keySet().forEach(System.out::println);

    }
    /**
     *  TODO set集合转list列表
     */
    public void set_to_list() {
        // 提取person列表的所有姓名，并存放到set集合中
        Set<Person> personSet = new HashSet<>();
        personSet.add(new Person("t1",10));
        personSet.add(new Person("t2",4));
        personSet.add(new Person("t3",49));
//        List<Person> result = new ArrayList<>(personSet);
//        result.forEach(System.out::println);
        List<Person> personList = personSet.stream().collect(Collectors.toList());
        personList.forEach(System.out::println);
    }
    public void set_to_map() {
        Set<Person> personSet = new HashSet<>();
        personSet.add(new Person("t1",10));
        personSet.add(new Person("t2",4));
        personSet.add(new Person("t3",49));
        Map<String, Integer> map = personSet.stream().collect(Collectors.toMap(Person::getName, Person::getAge));
        map.keySet().forEach(System.out::println);
        map.values().forEach(System.out::println);
    }

    public void map_to_list() {
        Map<String,Person> map = new HashMap<>();
        map.put("p1",new Person("m1",12));
        map.put("p2",new Person("m2",14));
        map.put("p3",new Person("m3",6));
        List<Person> collect = map.values().stream().sorted(Comparator.comparing(Person::getAge)).collect(Collectors.toList());
        collect.forEach(System.out::println);
    }

    public void string_to_class() {
        String str = "m1:12";
        String str2 = "m2:13";
        String str3 = "m3:2";
        List<Person> collect = Stream.of(str,str2,str3).map(s -> {
            String[] split = s.split(":");
            return new Person(split[0],Integer.valueOf(split[1]));
        }).sorted(Comparator.comparing(Person::getAge)).collect(Collectors.toList());
        collect.forEach(System.out::println);
    }

    /**
     *  使用map方法: 入参类型为T 转换后结果类型为R
     */
    public void map() {
        List<String> worldList = Arrays.asList("abc","Agc");
        List<String> resultList = worldList.stream().map(String::toUpperCase).collect(Collectors.toList());
        resultList.forEach(System.out::println);
    }
    /**
     *  flatMap方法：入参类型为 T，将流中的每个元素进行转换，结果为 流R
     */
    public void flatMap() {
        List<String> worldList = Arrays.asList("abc","Agc");
        List<String> resultList = worldList.stream().flatMap(s -> Stream.of(s.toUpperCase())).collect(Collectors.toList());
        resultList.forEach(System.out::println);
    }

    /**
     *  peek方法：入参为Consumer<T>函数，peek操作会按照Consumer<T>提供的逻辑去消费流中的每一个元素，
     *  同时有可能会改变元素内部中的一些属性
     *  Consumer<T>是一个函数式接口，一个抽象方法void accept(T t)意为接收一个参数T,并将其消费掉，返回void
     */
    public void peek() {
        List<String> str = Arrays.asList("abc","123");
        List<String> collect = str.stream().peek(System.out::println).collect(Collectors.toList());
    }

    /**
     * mapToInt、mapToLong、mapToDouble、flatMapToDouble、flatMapToInt、flatMapToLong
     *
     * LongStream转为Stream只需要调用boxed()方法
     */
    public void map_to_other() {
        // mapToInt
        List<String>  str = Arrays.asList("abc34","123ab");
        str.stream().mapToInt(String::length).forEach(System.out::println);
        List<Integer> collect = str.stream().mapToInt(String::length).boxed().collect(Collectors.toList());
        collect.forEach(System.out::println);

        System.out.println("=====mapToLong===");
        //mapToLong
        List<Person> personList = Arrays.asList(new Person("xx1",1),new Person("ss1",6),new Person("fff2",3));
        List<Long> longList =  personList.stream().mapToLong(Person::getAge).sorted().boxed().collect(Collectors.toList());
        longList.forEach(System.out::println);

        System.out.println("longStream_to_list===");
        List<Long> longStream_to_list = personList.stream().sorted(Comparator.comparing(Person::getAge)).mapToLong(Person::getAge).boxed().collect(Collectors.toList());
        longStream_to_list.forEach(System.out::println);
    }

    /**
     *  TODO 如何将LongStream转换成Stream：调用boxed()方法
     */


    /**
     *  中间操作-有状态操作
     */


    public static void main(String[] args) {
        StreamMidTest_stateLess streamMidTest = new StreamMidTest_stateLess();
//        streamMidTest.createStream();
//        streamMidTest.StreamStaticMethod();

        // 无状态-filter
//        streamMidTest.stateLess_filter();
//        streamMidTest.stateLess_map();
//        streamMidTest.map_to_list();
//        streamMidTest.string_to_class();
        streamMidTest.map_to_other();
    }





    public class Person{
        private String name;
        private Integer age;

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }

        public Person(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }
}
