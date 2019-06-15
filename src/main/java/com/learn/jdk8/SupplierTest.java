package com.learn.jdk8;

import org.junit.Test;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * @ProjectName: JDK
 * @Package: com.learn.jdk8
 * @ClassName: SupplierTest
 * @Author: liuze
 * @Description: ${description}
 * @Date: 2019/5/17 19:59
 * @Version: 1.0
 */
public class SupplierTest {

    @Test
    public void Test1(){
        Supplier<String> stringSupplier = ()->"hello world";

        System.out.println(stringSupplier);
        System.out.println(stringSupplier.get());
    }

    @Test
    public void Test2(){
        Supplier<Student> studentSupplier = ()->new Student();
        System.out.println(studentSupplier.get());

        Supplier<Student> supplier = Student::new;
        System.out.println(supplier.get().getName());
    }
}
class Student{
    private String name = "liu";
    private int age = 21;

    public Student() {
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
