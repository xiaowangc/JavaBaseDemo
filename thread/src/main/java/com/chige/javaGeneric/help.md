### 泛型的定义
泛型的本质是参数化类型，即给类型指定一个参数，然后在使用时再指定此参数具体的值，那样这个类型就可以在使用时决定了。
这种参数类型可以用在类、接口和方法中，分别被称为泛型类、泛型接口、泛型方法。

### 泛型字母
T 一般用于指 java bean 类型；
E 泛指集合列表中的元素类型；
K 泛指键值对中的key类型；
V 指键值对中的value类型；
? 指通配符，表示任意类型；

### 为什么需要泛型？
Java中引入泛型最主要的目的是将类型检查工作提前到编译时期，将类型强转（cast）工作交给编译器，
从而让你在编译时期就获得类型转换异常以及去掉源码中的类型强转代码。
`未使用泛型之前：类型的检查和类型的强转都必须由我们程序员自己负责`

### 泛型的三种应用方式
泛型类：只能用在成员变量上，只能使用引用类型，泛型不能用在静态属性上

    public class Generic<T> {
        public T name;
        public Generic(T param){
            name=param;
        }
        public T m(){
            return name;
        }
    }
泛型接口：只能用在抽象方法上;其定义与泛型类基本一致
    
    public interface Generator<T> {
        public T produce();
    }
泛型方法：返回值前面加上<>标签类型；
        判断一个方法是否是泛型方法关键看: 方法返回值前面有没有使用<>标记的类型，有就是，没有就不是
        `public <E> void m1(E e){ }这就是一个泛型方法`

### 泛型的使用方法
1、如何继承一个泛型类

    （1）如果不传入具体的类型，则子类也需要指定类型参数，子类为泛型类
        class Son<T> extends Generic<T>{}
    （2）如果传入具体参数，则子类不需要指定类型参数，子类为普通类
        class Son extends Generic<String>{}
2、如何实现一个泛型接口，实现方式同上
    
    （1）如果不传入具体的类型，则子类也需要指定类型参数，子类为泛型类
        class Son<T> implements GenericInterface<T> {}
    （2）如果传入具体参数，则子类不需要指定类型参数，子类为普通类
        class Son implements GenericInterface<String> {}
   
3、如何调用一个泛型方法: 同调用普通方法基本一致。
        
### 泛型中的通配符 ?
`通配符上界`：通配符上界使用<? extends T>的格式，意思是需要一个`T类型或者T类型的子类`，一般T类型都是一个具体的类型，例如下面的代码
类型 <= T
        
        public void printIntValue(List<? extends Number> list) {  
            for (Number number : list) {  
                System.out.print(number.intValue()+" ");   
            }  
        }

`通配符下界`: 通配符下界使用<? super T>的格式，意思是需要一个T类型或者T类型的父类，一般T类型都是一个具体的类型，例如下面的代码。
类型 >= T

        public void fillNumberList(List<? super Number> list) {  
            list.add(new Integer(0));  
            list.add(new Float(1.0));  
        }

### 泛型在静态方法中的问题
（1）泛型类中的静态方法和静态变量不可以使用泛型类所声明的泛型类型参数，例如下面的代码编译失败

        public class Test<T> {      
            public static T one;   //编译错误      
            public static  T show(T one){ //编译错误      
                return null;      
            }      
        }
因为静态方法和静态变量属于类所有，而泛型类中的泛型参数的实例化是在创建泛型类型对象时指定的，所以如果不创建对象，根本无法确定参数类型。
而静态泛型方法是可以使用的，如下 `public static <T> T show(T one) {return null;}`

### 泛型原理解析
#### 擦除

        public static void main(String[] args) {
                List<String> strList = new ArrayList<>();
                List<Integer> intList = new ArrayList<>();
                System.out.println(strList.getClass() == intList.getClass());
        } //输出结果为true
明明我们定义了两种不同的类型，为什么输出结果会为true呢？
答：因为，在编译期间，`所有的泛型信息都会被擦除`，List<Integer>和List<String>类型，在编译后都会变成List类型（原始类型）。
Java中的泛型基本上都是在编译器这个层次来实现的，这也是Java的泛型被称为“伪泛型”的原因。

#### 原始类型
原始类型就是泛型类型擦除了泛型信息后，在字节码中真正的类型。无论何时定义一个泛型类型，相应的原始类型都会被自动提供。
原始类型的名字就是删去类型参数后的泛型类型的类名。擦除`类型变量`，并替换为`限定类型`（T为无限定的`类型变量`，用Object替换）

泛型类型和原始类型的对比
泛型类型：

        public class GenericClass<T> {
            private T name;
            private Integer size;
        
            public void setName(T name) {
                this.name = name;
            }
            public T getName() {
                return name;
            }
        }
原始类型：

        public class GenericClass {
            private Object name;
            private Integer size;
        
            public void setName(Object name) {
                this.name = name;
            }
            public Object getName() {
                return name;
            }
        }
因为在GenericClass<T>中，T是一个无限定的类型变量，所以用Object替换。如果是GenericClass<T extends Number>，擦除后，类型变量用Number类型替换。

    ｜参考文章：https://zhuanlan.zhihu.com/p/64585072
              https://blog.csdn.net/sunxianghuang/article/details/51982979#commentsedit