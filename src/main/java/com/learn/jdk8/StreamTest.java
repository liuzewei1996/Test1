package com.learn.jdk8;

import org.junit.Test;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @ProjectName: JDK
 * @Package: com.learn.jdk8
 * @ClassName: StreamTest
 * @Author: liuze
 * @Description: ${description}
 * @Date: 2019/5/18 17:10
 * @Version: 1.0
 */
public class StreamTest {
    @Test
    public void TestStream(){
//        创建流的几种方式
        Stream stream1 = Stream.of("we","we","wr","zhang");

        String[] arr = new String[]{"Hello","Hi","Good","Bad"};
        Stream stream2 = Arrays.stream(arr);
        Stream stream3 = Stream.of(arr);

        List<String> list = Arrays.asList("we","we","wr","zhang");
        Stream stream4 = list.stream();
    }
    @Test
    public void UseStream(){
        IntStream.of(new int[]{5,6,7}).forEach(System.out::println);//  5 6 7
        System.out.println("--------------");
        IntStream.range(3, 5).forEach(System.out::println);//3 4
        System.out.println("--------------");
        IntStream.rangeClosed(3, 5).forEach(System.out::println);//3 4 5
    }

    @Test
    public void OperatorStream(){
//        实现：整型集合中所有元素相加再乘2得到结果
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9);
        System.out.println(list.stream().reduce(Integer::sum).map(i->i*2));
        System.out.println(list.stream().map(i->i*2).reduce(0,Integer::sum));
    }


    @Test
    public void UseStream2() {
//将流转化为数组对象
        Stream<String> stream1 = Stream.of("we", "we", "wr", "zhang");

//        String[] strings = (String[])stream1.toArray();//方式一
//        String[] strings = stream1.toArray(length->new String[length]);//方式二
        String[] strings = stream1.toArray(String[]::new);//方式三
        Arrays.asList(strings).forEach(System.out::println);
    }

    @Test
    public void UseStream3() {
//        将流转化为不同的集合
        Stream<String> stream1 = Stream.of("we", "we", "wr", "zhang");
        Stream<String> stream2 = Stream.of("we", "we", "wr", "zhang");
        Stream<String> stream3 = Stream.of("we", "we", "wr", "zhang");

        List<String> list = stream1.collect(Collectors.toList());//方式一

        List<String> list2 = stream2.collect(()->new ArrayList<String>(),
                (list1,item1)->list1.add(item1),(l1,l2)->l1.containsAll(l2));//方式二

        List<String> list3 = stream3.collect(LinkedList::new,LinkedList::add,LinkedList::addAll);//方式三

        list.forEach(System.out::println); //输出：we we wr zhang
        list2.forEach(System.out::println);//输出：we we wr zhang
        list3.forEach(System.out::println);//输出：we we wr zhang
    }

    @Test
    public void UseStream4() {
//        将流转化为不同的集合，对流元素进行操作
        Stream<String> stream1 = Stream.of("we", "we", "wr", "zhang");
        Stream<String> stream2 = Stream.of("we", "we", "wr", "zhang");
        Stream<String> stream3 = Stream.of("we", "we", "wr", "zhang");

        Set<String> set = stream1.collect(Collectors.toCollection(TreeSet::new));//Set
        set.forEach(System.out::println);//输出：we  wr  zhang

        String str = stream2.collect(Collectors.joining());//连接元素为一个字符串操作
        System.out.println(str);//输出：wewrzhang

        //LinkedList并进行操作：
        List<String> list = stream3.collect(Collectors.toCollection(LinkedList::new));
        list.stream().map(String::toUpperCase).collect(Collectors.toList()).
                forEach(System.out::println);//输出：WE WR ZHANG

        //flatMap的使用：得到 将流的每个元素按指定的函数操作后的所有元素形成的流
        Stream<List<Integer>> stream = Stream.of(Arrays.asList(1,2),
                Arrays.asList(1),Arrays.asList(8,9,10));
        stream.flatMap(list1 -> list1.stream()).map(i->i*i).forEach(System.out::println);
    }
    @Test
    public void TestComplex(){
        Stream<String> stream = Stream.generate(UUID.randomUUID()::toString);
        stream.findFirst().ifPresent(System.out::println);
        //findFirst返回流的第一个元素，且类型为Optional

        Stream.iterate(1, i->i+2).limit(5).forEach(System.out::println);
        //输出：1 3 5 7 9

        System.out.println("------------------------");
        //找出其中大于2的元素，然后将每个元素乘以2，然后忽略掉元素中的前两个元素，
        // 再取流中的前两个元素，再求总和/最小值
        Stream<Integer> stream1 = Stream.iterate(1, i->i+2).limit(5);
        Stream<Integer> stream2 = Stream.iterate(1, i->i+2).limit(5);
        Stream<Integer> stream3 = Stream.iterate(1, i->i+2).limit(5);

//        stream1.filter(i->i>2).map(i->i*2).skip(2).limit(2).reduce(Integer::sum);
//        System.out.println(stream1.filter(i->i>2).map(i->i*2).skip(2).limit(2).reduce(Integer::sum));
        System.out.println(stream1.filter(i->i>2).mapToInt(i->i*2).skip(2).limit(2).sum());//32 无则为0

        System.out.println("------------------------");
//        System.out.println(stream1.filter(i->i>200).mapToInt(i->i*2).skip(2).limit(2).min());
        stream2.filter(i->i>2).mapToInt(i->i*2).skip(2).limit(2).min().
                ifPresent(System.out::println);//14

        System.out.println("------------------------");
        IntSummaryStatistics intSummaryStatistics =
         stream3.filter(i->i>2).mapToInt(i->i*2).skip(2).limit(2).summaryStatistics();
        System.out.println(intSummaryStatistics.getMax());//18
        System.out.println(intSummaryStatistics.getAverage());//16.0
        System.out.println(intSummaryStatistics.getMin());//14
    }

    @Test
    public void TestRepeat(){
        Stream<Integer> stream = Stream.iterate(1, i->i+2).limit(5);//创建新的流
        System.out.println(stream);
        Stream<Integer> stream1 = stream.filter(i->i>2);//对流操作，创建新的流
        System.out.println(stream1);
        //对已操作过的流stream不能直接进行操作，对新的流对象stream1进行操作
        Stream<Integer> stream2 = stream1.distinct();
        System.out.println(stream2);
    }

    @Test
    public void TestRepeat2(){
        List<String> list = Arrays.asList("hello","word","hello world");
        List<String> list1 = Arrays.asList("hello","word","hello world");

        list.stream().map(i->i.substring(0,1).toUpperCase() + i.substring(1)).forEach(System.out::println);

        System.out.println("----------------");
        Stream<String> stringStream = list1.stream().map(i-> {
            String result = i.substring(0,1).toUpperCase() + i.substring(1);
            System.out.println("test");
            return result;
        });//中间操作，需要有终止操作才能倒着完成中间的操作
//        stringStream.forEach(System.out::println);

        System.out.println("----------------");
        IntStream.iterate(0, i->(i+1)%2).limit(6).distinct().forEach(System.out::println);//正常
//        IntStream.iterate(0, i->(i+1)%2).distinct().limit(6).forEach(System.out::println);//不停的运行
    }
}
