package com.learn.jdk8;

import org.junit.Test;

import java.util.*;
import java.util.function.Function;

/**
 * @ProjectName: JDK
 * @Package: com.learn.jdk8
 * @ClassName: Test3
 * @Author: liuze
 * @Description: ${description}
 * @Date: 2019/5/16 10:52
 * @Version: 1.0
 */
public class Test3 {
    public static void main(String[] args) {

        List<String> list = Arrays.asList("hello", "ketty", "baby");
        list.forEach(i->{
            System.out.println(i.toUpperCase());
        });

        List<String> list1 = new ArrayList<>();
        //diamond语法:ArrayList<String>()中String可以省略
        list.forEach(i -> list1.add(i.toUpperCase()));
        list1.forEach(i-> System.out.println(i));

        list.stream().map(i->i.toUpperCase()).
                forEach(i-> System.out.println(i));
        list.stream().map(String::toUpperCase).forEach(System.out::println);

    }
    @Test
    public void test1(){
        TestInterface1 i1 = ()->{};
        System.out.println(i1.getClass().getInterfaces()[0]);
        TestInterface2 i2 = ()->{};
        System.out.println(i2.getClass().getInterfaces()[0]);

        new Thread(()->{
            System.out.println("Hello World");
        }).start();
    }
    @Test
    public void test2(){
        Function<String, String> function = String::toUpperCase;
        System.out.println(function.getClass().getInterfaces()[0]);
//        如果通过类这种类型两个冒号后面引用的是一个实例方法，那么对
//        应的lambda表达式的第一个参数就是调用这个方法的对象
    }
    @Test
    public void TestStringCompare(){
        List<String> names = Arrays.asList("liu","ze","wei");
        Collections.sort(names, (o1, o2) -> o2.compareTo(o1));
        Collections.sort(names,(String o1,String o2) -> {
            return o2.compareTo(o1);
        });
        System.out.println(names);
    }
}

@FunctionalInterface
interface TestInterface1{
    void TestMethod1();
}
@FunctionalInterface
interface TestInterface2{
    void TestMethod2();
}
