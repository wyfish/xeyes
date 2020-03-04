package com.xingeyes.boot.dbaccess.jdbc.controller;

import com.xingeyes.boot.dbaccess.jdbc.XJDBCUtil;
import org.aspectj.weaver.ast.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class XJDBCController {

    @Autowired
    private XJDBCUtil xjdbcUtil;

    @GetMapping(value="/jdbc_tests/{id}")
    public Test testList(@PathVariable("id") Integer id){
        return xjdbcUtil.getTest(id);
    }

    @PostMapping(value="/jdbc_tests")
    public void testAdd(@RequestParam("id")Integer id, @RequestParam("age")Integer age, @RequestParam("name")String name){
        xjdbcUtil.createTest(id,age,name);
    }

    @PutMapping(value="/jdbc_tests/{id}")
    public void testUpdate(@PathVariable("id") Integer id,@RequestParam("age") Integer age,@RequestParam("name")String name){
        xjdbcUtil.updateTest(id,age,name);
    }

    @DeleteMapping(value="/jdbc_tests/{id}")
    public void testDelete(@PathVariable("id") Integer id){
        xjdbcUtil.deleteTest(id);
    }

}
