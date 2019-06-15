package com.learn.jdk8;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @ProjectName: JDK
 * @Package: com.learn.jdk8
 * @ClassName: CollectorSetTest
 * @Author: liuze
 * @Description: ${description}
 * @Date: 2019/5/20 15:08
 * @Version: 1.0
 */
public class CollectorSetTest<T> implements Collector<T,Set<T>,Set<T>> {
//简单自定义收集器的实现：将list结果收集到一个Set中

    public static void main(String[] args) {
        List<String> list = Arrays.asList("Hello","World","NiHao");
        Set<String> strings = list.stream().collect(new CollectorSetTest<String>());
        System.out.println(strings);
    }

    @Override
    public Supplier<Set<T>> supplier() {
        System.out.println("supplier invoked");
        return HashSet::new;
    }

    @Override
    public BiConsumer<Set<T>,T> accumulator() {
        System.out.println("accumulator invoked");
        //报错，要使用给定泛型的Set，不能使用具体实现类
//        return HashSet<T>::add;//不行
//        return Set<T>::add;//可以
        return (set,item)->set.add(item);//与上等价
    }

    @Override
    public BinaryOperator<Set<T>> combiner() {
        System.out.println("combiner invoked");
        return (set1,set2)->{set1.addAll(set2); return set1;};
    }

    @Override
    public Function<Set<T>,Set<T>> finisher() {
        System.out.println("finisher invoked");
//        return set->set;
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        System.out.println("characteristics invoked");
        return Collections.unmodifiableSet(EnumSet.of
                (Characteristics.IDENTITY_FINISH,Characteristics.UNORDERED));
    }
}
