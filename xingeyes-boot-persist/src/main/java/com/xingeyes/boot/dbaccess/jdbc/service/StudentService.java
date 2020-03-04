package com.xingeyes.boot.dbaccess.jdbc.service;

import com.xingeyes.boot.dbaccess.jdbc.entity.StudentEntity;

import java.util.List;

public interface StudentService {
    //写入数据
    int saveStudent();

    //查询数据
    List<StudentEntity> queryAllStudent();

    //更新数据
    int updateStudent(StudentEntity StudentEntity);

    //删除数据
    int deleteStudent(Integer id);
}
