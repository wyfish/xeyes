package com.xingeyes.boot.dbaccess.jdbc.service.impl;

import com.xingeyes.boot.dbaccess.jdbc.entity.StudentEntity;
import com.xingeyes.boot.dbaccess.jdbc.service.StudentService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service("studentServices")//别名
public class StudentServiceImpl implements StudentService {

    @Resource
    private JdbcTemplate jdbcTemplate; // 何时注入的？

    //JDBC 写入数据
    @Override
    public int saveStudent() {
        //初始化属性参数
        String name = "张三";
        Integer age = 12;
        //执行写入
        int row = jdbcTemplate.update("INSERT INTO student (name,age)VALUES (?,?);", "李四", 12);
        //返回结果
        return row;
    }


    //JDBC 查询数据
    @Override
    public List<StudentEntity> queryAllStudent() {
        //SQL
        String sql = "SELECT *  FROM student WHERE is_delete=0";
        //结果
        List<StudentEntity> list = jdbcTemplate.query(sql, new RowMapper<StudentEntity>() {
            //映射每行数据
            @Override
            public StudentEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                StudentEntity stu = new StudentEntity();
                stu.setId(rs.getInt("ID"));
                stu.setAge(rs.getInt("AGE"));
                stu.setName(rs.getString("NAME"));
                stu.setAddress(rs.getString("ADDRESS"));
                return stu;
            }

        });
        //返回结果
        return list;
    }

    //JDBC 更新数据
    @Override
    public int updateStudent(StudentEntity studentEntity) {
        //SQL
        String sql = "update tudent set name=?,address=? where id=?";
        //结果
        int row = jdbcTemplate.update(sql, new PreparedStatementSetter() {
            //映射数据
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, studentEntity.getName());
                preparedStatement.setString(2, studentEntity.getAddress());
                preparedStatement.setInt(3, studentEntity.getId());
            }
        });
        //反悔结果
        return row;
    }

    //删除数据
    @Override
    public int deleteStudent(Integer id) {
        //SQL+结果
        int resRow = jdbcTemplate.update("UPDATE student SET is_delete=1 WHERE id=?", new PreparedStatementSetter() {
            //映射数据
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1, id);
            }
        });
        //返回结果
        return resRow;
    }

}
