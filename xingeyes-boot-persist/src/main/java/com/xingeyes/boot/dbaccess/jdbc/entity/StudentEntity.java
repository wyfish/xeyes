package com.xingeyes.boot.dbaccess.jdbc.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StudentEntity {
    //id
    private Integer id;
    //姓名
    private String name;
    //年龄
    private Integer age;
    //性别
    private String sex;
    //住址
    private String address;
    //是否逻辑删除(0:未删除，1:已删除)
    private Integer isDelete;
}
