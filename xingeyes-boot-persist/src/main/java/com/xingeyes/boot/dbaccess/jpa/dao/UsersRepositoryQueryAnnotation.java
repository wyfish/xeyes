package com.xingeyes.boot.dbaccess.jpa.dao;

import com.xingeyes.boot.dbaccess.jpa.entity.SysUser;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * 扩展Repository， 使用@Query注解
 */
public interface UsersRepositoryQueryAnnotation extends Repository<SysUser,Integer> {
    @Query("from t_user where name = ?")
    List<SysUser> queryByNameUseHQL(String name);

    @Query(value = "select * from t_user where name=?",nativeQuery = true)
    List<SysUser> queryByNameUseSQL(String name);

    @Query("update t_user set name=? where id=?")
    @Modifying
    //需要执行一个更新操作
    void updateUsersNameById(String name,Integer id);

}
