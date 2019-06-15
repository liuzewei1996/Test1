package com.learn.jdk8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * @ProjectName: JDK
 * @Package: com.learn.jdk8
 * @ClassName: Test1
 * @Author: liuze
 * @Description: ${description}
 * @Date: 2019/5/16 9:27
 * @Version: 1.0
 */
public class Test1 {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8);
        for(int i = 0;i < list.size(); i++){
            System.out.println(list.get(i));
        }
        System.out.println("------------");
        for(Integer i : list){//外部迭代
            System.out.println(i);
        }
        System.out.println("------------");
        //通过Lambda表达式
        list.forEach(i -> System.out.println(i));//内部迭代
        //可以不声明类型，由编译器推断出来，（list中的每个元素为Integer类型）
        list.forEach((Integer i) -> System.out.println(i));
        System.out.println("------------");
        list.forEach(System.out::println);//通过方法引用创建函数式接口的实例
    }
}
