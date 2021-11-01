package com.chige.test;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.AllArgsConstructor;
import lombok.Data;


import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author wangyc
 * @date 2021/6/10 15:28
 */
public class Test1 {
    /**
     * 测试map的排序
     */
    public static void test1() {
        Map<String, Integer> map = new TreeMap<>();
        for (int i = 1; i <= 20; i++) {
            int i1 = RandomUtil.randomInt(1, 50);
            map.put(i + "", i1 + 1);
        }
        Map<String, Integer> resultE = new LinkedHashMap();
        Map<String, Integer> result = new LinkedHashMap();
        Comparator<Map.Entry<String, Integer>> entryComparator = Map.Entry.comparingByValue();
        entryComparator = entryComparator.reversed();
        map.entrySet().stream().sorted(entryComparator).forEachOrdered((e) -> {
            if (e.getValue() >= 20) {
                result.put(e.getKey(), e.getValue());
            }
        });
        System.out.println(result);
        System.out.println(resultE);
    }

    /**
     * 返回空列表
     */
    public static void test2() {
        System.out.println("空1" + new ArrayList<>());
        System.out.println("空2" + Collections.emptyList());
    }
    public static void test3() {
        int scard = 423;
        int size = 30;
        Set<String> ss = new HashSet<>();
        for (int i = 0; i < scard; i++) {
            ss.add("a" + i);
        }
        List<String> listAll = new ArrayList<>(ss);
        int tmp = 0;
        AtomicInteger atomicInteger = new AtomicInteger(0);
        while (scard > 0) {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                String s = listAll.get(tmp++);
                list.add(s);
                scard--;
                if (scard == 0) {
                    break;
                }
            }
            atomicInteger.addAndGet(list.size());
            System.out.println("重试-加载医生id数量为:" + list.size());
        }
        System.out.println("tmp=" + tmp + "-" + "加载的数量为：" + atomicInteger.get() + "scard:" + scard);

    }

    public static void test4() {
        int i = 0;
        long[] doctorList = {1,2,3,4,5,6,7,8,9,10,11,12,13,4231,2353,2344,1232,542};
        for (long doctorId : doctorList) {
            if (doctorId % 3 == 2) {
                System.out.println("满足："+ doctorId);
            }else {
                System.out.println("不满足");
            }
        }
    }

    public static List<Integer> test5() {
        List<Integer> list1 = Arrays.asList(2128835,4027874,3465506,3863724,31417157,38051293,220062291,
                232255656,240112288,229667358,252547403,241493578,241718342,253709957,778414,228353,1808542,
                2008853,895865,2965544,24152823,267275957,268718425,32889605,21022208,867409,22721343,1818709,
                2869460,29658525,221759695,223146407,256993613,244758465,260944171,20003783,1927315,21377804,
                1723414,2442252,1790794,269690381,30341809,3773677,25904469,1654675,3322966,1734,3830619,
                2993338,224796259,232869408,815836,965430,247992931,267141741,25784225,242288914,29441573,
                27066747,2126571,1096917,237808118,280882,2299442,21048919,3384537,225365167,224316343
                ,220078763,38168109,227710421,229164455,270833489,256927513,244313520,1526984,262212675,
                262555119,648397,22458831,2998086,3591344,106583,24175733,23281769,119357,1929964,24494617,
                30356065,368322,223297273,26790763,167816,224743335,34229041,222190365,243025598,2231527,2200911,686277,1887445,21346842,220024827,34422089,28730903,26299665,26382441,3126795,2591813,231500046,258377095,28814251,3334371,254891615,34413229,35511183,238230168);
        List<Integer> list2 = Arrays.asList(1089741,2240540,222779883,223876967,272337135,265442135,256992985,1723242,899376,2177927,21070862,29415187,1416988,1170556,1909979,29015207,36188215,33635389,1512928,419600,2366388,4071659,226909859,232809182,25081881,273178153,36791351,26820071,259721,33633559,466990,3153371,943592,2411848,2970887,21715725,220840539,222006297,253396009,253852759,232607086,243762802,240361730,36876301,36913443,3136001,20289862,20699961,268609767,20653858,831416,2916742,2855299,20055176,31987877,36043711,1218874,233736326,26295473,3891141,2584967,20411526,248158505,3446809,30115475,20960487,26205521,26235595,37724669,901452,23416255,267323627,28967081,31738827,37942731,467272,376614,224019765,32565007,223156385,235270898,247923017,3239877,252362201,2149899,748026,27712689,29814683,20797543,273428915,289164,33609467,29178707,987850,1057689,23489967,37192401,265685355,259388543,247761759,247455191,34526551,414431,21241477,235898302,1205597,555087,24929583,37225901,29152549,1070653,20991853,24346357,37036697,2817854,3021680);
        List<Integer> list3 = Arrays.asList(328869,1885403,2735653,224593335,32896935,36650319,239976494,227765787,259440675,253505447,876485,22360285,3360333,1825200,242582818,33497887,34970213,2821224,269636045,270642997,1643275,2085250,225224533,226848045,31965101,235218094,242073734,267248363,247890199,254713491,2385984,30210661,20812454,34140089,1056601,25598517,23488917,220427,221116229,35071071,228421795,239591492,392067,952627,248807297,3113903,2541016,20745663,236975236,1365647,20365109,35301513,180566,34395873,32223219,20543428,20687134,20711388,230581086,230652758,239367510,220540677,220331559,244846373,261499065,447825,1188410,1223685,2752318,20090405,37763117,3760271,231402454,1725102,238429864,236030738,1621033,25405153,936459,223646677,225784581,221735095,222661757,223017045,232712100,256559311,1902318,975788,1178861,2845248,247383985,26402635,236992582,21397760,30117729,3944257,3124369,224194943,221962241,35301809,227423721,252932783,255247403,258545177,1206935,373070,243035652,3672563,1898836,28304685,27084147,208776,522612,2859823,32773155,3690492,2766210);
        List<Integer> list4 = Arrays.asList(783201,1485370,2986139,225013703,231243152,231874272,235994986,220573747,221120211,20242242,23198807,2812304,3191579,1764299,21512816,24306687,3679429,28714305,20488249,156529,678462,27613143,36065227,222218863,232133500,229483499,251463911,266484281,253693895,1213493,897117,3017679,76883,29635737,239221144,36127207,29043317,3434182,986819,2535902,3399130,221447273,266145989,241679300,243264356,27741949,496993,884747,25410293,21337624,2582539,29096247,24445031,3150637,1526655,3079320,229045341,1544518,541067,716098,1741329,3658894,3517167,3847234,259189705,258995501,783313,1784574,262356525,444493,21101972,2106641,24887671,532910,3683184,232688216,34281811,30609541,152112,316608,1030479,3354832,230420040,230306526,221977095,227985485,251801357,251985705,251825819,1964564,4071881,183481,172550,223239857,221084815,253023711,237308262,24073,1064913,1837068,3100898,1936617,2071560,2133978,1331985,27437529,691775,238413374,20027192,768508);
        List<Integer> list5 = Arrays.asList(257291,3326802,222241771,256350969,1805754,369621,244746895,1810902,1553033,36465115,1177490,33126853,226086635,245259367,255008879,831954,1040521,30185163,37498951,30261727,23308279,2091233,2880834,27683435,32592011,225505643,226405567,231532708,253742927,265035243,379265,645737,695762,808936,33420771,3891080,4063978,30501625,2374455,27201027,223264009,1828574,3515066,20523517,21661352,229073095,224386905,273583019,2597374,230515240,228747459,253121183,254928711,1666254,274672,409087,21357094,21391541,2470724,2163984,1650126,26350053,259611227,221931665,266659433,268582645,20430468,3617154,3927697,35229473,31511629,884536,2303675,1540191,1435830,877219,32497,20524071,221077525,239996596,254149141,1342931,21043822,23545351,231495390,1914563,633793,660277,245028,220725355,223106765,252704317,243233130,256242293,259478473,248726127,270714811,3636215,971147,650879,1987029,26504973,27444791,228397277,1536582,34754801,1311731);
        Set<Integer> set = new HashSet<>();
        set.addAll(list1);
        set.addAll(list2);
        set.addAll(list3);set.addAll(list4);set.addAll(list5);
        System.out.println("size=" + (list1.size() + list2.size() +list3.size() +list4.size() +list5.size()));
        System.out.println("set siez=" + set.size());
        List<Integer> result = new ArrayList<>();
        for (Integer integer : list1) {
            if (list2.contains(integer)) {
                result.add(integer);
            }
        }
        return result;
    }
    // 求列表交集
    public static void test6() {
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        list1.add(1);
        list1.add(2);
        list1.add(3);
        list2.add(2);
        list2.add(3);
        list2.add(4);
        list1.retainAll(list2);
        System.out.println(list1);
        System.out.println(list2);
        Set<Integer> set1 = new HashSet<>();
        Set<Integer> set2 = new HashSet<>();
        set1.add(1);
        set1.add(2);
        set1.add(3);
        set1.add(4);
        set1.add(6);
        set1.add(7);
        set2.add(1);
        set2.add(3);
        set2.add(4);
        set2.add(2);
        set2.add(5);
        set1.retainAll(set2);
        List<String> list11 = new ArrayList<>();
        System.out.println(set1);
        System.out.println(set2);
        System.out.println(String.join(",", list11));
    }
    // 求交集
    public static void test7() {
        List<Integer> list1 = Arrays.asList(1,2,3,4,5);
        List<Integer> list2 = Arrays.asList(1,2,7,4,6);
        List<Integer> intersection = (List<Integer>) CollectionUtil.intersection(list1, list2);
        System.out.println(list1);
        System.out.println(list2);
        System.out.println(intersection);

    }
    public static void test8() {
        Person p1 = new Person("1","aa");
        Person p2 = new Person("2","bb");
        Person p3 = new Person("3","cc");
        Person p4 = new Person("3","cgs");
        List<Person> personList = new ArrayList<>();
        personList.add(p1);
        personList.add(p2);
        personList.add(p3);
        Person person1 = personList.stream().filter(person -> person.getPersonId().equals("4")).findFirst().orElse(null);
        if (person1 != null) {
            System.out.println(person1.getLabel());
        }else {
            System.out.println("null");
        }

    }
    @Data
    @AllArgsConstructor
    static class Person {
        private String personId;
        private String label;
    }
    public static void main(String[] args) {
        test6();

    }
}


