package com.learn.jdk8;

/**
 * @ProjectName: JDK
 * @Package: com.learn.jdk8
 * @ClassName: Test2
 * @Author: liuze
 * @Description: ${description}
 * @Date: 2019/5/16 9:15
 * @Version: 1.0
 */
@FunctionalInterface
interface MyInterface {
    void test();
    //    void test1();
    String toString();
}
public class Test2 {
    public void myTest(MyInterface myInterface){
        System.out.println(1);
        myInterface.test();
        System.out.println(2);
    }
    public static void main(String[] args) {
        Test2 test2 = new Test2();
        test2.myTest(() -> {
            System.out.println("my test");
            }
        );
//        test2.myTest(new MyInterface() {
//            @Override
//            public void test() {
//                System.out.println("my test");
//            }
//        });

        MyInterface myInterface = ()->{
            System.out.println("Hello");
        };
        test2.myTest(myInterface);
        System.out.println(myInterface.getClass());
        System.out.println(myInterface.getClass().getSuperclass());
        System.out.println(myInterface.getClass().getInterfaces()[0]);
    }
}
