package com.chige.stream;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** 中间操作-有状态
 * @author wangyc
 * @date 2021/6/8 11:19
 */
public class StreamMidTest_stateFul {

    /**
     *  distinct 流中的元素必须实现hashCode和equals方法
     *  去重之后还是按照原流中的排序顺序输出的，所以是有序的！
     */
    public void distinct() {
        List<String> list = Arrays.asList("a","b","a","c","d","f","b");
        List<String> collect = list.stream().distinct().collect(Collectors.toList());
        collect.forEach(System.out::println);
    }

    /**
     *  sorted 有两种形式 有参和无参，有参的可以传入自定义比较器
     */
    public void sorted() {
        // 无参
        Stream<Integer> stream = Stream.of(1,3,4,6,1,2,67,2);
        // 去重并排序
        stream.sorted().distinct().forEach(System.out::println);

        Set<Person> personList = new HashSet<>();
        personList.add(new Person(1L,"x1",14));
        personList.add(new Person(2L,"x2",13));
        personList.add(new Person(5L,"x3",11));
        personList.add(new Person(0L,"x49",165));
        personList.add(new Person(5L,"x3",11));
        // 有参-自定义比较器
        personList.stream().sorted(Comparator.comparing(Person::getAge)).collect(Collectors.toList()).forEach(System.out::println);
    }

    public static void main(String[] args) {
//        StreamMidTest_stateFul stateFul = new StreamMidTest_stateFul();
//        stateFul.sorted();
        List<Integer> list = Arrays.asList(1882856,989351,850163,614032,1476881,1594008,3449797,3030808,20509953,378339,225449265,36710753,26888877,235990656,250931,20461769,35566451,1572053,45299,3576859,3675233,2289341,25244745,233992720,224518311,231821998,23075185,20485743,1762286,3453270,259690391,2135300,2973245,3459398,3696697,282312,1874659,148430,28625127,21657349,31589089,221674971,750095,20119518,21679149,2989624,3100299,272010925,26641665,27553871,708784,310372,2593446,2974357,2866801,23057777,24847935,874522,21282212,21293244,22938261,22656847,20141836,236085824,33282041,232766126,229401207,246053467,221842137,20399579,25112561,225881825,30046301,30488685,220196635,2577504,1030995,1464642,25784095,3289465,3291337,241198114,67181,20582280,2825540,3111533,3902118,909481,408187,1513679,1850530,3610954,20777155,20026986,229775416,225078661,33676603,26679829,222308515,260003497,257130775,1243212,22891867,21890497,3890772,2246710,3930633,25016053,21892119,267734835);
        List<Integer> list2 = Arrays.asList(2055549,1948707,2013915,25680093,873675,20000870,184454,858456,21142706,228098929,26553349,31463065,222856361,1432896,220793831,2645543,1101469,37501159,223552529,36043423,24486617,21235794,20783656,23668783,247313207,244389014,265551689,265224249,273901407,1017037,2193909,267497563,272,1455727,642026,223769623,12183,721262,1464949,227911513,38344093,224356973,225118455,227437689,272820203,259163433,2424239,2565552,248437325,393852,1396118,1751025,1746103,2438381,2585815,1778842,808422,1267371,20914251,396812,918330,22087817,29405795,35218913,37700453,224718353,242883498,255939415,266431181,265813875,269099843,2389795,680400,222871,959535,3611784,21772193,252795721,228667697,38313959,224092891,29882611,1950406,3504751,2891474,27053515,25524769,29163105,20016041,2305144,3542236,227235041,1577,275287,1856482,971443,2184149,4055157,20076812,3683028,481346,394916,20204589,239371470,30537721,223500875,254659339,235342108);
        System.out.println(list2.size());
        System.out.println(list.size());
        int j = 0;
        for (Integer i : list) {
            boolean contains = list2.contains(i);
            if (contains) {
                System.out.println(j++);
            }
        }

    }
    public class Person{
        // 相当于身份证
        private Long idCard;
        private String name;

        @Override
        public String toString() {
            return "Person{" +
                    "idCard=" + idCard +
                    ", name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }

        private Integer age;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return Objects.equals(idCard, person.idCard);
        }

        @Override
        public int hashCode() {
            return Objects.hash(idCard);
        }

        public Long getIdCard() {
            return idCard;
        }

        public void setIdCard(Long idCard) {
            this.idCard = idCard;
        }

        public Person(Long idCard, String name, Integer age) {
            this.idCard = idCard;
            this.name = name;
            this.age = age;
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
