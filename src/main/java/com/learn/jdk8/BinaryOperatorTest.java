package com.learn.jdk8;

import org.junit.Test;

import java.util.Comparator;
import java.util.function.BinaryOperator;

/**
 * @ProjectName: JDK
 * @Package: com.learn.jdk8
 * @ClassName: BinaryOperatorTest
 * @Author: liuze
 * @Description: ${description}
 * @Date: 2019/5/17 20:58
 * @Version: 1.0
 */
public class BinaryOperatorTest {
    @Test
    public void Test1(){
        System.out.println(compute(12,1,(v1,v2) -> v1-v2));
        System.out.println(MyCompare(12,10,(v1,v2)->v2-v1));
        //比较长度
        System.out.println(MyCompareString("liu", "ze", (a,b)->a.length()-b.length()));
        //比较首字母
        System.out.println(MyCompareString("liu", "ze", (a,b)->a.charAt(0)-b.charAt(0)));
    }
    public int compute(int a, int b, BinaryOperator<Integer> binaryOperator){
        return binaryOperator.apply(a, b);
    }
    public int MyCompare(int a, int b, Comparator<Integer> comparator){
        return BinaryOperator.maxBy(comparator).apply(a, b);
    }
    public String MyCompareString(String a, String b, Comparator<String> comparator){
        return BinaryOperator.maxBy(comparator).apply(a, b);
    }
}
