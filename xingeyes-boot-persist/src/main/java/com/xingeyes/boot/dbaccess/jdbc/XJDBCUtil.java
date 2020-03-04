package com.xingeyes.boot.dbaccess.jdbc;

import org.aspectj.weaver.ast.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class XJDBCUtil {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createTest(Integer id,Integer age,String name) {
        jdbcTemplate.update("INSERT INTO test(id,age,name) VALUES (?,?,?)",id,age,name);
    }

    public void updateTest(Integer id,Integer age,String name) {
        jdbcTemplate.update("update test set age=?,name=? where id=?",age,name,id);
    }

    public void deleteTest(Integer id){
        jdbcTemplate.update("delete from test where id=?",id);
    }

    public Test getTest(Integer id){
        List<Test> list = jdbcTemplate.query("select * from test where id="+id,new BeanPropertyRowMapper(Test.class));
        if(list!=null && list.size()>0){
            Test test = list.get(0);
            return test;
        }
        return null;
    }

}
