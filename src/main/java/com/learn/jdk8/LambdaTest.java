package com.learn.jdk8;

import org.junit.Test;

/**
 * @ProjectName: JDK
 * @Package: com.learn.jdk8
 * @ClassName: LambdaTest
 * @Author: liuze
 * @Description: ${description}
 * @Date: 2019/5/24 16:57
 * @Version: 1.0
 */
public class LambdaTest {
    //匿名内部类与Lambda表达式的不同
    Runnable r1 = ()-> System.out.println(this);
    Runnable r2 = new Runnable() {
        @Override
        public void run() {
            System.out.println(this);
        }
    };
    @Test
    public void Test1(){
        Thread t1 = new Thread(r1);
        t1.start();
        //com.learn.jdk8.LambdaTest@8b124ba
        //上面this表示当前类的对象
        System.out.println("------------");
        Thread t2 = new Thread(r2);
        t2.start();
        //com.learn.jdk8.LambdaTest$1@5523f34f 中com.learn.jdk8.LambdaTest$1表示当前对象
        //5523f34f为一个hash值；名称表示第一个匿名内部类；生成的是LambdaTest$1.class字节码文件
        //即上面this表示匿名内部类的对象：开辟了一个新的作用域
    }
}
