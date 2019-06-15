package com.learn.jdk8;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.*;

/**
 * @ProjectName: JDK
 * @Package: com.learn.jdk8
 * @ClassName: StreamGroupingTest
 * @Author: liuze
 * @Description: ${description}
 * @Date: 2019/5/19 11:36
 * @Version: 1.0
 */
public class StreamGroupingTest {
    @Test
    public void TestGrouping(){
//        分组与分区功能的实现(可与SQL语句对比)
        SomePerson somePerson1 = new SomePerson("liuliu", 21,100 );
        SomePerson somePerson2 = new SomePerson("tian", 11,100 );
        SomePerson somePerson3 = new SomePerson("men", 11,100 );
        SomePerson somePerson4 = new SomePerson("tian", 30,110 );

        List<SomePerson> somes = Arrays.asList(somePerson1,somePerson2,somePerson3,somePerson4);
        //按姓名分
        Map<String,List<SomePerson>> mapName = somes.stream().
                collect(Collectors.groupingBy(SomePerson::getName));
        //按高度分
        Map<Integer,List<SomePerson>> mapHeigt = somes.stream().
                collect(Collectors.groupingBy(SomePerson::getHeight));
        //按高度分，且统计个数
        Map<Integer,Long> mapAndCount = somes.stream().
                collect(Collectors.groupingBy(SomePerson::getHeight, Collectors.counting()));
        //按姓名分，且统计高度相关信息
        Map<String,Double> mapNameAndCompute = somes.stream().collect(Collectors.
                groupingBy(SomePerson::getName, Collectors.averagingInt(SomePerson::getHeight)));
        //分区
        Map<Boolean,List<SomePerson>> mapTwo = somes.stream().collect(Collectors.
                partitioningBy(s->s.getAge() >= 20));

        System.out.println(mapName);
        System.out.println(mapHeigt);
        System.out.println(mapAndCount);//输出： {100=3, 110=1}
        System.out.println(mapNameAndCompute);//输出：{tian=105.0, men=100.0, liuliu=100.0}
        System.out.println(mapTwo);
        mapTwo.get(true).forEach(i-> System.out.println(i.getAge()));//输出：21 30
    }

    @Test
    public void Test2(){
        SomePerson somePerson1 = new SomePerson("liuliu", 21,100 );
        SomePerson somePerson2 = new SomePerson("tian", 11,100 );
        SomePerson somePerson3 = new SomePerson("men", 11,100 );
        SomePerson somePerson4 = new SomePerson("tian", 30,110 );

        List<SomePerson> somes = Arrays.asList(somePerson1,somePerson2,somePerson3,somePerson4);

        somes.stream().collect(Collectors.minBy(Comparator.comparingInt(SomePerson::getAge)))
                .ifPresent(System.out::println);
        System.out.println(somes.stream().collect(Collectors.averagingDouble(SomePerson::getAge)));
        System.out.println(somes.stream().collect(Collectors.summarizingDouble(SomePerson::getAge)));
        System.out.println(somes.stream().map(SomePerson::getName).
                collect(Collectors.joining(",")));
        System.out.println(somes.stream().map(SomePerson::getName).
                collect(Collectors.joining(",","<begin>","<end>")));

        //多级分组：Height和name双重分组
        Map<Integer,Map<String,List<SomePerson>>> maps = somes.stream().
                collect(Collectors.groupingBy(SomePerson::getHeight,Collectors.groupingBy(SomePerson::getName)));
        System.out.println(maps);

        //多级分区：age>20中age>30
        Map<Boolean,Map<Boolean,List<SomePerson>>> mapb = somes.stream().collect
                (Collectors.partitioningBy(s->s.getAge()>20, Collectors.partitioningBy(s->s.getHeight()>30)));
        System.out.println(mapb);

        //年龄每个分区的人的个数
        Map<Boolean,Long> mapCount = somes.stream().collect
                (Collectors.partitioningBy(s->s.getAge()>20,Collectors.counting()));
        System.out.println(mapCount);

        //按Name分组，并只取Age较小的
        Map<String,SomePerson> map2 = somes.stream().collect(groupingBy(SomePerson::getName,
                collectingAndThen(minBy(Comparator.comparing(SomePerson::getAge)),Optional::get)));
        System.out.println(map2);
    }
}
class SomePerson{
    private String name;
    private int age;
    private int height;

    public SomePerson(String name, int age, int height) {
        this.name = name;
        this.age = age;
        this.height = height;
    }

    @Override
    public String toString() {
        return "SomePerson{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", height=" + height +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
