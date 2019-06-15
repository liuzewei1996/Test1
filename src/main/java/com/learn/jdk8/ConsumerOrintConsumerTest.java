package com.learn.jdk8;

import org.junit.Test;

import java.util.function.Consumer;
import java.util.function.IntConsumer;

/**
 * @ProjectName: JDK
 * @Package: com.learn.jdk8
 * @ClassName: ConsumerOrintConsumerTest
 * @Author: liuze
 * @Description: ${description}
 * @Date: 2019/5/22 21:02
 * @Version: 1.0
 */
public class ConsumerOrintConsumerTest {
    public void compute(Consumer<Integer> consumer){
        consumer.accept(100);
    }
    @Test
    public void Test(){
        Consumer<Integer> consumer = i-> System.out.println(i);
        IntConsumer intConsumer = i-> System.out.println(i);

        compute(consumer);//面向对象方式：传递对象
//        compute(intConsumer);//编译报错：intConsumer与Consumer类型无关

        compute(consumer::accept);//函数式方式：传递的是行为
        compute(intConsumer::accept);//不报错，输出正常
    }
}
