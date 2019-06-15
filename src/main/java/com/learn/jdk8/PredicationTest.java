package com.learn.jdk8;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * @ProjectName: JDK
 * @Package: com.learn.jdk8
 * @ClassName: PredicationTest
 * @Author: liuze
 * @Description: ${description}
 * @Date: 2019/5/17 18:36
 * @Version: 1.0
 */
public class PredicationTest {
    @Test
    public void predicateTest() {
        Predicate<String> predicate = s -> s.length() > 5;
        System.out.println(predicate.test("liuzwe"));//true
    }

    @Test
    public void predicationTest2() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        conditionFilter(list, value -> value % 2 == 0);//打印偶数
        conditionFilter(list, value -> value % 2 != 0);//打印奇数
        conditionFilter(list, value -> value > 5);
        System.out.println("--------------------------");
        conditionFilter(list, value -> true);

        System.out.println("--------------------------");
        conditionFilter2(list, value -> value % 2 == 0, value -> value > 5);

        //Predicate中的静态方法：static <T> Predicate<T> isEqual(Object targetRef)：判断两个参数是否相等
        System.out.println(Predicate.isEqual("test").test("test"));
    }

    public void conditionFilter(List<Integer> list, Predicate<Integer> predicate) {
        for (Integer integer : list) {
            if (predicate.test(integer))
                System.out.println(integer);
        }
    }
    //Predicate接口中default Predicate<T> and(Predicate<? super T> other)的使用(逻辑与)：
    // 同时满足other以及此Predicate
    // 还有or（逻辑或）,negate（非）
    public void conditionFilter2(List<Integer> list, Predicate<Integer> predicate1,
                                 Predicate<Integer> predicate2) {
        for (Integer integer : list) {
            if (predicate1.and(predicate2).test(integer)) {
                System.out.println(integer);
            }
        }
    }

}
