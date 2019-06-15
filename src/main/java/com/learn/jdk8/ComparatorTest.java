package com.learn.jdk8;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @ProjectName: JDK
 * @Package: com.learn.jdk8
 * @ClassName: ComparatorTest
 * @Author: liuze
 * @Description: ${description}
 * @Date: 2019/5/19 21:45
 * @Version: 1.0
 */
public class ComparatorTest {

    @Test
    public void Test1(){
        List<String> list = Arrays.asList("nihao","hello","Hi","Women");
        List<String> list1 = Arrays.asList("nihao","hello","Hi","Women");
        List<String> list2 = Arrays.asList("nihao","hello","Hi","Women");
        List<String> list3 = Arrays.asList("nihao","hello","Hi","Women");
        List<String> list4 = Arrays.asList("nihao","hello","Hi","Women");
        List<String> list5 = Arrays.asList("nihao","hello","Hi","Women");
        List<String> list6 = Arrays.asList("nihao","hello","Hi","Women");

        //字符串长度排序
        Collections.sort(list,(l1,l2)->l1.length()-l2.length());
        Collections.sort(list1, Comparator.comparingInt(String::length).reversed());
        //此reversed()情况下Comparator.comparingInt中对参数无法进行类型推断需要; (String i)
        Collections.sort(list2, Comparator.comparingInt((String i)->i.length()).reversed());
        list3.sort(Comparator.comparingInt(String::length));

        //先字符串长度，长度相同的再字母顺序
        Collections.sort(list4,Comparator.comparingInt(String::length).
                thenComparing(String.CASE_INSENSITIVE_ORDER));
        //与上一种等价
//        Collections.sort(list4,Comparator.comparingInt(String::length).
//                thenComparing((i1,i2)->i1.toUpperCase().compareTo(i2.toUpperCase())));

        System.out.println(list);
        System.out.println(list1);
        System.out.println(list2);
        System.out.println(list3);
        System.out.println(list4);
    }
}
