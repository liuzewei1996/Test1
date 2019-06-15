package com.learn.jdk8;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @ProjectName: JDK
 * @Package: com.learn.jdk8
 * @ClassName: AutoCloseableTest
 * @Author: liuze
 * @Description: ${description}
 * @Date: 2019/5/21 17:47
 * @Version: 1.0
 */
public class AutoCloseableTest implements AutoCloseable{
//    测试AutoCloseable接口的用法：使用try with resources声明时 接口中的方法会被自动的调用
//    比如操作文件，IO流时都创建新的文件，IO流，使用try中创建新的对象（即 with resources），
//    然后对文件内容或IO内容进行操作时就会自动调用close方法，以关闭资源
    public void doSomething(){
        System.out.println("do some things");
    }
    public void close() throws Exception{
        System.out.println("close invoked");
    }

    public static void main(String[] args) throws Exception {
        try (AutoCloseableTest autoCloseable = new AutoCloseableTest()){
            autoCloseable.doSomething();//输出：do some things  close invoked
        }
    }
    @Test
    public void Test1(){
//        读BaseStream接口中onClose方法上面的说明文档。测试它的功能或作用：
        List<String> list = Arrays.asList("Hello","World","Hello world");
        try(Stream<String> stream = list.stream() ){
            stream.onClose(()-> {
                System.out.println("close");
                throw new NullPointerException("first exception");
//                throw  NullPointerException;
            }).onClose(()-> {
                System.out.println("close1");
                throw new NullPointerException("second exception");
//                throw  NullPointerException;
            }).forEach(System.out::println);
        }
    }
}
