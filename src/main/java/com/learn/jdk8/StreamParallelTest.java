package com.learn.jdk8;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @ProjectName: JDK
 * @Package: com.learn.jdk8
 * @ClassName: StreamParallel
 * @Author: liuze
 * @Description: ${description}
 * @Date: 2019/5/19 10:00
 * @Version: 1.0
 */
public class StreamParallelTest {
    @Test
    public void TestParallel(){
        List<String> list = new ArrayList<>(5000000);
        for(int i=0;i<5000000;i++){
            list.add(UUID.randomUUID().toString());
        }
        System.out.println("开始排序");
        long starttime = System.nanoTime();
        list.stream().sorted().count();
        long endtime = System.nanoTime();
        long millis = TimeUnit.NANOSECONDS.toMillis(endtime-starttime);
        System.out.println("排序耗时：" + millis);

        System.out.println("开始排序");
        starttime = System.nanoTime();
        list.parallelStream().sorted().count();//并行
        endtime = System.nanoTime();
        millis = TimeUnit.NANOSECONDS.toMillis(endtime-starttime);
        System.out.println("排序耗时：" + millis);
    }

    @Test
    public void TestPrinciple(){
//      对流进行操作，一个操作容器，并对操作串行化，即对流的每个元素进行短路操作
//      实现：输出集合中第一个长度为5的字符串
        List<String> list = Arrays.asList("Hello1","world","Liu LIU");
        list.stream().mapToInt(i->{
            int length = i.length();
            System.out.println(i);
            return length;
        }).filter(i->i == 5).
                findFirst().ifPresent(System.out::println);//输出： Hello  5
    }

    @Test
    public void Test(){
//        实现：得到将集合中所有元素且无重复
        List<String> list = Arrays.asList("Hello","Hello world",
                "welcome the Hello world","Hello welcome");

        list.stream().map(item -> item.split(" ")).distinct().
                collect(Collectors.toList()).forEach(System.out::println);
        //不可取，返回四个String[]

        //需要使用flatMap：distinct去重或用Set（无重复元素）
        List<String> result = list.stream().map(item -> item.split(" ")).
                flatMap(Arrays::stream).distinct().collect(Collectors.toList());
        Set<String> strings = list.stream().map(item -> item.split(" ")).
                flatMap(Arrays::stream).collect(Collectors.toCollection(TreeSet::new));

        result.stream().forEach(System.out::println);//输出：Hello world welcome the
        System.out.println("-----------------------");
        strings.stream().forEach(System.out::println);//输出：Hello world welcome the
    }

    @Test
    public void Test1(){
//        实现：list1+list2 交叉叠加输出（不同放式和不同人打招呼）
        List<String> list1 = Arrays.asList("Hello","Hi");
        List<String> list2 = Arrays.asList("zhong hua","tian men","liu jia");
        List<String> stringList = list1.stream().flatMap(i->list2.stream().
                map(i2->i + " " + i2)).collect(Collectors.toList());
        stringList.forEach(System.out::println);
    }
}
