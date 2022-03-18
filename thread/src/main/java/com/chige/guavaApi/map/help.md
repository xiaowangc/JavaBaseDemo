Guava中封装的一些关于Map的骚操作:
## 不可变map对象 - ImmutableMap
特点：
* 可以链式编程的Map；
* 不可变的map：线程安全（在并发程序中，使用Immutable既保证线程安全性，也大大增强了并发时的效率（跟并发锁方式相比）
* 可以当作一个常量来对待，并且这个对象在以后也不会被改变；
* 将一个对象复制一份成immutable的，是一个防御性编程技术

创建不同类型的ImmutableMap对象
    
    //创建普通的ImmutableMap对象
        ImmutableMap<String, Integer> immutableMap = ImmutableMap.<String, Integer>builder()
                .put("day", 1)
                .put("word", 2)
                .put("month", 3)
                .build();

        // 不可变的bitMap
        ImmutableBiMap<String, String> immutableBiMap = ImmutableBiMap.<String, String>builder()
                .put("day", "one")
                .put("month", "two")
                .put("word", "a")
                .build();
        ImmutableBiMap<String, String> inverse = immutableBiMap.inverse();

        // 不可变的tableMap
        ImmutableTable<String, String, Integer> immutableTable = ImmutableTable.<String, String, Integer>builder()
                .put("name1", "Jan", 1)
                .put("name2", "Joken", 2)
                .build();
        System.out.println(immutableTable.row("name1"));

        // 不可变的多值 multimap
        ImmutableMultimap<String, Integer> immutableMultimap = ImmutableMultimap.<String, Integer>builder()
                .put("name", 1)
                .put("name", 2)
                .put("word", 3)
                .build();
        System.out.println(immutableMultimap.get("name"));

        // 不可变的范围rangeMap
        ImmutableRangeMap<Integer, String> immutableRangeMap = ImmutableRangeMap.<Integer, String>builder()
                .put(Range.closedOpen(0, 60), "fail")
                .put(Range.closed(60, 80), "satisfactory")
                .put(Range.openClosed(90, 100), "excellent")
                .build();
        System.out.println(immutableRangeMap.get(1));
        
        // 不可变的classToInstanceMap对象
        ImmutableClassToInstanceMap<Map> mapImmutableClassToInstanceMap = ImmutableClassToInstanceMap.<Map>builder()
                .put(Map.class, new HashMap())
                .put(TreeMap.class, new TreeMap())
                .build();
        System.out.println(mapImmutableClassToInstanceMap.getInstance(Map.class));

## Table - 双键Map
java中的Map只允许有一个key和一个value存在，但是guava中的`Table`允许`一个value存在两个key`。
Table中的两个key分别被称为`rowKey和columnKey`

    场景举例：记录员工每个月工作的天数。用java中普通的Map实现的话就需要两层嵌套：
        Map<String,Map<String,Integer>> map=new HashMap<>();
        //存放元素
        Map<String,Integer> workMap=new HashMap<>();
        workMap.put("Jan",20);
        workMap.put("Feb",28);
        map.put("Hydra",workMap);
    
        //取出元素
        Integer dayCount = map.get("Hydra").get("Jan");

双键Map的所有api方法：
(1)创建双键Map:Table<String,String,Integer> table = HashBasedTable.create();

(2)存值：put(row,column,value)

(3)取值：get(row,column)

(4)获取key或者value的集合：rowKeySet(); columnKeySet(); values();

(5)获取指定行或列的map值：row("key"); column("key");

(6)table.cellSet()

(7)转换rowKey和columnKey: Tables.transpose(table);

(8)转换成双层map: table.rowMap(); table.columnMap();

## BitMap - 双向Map
在JDK的Map中，如果想要根据value获取到对应的key,一般的常用方法就是通过for循环或者迭代器
的方式，最终都要遍历整个Map.
而guava中的BiMap提供了一种key和value双向关联的数据结构,可以通过value来获取对应的key值。

bitMap可以通过调用inverse方法进行反转，但是反转后的bitMap不是一个新的对象，它实现了视图的关联，
对反转后的BiMap执行的所有操作会作用于原先的BiMap上。
双向Map特点：(1)key和value都是唯一的；(2)反转前后的bitMap都是同一对象体

双向map的api方法:

(1)创建双向bitMap: HashBiMap<String, Integer> biMap = HashBiMap.create();

(2)普通存值：put(key,value); 
   强制存值：forcePut(key,value); 当value已存在时，新key会强制覆盖原先key；
            而同时key也已存在时，新value会覆盖原先的value;

(3)取值：get(key);

(4)反转：biMap.inverse()

(5)获取所有的key: bitMap.keySet();

(6)获取所有的values: bitMap.values();


## Multimap - 多值Map
java中的Map维护的是键值一对一的关系，如果要将一个键映射到多个值上，那么就只能把值的内容设为集合形式，简单实现如下:
        
    Map<String, List<Integer>> map=new HashMap<>();
    List<Integer> list=new ArrayList<>();
    list.add(1);
    list.add(2);
    map.put("day",list);        

guava中的Multimap提供了将一个键映射到多个值的形式，使用起来无需定义复杂的内层集合，可以像使用普通的Map一样使用它，定义及放入数据如下：

    Multimap<String, Integer> multimap = ArrayListMultimap.create();
    multimap.put("day",1);
    multimap.put("day",2);
    multimap.put("day",8);
    multimap.put("month",3);

multimap的常见操作方法：
(0)创建multimap：Multimap<String, Integer> multimap = ArrayListMultimap.create();
创建时指定类型：ArrayListMultimap<String, Integer> multimap = ArrayListMultimap.create();
             HashMultimap<String, String> hashMultimap = HashMultimap.create();
             TreeMultimap<Comparable, Comparable> treeMultimap = TreeMultimap.create();
(1)获取值的集合： Collection<Integer> col = multimap.get("key");
Multimap的get方法会返回一个非null的集合，但是这个集合的内容可能是空
使用get方法返回的集合也不是一个独立的对象，可以理解为集合视图的关联，对这个新集合的操作仍然会作用于原始的Multimap上

(2)转换为map: asMap(); 将Multimap转换为Map<K,Collection>的形式，同样这个Map也可以看做一个关联的视图，
             在这个Map上的操作会作用于原始的Multimap

(3)数量的问题见GuavaMultimap.java

## RangeMap - 范围Map
先举例：实现根据分数对考试成绩进行分类

    public static String getRank(int score){
        if (0<=score && score<60)
            return "fail";
        else if (60<=score && score<=90)
            return "satisfactory";
        else if (90<score && score<=100)
            return "excellent";
        return null;
    }

上面的代码出现了大量的if-else来对范围区间的分数进行判断，扩展性
和可读行都很差，不符合开闭原则。
guava中的RangeMap描述了一种从区间到特定值的映射关系，让我们能够以更为优雅的方法来书写代码。
下面用RangeMap改造上面的代码并进行测试：

    RangeMap<Integer, String> rangeMap = TreeRangeMap.create();
    rangeMap.put(Range.closedOpen(0,60),"fail");
    rangeMap.put(Range.closed(60,90),"satisfactory");
    rangeMap.put(Range.openClosed(90,100),"excellent");
    
    System.out.println(rangeMap.get(59));
    System.out.println(rangeMap.get(60));
    System.out.println(rangeMap.get(90));
    System.out.println(rangeMap.get(91));

## ClassToInstanceMap - 实例Map
ClassToInstanceMap是一个比较特殊的Map，它的键是Class，而值是这个Class对应的实例对象
举例：

    ClassToInstanceMap<Object> instanceMap = MutableClassToInstanceMap.create();
    User user=new User("Hydra",18);
    Dept dept=new Dept("develop",200);

    //使用putInstance方法存入对象：
    instanceMap.putInstance(User.class,user);
    instanceMap.putInstance(Dept.class,dept);   
    
    // 取出对象
    User user1 = instanceMap.getInstance(User.class);
    boolean flag =  user1 == user; //ture

上面使用guava的ClassToInstanceMap实现了缓存对象的操作，可是普通的Map<Class, Object>同样也能达到效果呀，
两者有什么不一样的地方吗？
首先，比较明显的是，在定义的时候就设置了存储的类型，在取出对象时省去了复杂的强制类型转换，避免了手动进行类型转换的错误。
其次，查看ClassToInstanceMap的源码：
    
    public interface ClassToInstanceMap<B> extends Map<Class<? extends B>, B>{...}
可以发现，ClassToInstanceMap接口的定义，其中是带有范型的，也就是说在一开始定义的时候就限制了存储的类型。
举例：
    
    // 定义时设置了存储的是Map类型或者Map的子类
    ClassToInstanceMap<Map> instanceMap = MutableClassToInstanceMap.create();
    HashMap<String, Object> hashMap = new HashMap<>();
    TreeMap<String, Object> treeMap = new TreeMap<>();
    ArrayList<Object> list = new ArrayList<>();
    
    instanceMap.putInstance(HashMap.class,hashMap);
    instanceMap.putInstance(TreeMap.class,treeMap);
    // 如果往instanceMap中存储list类型则会编译出错

总结：
适合使用场景：主要是缓存对象，又不想做复杂的类型校验时，可以使用ClassToInstanceMap