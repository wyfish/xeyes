package com.xingeyes.boot.dbaccess.jpa.dao;

import com.xingeyes.boot.dbaccess.jpa.entity.SysUser;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * 继承Repository接口，方法名称命名查询
 *  Repository 提供了方法名称命名查询方式
 *  Repository 提供了基于@Query注解查询与更新
 */
public interface UsersRepositoryByName extends Repository<SysUser,Integer> {

    //方法名称必须要遵循驼峰式命名规则，findBy（关键字）+属性名称（首字母大写）+查询条件（首字母大写）
    List<SysUser> findByName(String name);

    List<SysUser> findByNameAndAge(String name,Integer age);

    List<SysUser> findByNameLike(String name);
}
