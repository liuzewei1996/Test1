package com.learn.jdk8;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @ProjectName: JDK
 * @Package: com.learn.jdk8
 * @ClassName: MethodReferenceTest
 * @Author: liuze
 * @Description: ${description}
 * @Date: 2019/5/18 11:09
 * @Version: 1.0
 */
public class MethodReferenceTest {

    @Test
    public void TestLambda(){
        Teacher teacher1 = new Teacher("liu", 12);
        Teacher teacher2 = new Teacher("ze", 55);
        Teacher teacher3 = new Teacher("tian", 29);
        Teacher teacher4 = new Teacher("men", 1);
        List<Teacher> teacherList = Arrays.asList(teacher1,teacher2,teacher3,teacher4);
//使用Lambda表达式形式实现
        teacherList.sort((t1,t2)->Teacher.SortByage(t1, t2));
        teacherList.forEach(t-> System.out.println(t.getAge()));
        System.out.println("-----------------------");
        teacherList.sort((t1,t2)->Teacher.SortByname(t1, t2));
        teacherList.forEach(t-> System.out.println(t.getName()));
    }
    @Test
    public void TestMethodReference(){
//        使用方法引用：第一类：
//        类名::静态方法名
        Teacher teacher1 = new Teacher("liu", 12);
        Teacher teacher2 = new Teacher("ze", 55);
        Teacher teacher3 = new Teacher("tian", 29);
        Teacher teacher4 = new Teacher("men", 1);
        List<Teacher> teacherList = Arrays.asList(teacher1,teacher2,teacher3,teacher4);

        teacherList.sort(Teacher::SortByage);
        teacherList.forEach(t-> System.out.println(t.getAge()));
        System.out.println("-----------------------");
        teacherList.sort(Teacher::SortByname);
        teacherList.forEach(t-> System.out.println(t.getName()));
    }

    @Test
    public void TestMethodReference2(){
//        使用方法引用：第二类：
//        引用名（对象名）::实例方法名
        Teacher teacher1 = new Teacher("liu", 12);
        Teacher teacher2 = new Teacher("ze", 55);
        Teacher teacher3 = new Teacher("tian", 29);
        Teacher teacher4 = new Teacher("men", 1);
        List<Teacher> teacherList = Arrays.asList(teacher1,teacher2,teacher3,teacher4);

        TeacherCompare teacherCompare = new TeacherCompare();
        teacherList.sort(teacherCompare::SortTeacherAge);
        teacherList.forEach(t-> System.out.println(t.getAge()));
        System.out.println("-----------------------");
        teacherList.sort(teacherCompare::SortTeacherName);
        teacherList.forEach(t-> System.out.println(t.getName()));
    }

    @Test
    public void TestMethodReference3(){
//        使用方法引用：第三类：
//        类名::实例方法名
//        实例方法一定是由对象来调用的，此对象就是sort方法中接收的Lambda表达式的第一个参数
//        如果接收多个参数，则除了第一个参数，后面所有的参数都作为实例方法的参数
//        sort方法中lambda表达式为实现Comparator函数接口的int compare(T o1, T o2); 方法
        Teacher teacher1 = new Teacher("liu", 12);
        Teacher teacher2 = new Teacher("ze", 55);
        Teacher teacher3 = new Teacher("tian", 29);
        Teacher teacher4 = new Teacher("men", 1);
        List<Teacher> teacherList = Arrays.asList(teacher1,teacher2,teacher3,teacher4);

        teacherList.sort(Teacher::Sortage);
        teacherList.forEach(t-> System.out.println(t.getAge()));
        System.out.println("-----------------------");
        teacherList.sort(Teacher::Sortname);
        teacherList.forEach(t-> System.out.println(t.getName()));
    }

    @Test
    public void TestMethodReference4(){
//        使用方法引用：第四类：构造方法引用
//        类名::new
        System.out.println(getString(()->"liu"));//liutest
        System.out.println(getString(String::new));//test
        System.out.println(getString2("ze",String::new));//ze 注(构造方法一定会返回当前类的实例)
    }

    public String getString(Supplier<String> supplier){
        return supplier.get() + "test";
    }
    public String getString2(String str, Function<String,String> function){
        return function.apply(str);
    }
}
class Teacher{
    private String name;
    private int age;

    public Teacher(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
//定义这两个静态方法是有意为之的，是为了这个示例设计的。
// 平时使用时这样写没有意义（与当前类完全无关）。
//静态方法（实现比较器）以传入 sort(Comparator<? super E> c)方法作为参数
    public static int SortByname(Teacher st1, Teacher st2){
        return st1.getName().compareTo(st2.getName());
    }
    public static int SortByage(Teacher st1 , Teacher st2){
        return st1.getAge() - st2.getAge();
    }
//以下两种才有意义
    public int Sortname(Teacher st){
        return this.getName().compareTo(st.getName());
    }
    public int Sortage(Teacher st){
        return this.getAge() - st.getAge();
    }

}
class TeacherCompare{
//实例方法（实现比较器）以传入 sort(Comparator<? super E> c)方法作为参数
    public int SortTeacherName(Teacher t1, Teacher t2){
        return t1.getName().compareTo(t2.getName());
    }
    public int SortTeacherAge(Teacher t1, Teacher t2){
        return t1.getAge()- t2.getAge();
    }
}
