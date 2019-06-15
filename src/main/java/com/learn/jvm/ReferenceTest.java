package com.learn.jvm;

import org.junit.Test;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.WeakHashMap;

/**
 * @ProjectName: JDK
 * @Package: com.learn.jvm
 * @ClassName: ReferenceTest
 * @Author: liuze
 * @Description: ${description}
 * @Date: 2019/5/25 20:06
 * @Version: 1.0
 */
public class ReferenceTest {
    @Test
    public void RefernceSTest(){
        //强引用
        Object object = new Object();
        Object object1 = object;
        object = null;
        System.gc();
        System.out.println(object1);//java.lang.Object@4ee285c6
    }
    @Test
    public void SoftMemoryEnoughTest(){
        //软引用
        Object object = new Object();
        SoftReference<Object> softReference = new SoftReference<>(object);
        System.out.println(object);//java.lang.Object@4ee285c6
        System.out.println(softReference.get());//java.lang.Object@4ee285c6

        object = null;
        System.gc();
        System.out.println(object);//null
        System.out.println(softReference.get());//java.lang.Object@4ee285c6
    }

    @Test
    public void SoftMemoryNotEnoughTest(){
        //JVM配置，故意产生大对象并配置小内存；
        //-Xms5m -Xmm5m -XX:PrintGCDetails
        Object object = new Object();
        SoftReference<Object> softReference = new SoftReference<>(object);
        System.out.println(object);//java.lang.Object@4ee285c6
        System.out.println(softReference.get());//java.lang.Object@4ee285c6

        object = null;

        try{
            byte[] bytes = new byte[30*1024*1024];
        }catch (Throwable e){
            e.printStackTrace();
        }finally {
            System.out.println(object);//null
            System.out.println(softReference.get());//null
        }
    }

    @Test
    public void WeakTest(){
        //弱引用
        Object object = new Object();
        WeakReference<Object> weakReference = new WeakReference<>(object);
        System.out.println(object);//java.lang.Object@4ee285c6
        System.out.println(weakReference.get());//java.lang.Object@4ee285c6

        object = null;
        System.gc();
        System.out.println(object);//null
        System.out.println(weakReference.get());//null
    }
    @Test
    public void HashMapTest(){
        HashMap<Integer,String> map = new HashMap<>();
        Integer key = new Integer(1);
        String value = "Value";
        map.put(2,"value");
        map.put(key, value);
        System.out.println(map);//{2=value, 1=Value}

        key = null;
        System.out.println(map);//{2=value, 1=Value}

        System.gc();
    }
    @Test
    public void WeakHashMapTest(){
        WeakHashMap<Integer,String> map = new WeakHashMap<>();
        Integer key = new Integer(1);
        String value = "Value";
        map.put(key, value);
        map.put(2,"value");
        System.out.println(map);//{2=value, 1=Value}

        System.out.println("----------");
        key = null;
        System.out.println(map);//{2=value, 1=Value}

        System.out.println("----------");
        System.gc();
        System.out.println(map);//{2=value}
    }

    @Test
    public void ReferenceTest() throws InterruptedException {
        Object object = new Object();
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        WeakReference<Object> weakReference = new WeakReference<>(object,referenceQueue);
        System.out.println(object);//java.lang.Object@4ee285c6
        System.out.println(weakReference.get());//java.lang.Object@4ee285c6
        System.out.println(referenceQueue.poll());//null

        System.out.println("------------");
        object = null;
        System.gc();
        Thread.sleep(500);
        System.out.println(object);//null
        System.out.println(weakReference.get());//null
        System.out.println(referenceQueue.poll());//java.lang.ref.WeakReference@621be5d1
    }

    @Test
    public void PhantomTest() throws InterruptedException {
        //虚引用
        Object object = new Object();
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        PhantomReference<Object> phantomReference = new PhantomReference<>(object,referenceQueue);
        System.out.println(object);//java.lang.Object@4ee285c6
        System.out.println(phantomReference.get());//null
        System.out.println(referenceQueue.poll());//null

        System.out.println("------------");
        object = null;
        System.gc();
        Thread.sleep(500);
        System.out.println(object);//null
        System.out.println(phantomReference.get());//null
        System.out.println(referenceQueue.poll());//java.lang.ref.WeakReference@621be5d1
    }
}
