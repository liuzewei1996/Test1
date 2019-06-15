package com.learn.jdk8;

import org.junit.Test;

import java.nio.file.OpenOption;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @ProjectName: JDK
 * @Package: com.learn.jdk8
 * @ClassName: OptionalTest
 * @Author: liuze
 * @Description: ${description}
 * @Date: 2019/5/18 9:26
 * @Version: 1.0
 */
public class OptionalTest {

    @Test
    public void Test1(){//不推荐采用这种方式
        Optional<String> optionalS = Optional.of("Hello");
        if(optionalS.isPresent()) System.out.println(optionalS.get());
    }
    @Test
    public void Test2(){//推荐
        Optional<String> optionalS = Optional.of("Hello");
        Optional<String> op = Optional.empty();
        optionalS.ifPresent(item -> System.out.println(item));
        System.out.println(op.orElse("word"));//op为空则打印传入的参数，不为空则打印op本身
    }

    @Test
    public void Test3(){
        Employee employee1 =new Employee();
        employee1.setName("Liu");
        Employee employee2 =new Employee();
        employee2.setName("Ze");

        Company company = new Company();
        company.setName("Hua Zhong");

        List<Employee> employeeList = Arrays.asList(employee1,employee2);//构造Employee集合
        company.setList(employeeList);//在company中添加集合

        List<Employee> employees = company.getList();
        Optional<Company> optional = Optional.ofNullable(company);
        System.out.println(optional.map(mycom->mycom.getList()).orElse(Collections.emptyList()));
//传入的集合若不为空，则打印此集合；传入的集合为空，则打印空集合
    }
}
//构造的company与Employee为一对多的关系
class Employee{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
class Company{
    private String name;
    private List<Employee> employees;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getList() {
        return employees;
    }

    public void setList(List<Employee> employees) {
        this.employees = employees;
    }
}
