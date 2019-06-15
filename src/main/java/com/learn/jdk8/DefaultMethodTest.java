package com.learn.jdk8;

/**
 * @ProjectName: JDK
 * @Package: com.learn.jdk8
 * @ClassName: Defult
 * @Author: liuze
 * @Description: ${description}
 * @Date: 2019/5/18 15:16
 * @Version: 1.0
 */
public class DefaultMethodTest implements MyTest1 ,MyTest2{
    @Override
    public void MyMethod() {
        System.out.println("MyMethod");
        MyTest1.super.MyMethod();
    }
    public static void main(String[] args) {
        DefaultMethodTest dTest = new DefaultMethodTest();
        dTest.MyMethod();
    }
}
interface MyTest1{
     default void MyMethod(){
        System.out.println("MyTest1");
    }
}
interface MyTest2{
    default void MyMethod(){
        System.out.println("MyTest2");
    }
}
