package com.learn.jvm;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @ProjectName: JDK
 * @Package: com.learn.jvm
 * @ClassName: OOMTest
 * @Author: liuze
 * @Description: ${description}
 * @Date: 2019/5/26 10:44
 * @Version: 1.0
 */
public class OOMTest {
    @Test
    public void StackOverFlowErrorTest(){
        stackover();//java.lang.StackOverflowError
    }
    private void stackover(){
        //不停的调用自己，栈的大小默认512-1024K，使其出现StackOverFlowError
        //递归调用，将栈空间称爆
        stackover();
    }
    @Test
    public void JVMHeapSpaceTest(){
        //堆区内存不足异常
        String str = "liu";
        while (true){
            str += str  + new Random().nextInt(111111)+ new Random().nextInt(2222222);
            str.intern();//不停的new对象，占空间
        }//java.lang.OutOfMemoryError: Java heap space
    }
    @Test
    public void GCOverHeadDemo(){
        //-Xms10m  -Xmx10m   -XX:+PrintGCDetails -XX:MaxDirectMemorySize=5m
        int i=0;
        List<String> list = new ArrayList<>();
        try{
            while (true){
                list.add(String.valueOf(++i).intern());
            }
        }catch (Exception e){
            System.out.println("********************i:"+i);
            e.printStackTrace();
            throw e;
        }//java.lang.OutOfMemoryError: GC overhead limit exceeded
    }
    @Test
    public void DirectBufferMemoryTest(){
        /*写NIO程序经常使用ByteBuffer来读取或者写入数据，这是一种基于通道（Channel）
        与缓冲区（Buffer）的I/O方式。它可以使用Native函数库直接分配堆外内存，然后通
        过一个存储在Java堆里面的DirectByteBuffer对象作为这块内存的引用进行操作。
        这样能在一些场景中提高性能，因为避免了在JVM堆和Native堆中来回复制数据。
        byteBuffer.allocate(capability)是分配JVM堆内存，属于GC管辖范围，由于需要拷贝所以速度相对较慢。
        byteBuffer.allocateDirect(capability)是分配OS本地内存，不属于GC管辖范围，速度相对较快。
        如果不断分配本地内存，堆内存很少使用，那么JVM就不需要执行GC，DirectByteBuffer对象们就
        不会被回收，这时候堆内存充足，但本地内存可能已经用光了，再次尝试分配本地内存时
        就会出现OutOfMemoryError
        * */
        //-Xms10m  -Xmx10m   -XX:+PrintGCDetails -XX:MaxDirectMemorySize=5m
        System.out.println("配置的maxDirectMemory：" +
                (sun.misc.VM.maxDirectMemory()/(double)1024/1024) + "MB");
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        ByteBuffer bb = ByteBuffer.allocateDirect(6*1024*1024);
    }//java.lang.OutOfMemoryError: Direct buffer memory

    @Test
    public void UnableNewNativeThreadTest(){
        //需要配置linux为非root用户模式
        for(int i = 0; ;i++){
            System.out.println("---------------i = " + i);
            new Thread(()->{
                try {
                    Thread.sleep(Integer.MAX_VALUE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            },""+i).start();
        }
    }//OutofMemeoryError: unable to create new native thread

    @Test
    public void MetaspaceOOMTest(){
        //-XX:MetaspaceSize=8m -XX:MaxMetaspaceSize=8m
        int i = 0;
        try{
            while (true){
                i++;

            }
        }catch (Throwable e){
            System.out.println("**********多少次后发生了异常：" + i);
            e.printStackTrace();
        }
    }
    static class metaTest{
    }

    //-Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseSerialGC     (DefNew + Tenured)
    //-Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParNewGC     (ParNew + Tenured)
    //-Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParallelGC    (PSYoungGen + ParOldGen)
    //-Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParallelOldGC    (PSYoungGen + ParOldGen)
    //-Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags     (PSYoungGen + ParOldGen)  (不加默认为这个)
    //-Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseConcMarkSweepGC    (Par new generation + concurrent)
    //-Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseG1GC    (PSYoungGen + ParOldGen)
}
