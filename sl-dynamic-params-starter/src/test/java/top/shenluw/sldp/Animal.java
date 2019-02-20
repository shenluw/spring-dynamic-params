package top.shenluw.sldp;

import top.shenluw.sldp.annotation.Sldp;

/**
 * @author Shenluw
 * 创建日期：2019/2/20 12:03
 */
@Sldp
public class Animal {
    private String name;
    private int age;

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

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
