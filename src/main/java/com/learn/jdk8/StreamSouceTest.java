package com.learn.jdk8;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @ProjectName: JDK
 * @Package: com.learn.jdk8
 * @ClassName: StreamSouceTest
 * @Author: liuze
 * @Description: ${description}
 * @Date: 2019/5/23 22:17
 * @Version: 1.0
 */
public class StreamSouceTest {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("ke","wo","li","ta");
        System.out.println(list.getClass());
        list.stream().forEach(System.out::println);
        list.stream().map(i->i).forEach(System.out::println);
        //中间操作仅仅返回了StatelessOp对象，做了一些赋值等操作，没有执行里面的实现
    }

    @Test
    public void Test1(){
        //断点Debug
        List<String> list = Arrays.asList("ke","wo","li","ta");
        Stream<String> stringStream = list.stream();
        System.out.println("111111");
        Stream<String> stringStream1 = stringStream.map(i->i+"_abc");
        System.out.println("111111");
        stringStream1.forEach(System.out::println);

        list.forEach(System.out::println);//与上一种方式截然不同

    }
}
