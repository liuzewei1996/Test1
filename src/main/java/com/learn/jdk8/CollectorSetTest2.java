package com.learn.jdk8;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * @ProjectName: JDK
 * @Package: com.learn.jdk8
 * @ClassName: CollectorSetTest2
 * @Author: liuze
 * @Description: ${description}
 * @Date: 2019/5/20 15:49
 * @Version: 1.0
 */
public class CollectorSetTest2<T> implements Collector<T, Set<T>,Map<T,T>> {
//    实现：输入：Set<String>   输出：Map<String,String>

    public static void main(String[] args) {
        List<String> list = Arrays.asList("Hello","World","NiHao","Beijing","Hello","a","b","c","d","e");
        Set<String> strings = new TreeSet<>();
        strings.addAll(list);
        System.out.println(strings);

        System.out.println(Runtime.getRuntime().availableProcessors());//打印线程数
//      parallelStream一般默认情况下会生成的线程数：与CPU核心数相同的线程；Intel现在进行了超核技术。
        Map<String,String> map = strings.parallelStream().collect(new CollectorSetTest2<>());//1
//        Map<String,String> map1 = strings.stream().parallel().
//        collect(new CollectorSetTest2<>());//完全等价于1
        System.out.println(map);


    }

    @Override
    public Supplier<Set<T>> supplier() {
//         * below must be equivalent:
// * <pre>{@code
// *     A a1 = supplier.get();
// *     accumulator.accept(a1, t1);
// *     accumulator.accept(a1, t2);
// *     R r1 = finisher.apply(a1);  // result without splitting
// *
// *     A a2 = supplier.get();
// *     accumulator.accept(a2, t1);
// *     A a3 = supplier.get();
// *     accumulator.accept(a3, t2);
// *     R r2 = finisher.apply(combiner.apply(a2, a3));  // result with splitting
// * } </pre>

        System.out.println("supplier invoked");
//        return HashSet::new;
        return ()->{
            System.out.println("---------");
            //测试Characteristics.CONCURRENT时，中间结果容器的数量
            return new HashSet<T>();
        };
    }

    @Override
    public BiConsumer<Set<T>, T> accumulator() {
        System.out.println("accumulator invoked");
        return (set,item)->{
            System.out.println(set + "," + Thread.currentThread().getName());//测试并行流时的输出
            set.add(item);
//            当Characteristics.CONCURRENT：可能发生异常：ConcurrentModificationException
//            it is not generally permissible for one thread to modify a Collection
//            while another thread is iterating over it
        };
    }

    @Override
    public BinaryOperator<Set<T>> combiner() {
//        并行流且在Characteristics.CONCURRENT的情况下才会被调用
        System.out.println("combiner invoked");
        return (s,s1)->{s.addAll(s1);return s;};
    }

    @Override
    public Function<Set<T>,Map<T,T>> finisher() {
        System.out.println("finisher invoked");
        return set -> {
            Map<T,T> map = new HashMap<>();
            set.stream().forEach(item->map.put(item, item));
            return map;
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        System.out.println("characteristics invoked");
//        return Collections.unmodifiableSet(EnumSet.of(Characteristics.UNORDERED,Characteristics.CONCURRENT));
        return Collections.unmodifiableSet(EnumSet.of(Characteristics.UNORDERED));
    }



}
