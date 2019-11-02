package cn.huanji.domain;

import lombok.Data;

@Data
public class Student {
    private String name;
    private String sex;
    private String address;

    public Student(String name,String sex,String address){
        this.name = name;
        this.sex = sex;
        this.address = address;
    }
}
