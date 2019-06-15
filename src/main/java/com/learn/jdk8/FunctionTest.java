package com.learn.jdk8;

import org.junit.Test;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @ProjectName: JDK
 * @Package: com.learn.jdk8
 * @ClassName: Function
 * @Author: liuze
 * @Description: ${description}
 * @Date: 2019/5/17 14:51
 * @Version: 1.0
 */
public class FunctionTest {

    @Test
    public void Test1(){
        FunctionTest functionTest = new FunctionTest();

        Function<Integer,Integer> function = value -> value*value;//也可以单独定义好
        System.out.println(functionTest.compute(1, function));

        System.out.println(functionTest.compute(1, value->5*value));
        System.out.println(functionTest.convert(2, value->value + "Hello"));
    }
    public int compute(int a, Function<Integer,Integer> function){
        int result = function.apply(a);
        return result;
    }
    public String convert(int a, Function<Integer, String> function){
        return function.apply(a);
    }

    @Test
    public void Test2(){
        System.out.println(calculate1(1, value->value*value,
                value->value+value));//结果:4
        System.out.println(calculate2(1, value->value*value,
                value->value+value));//结果:2
    }
//    先function2，再function1
    public int calculate1(int a, Function<Integer,Integer> function1,
                         Function<Integer,Integer> function2){
        return function1.compose(function2).apply(a);
//在Function接口中的default方法：
// default <V> Function<V, R> compose(Function<? super V, ? extends T> before)：
// 对Function的输入参数先应用before函数，再应用此函数
    }
//    先function1,再function2
    public int calculate2(int a, Function<Integer,Integer> function1,
                         Function<Integer,Integer> function2){
        return function1.andThen(function2).apply(a);
//在Function接口中的default方法：
// default <V> Function<V, R> andThen(Function<? super V, ? extends T> after)：
// 对Function的输入参数先应用此函数，再应用after函数
    }

    @Test
    public void TestBiFunction(){
        System.out.println(calculate3(1, 2, (v1,v2)->v1*v2));//2
        System.out.println(calculate3(1, 2, (v1,v2)->v1/v2));//0
        System.out.println(calculate3(1, 2, (v1,v2)->v1+v2));//3
        System.out.println(calculate3(1, 2, (v1,v2)->v1-v2));//-1
        System.out.println(calculate4(1, 2, (v1,v2)->v1+v2,v -> v*v ));//9
    }
    public int calculate3(int a, int b, BiFunction<Integer,Integer,Integer> biFunction){
        return biFunction.apply(a, b);//biFunction接收两个参数
    }
    public int calculate4(int a, int b, BiFunction<Integer,Integer,Integer> biFunction1,
                          Function<Integer,Integer> function2){
        return biFunction1.andThen(function2).apply(a, b);
    }

}
