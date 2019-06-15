package com.learn.jvm;

import org.junit.Test;

/**
 * @ProjectName: JDK
 * @Package: com.learn.jvm
 * @ClassName: parameterTest
 * @Author: liuze
 * @Description: ${description}
 * @Date: 2019/5/25 11:49
 * @Version: 1.0
 */
public class parameterTest {
    public static void main(String[] args) {
        System.out.println("--------");
//        byte[] b = new byte[50 * 1024 * 1024];
    }
    @Test
    public void Test1(){
        //使用程序打印
        long totalMemory = Runtime.getRuntime().totalMemory();
        //返回java虚拟机中的内存总量。初始一般为电脑内存的1/64
        long maxMemory = Runtime.getRuntime().maxMemory();
        //试图使用的最大内存量。初始一般为电脑内存的1/4
        System.out.println("totalMemory(-Xms) = "+ (totalMemory/(double)1024/1024) + "MB");
        System.out.println("maxMemory(-Xmx) = "+ (maxMemory/(double)1024/1024) + "MB");

    }
}
