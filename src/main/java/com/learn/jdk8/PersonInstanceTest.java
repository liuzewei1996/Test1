package com.learn.jdk8;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @ProjectName: JDK
 * @Package: com.learn.jdk8
 * @ClassName: PersonInstanceTest
 * @Author: liuze
 * @Description: ${description}
 * @Date: 2019/5/17 16:27
 * @Version: 1.0
 */
public class PersonInstanceTest {

    @Test
    public void PersonTest1(){
        Person person1 = new Person("liu", 12);
        Person person2 = new Person("Ze", 22);
        Person person3 =  new Person("wei", 21);
        Person person4 = new Person("ze", 16);
        Person person5 = new Person("ze", 17);

        List<Person> list = Arrays.asList(person1,person2,person3,person4,person5);
        List<Person> resultByname = getPersonByUsername("ze", list);
        List<Person> resultByage = getPersonByAge(20, list);
        resultByname.forEach(person -> System.out.println(person.getAge()));
        resultByage.forEach(person -> System.out.println(person.getName()));

        List<Person> resultByage2 = getPersonByAge2(20,list,
                (age,personlist)-> personlist.stream().filter
                        (person -> person.getAge()>age).collect(Collectors.toList()));
        resultByage2.forEach(person -> System.out.println(person.getName()));

    }
//    在集合中查找特定名字的Person集合
    public List<Person> getPersonByUsername(String username, List<Person> persons){
        return persons.stream().filter(person -> person.getName().equals(username)).
                collect(Collectors.toList());
    }
//    在集合中查找大于指定age的Person集合
    public List<Person> getPersonByAge(Integer userage,List<Person> persons){
        BiFunction<Integer,List<Person>,List<Person>> biFunction =
                (age,personList)->
                      persons.stream().filter(person -> person.getAge() > age).
                             collect(Collectors.toList()) ;
        return biFunction.apply(userage, persons);
    }
//    另一种方式实现，用户使用时才传进来需要的动作，更加灵活
    public List<Person> getPersonByAge2(Integer userage,List<Person> persons,
                                        BiFunction<Integer,List<Person>,List<Person> > biFunction){
        return biFunction.apply(userage, persons);
    }

}
class Person{
    private String name;
    private int age;

    public Person(String name, int age) {
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
}
