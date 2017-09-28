package com.lightwind.a07_quickindex;

/**
 * 功能：实体类
 * 作者：刘洋
 * 时间：2017/9/28
 */

public class Person {

    private String name;
    private String pingyin;

    public Person(String name) {
        this.name = name;
        this.pingyin = PinYinUtils.getPinYin(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPingyin() {
        return pingyin;
    }

    public void setPingyin(String pingyin) {
        this.pingyin = pingyin;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", pingyin='" + pingyin + '\'' +
                '}';
    }
}
