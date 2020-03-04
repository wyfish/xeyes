package com.xingeyes.boot.dbaccess.jpa.dao;

import com.xingeyes.boot.dbaccess.jpa.entity.SysUser;
import org.springframework.data.repository.CrudRepository;

/**
 * CrudRepository接口,主要是完成一些增删改查的操作。注意：CrudRepository接口继承了Repository接口
 */
public interface UsersRepositoryCrudRepository extends CrudRepository<SysUser,Integer> {

}
