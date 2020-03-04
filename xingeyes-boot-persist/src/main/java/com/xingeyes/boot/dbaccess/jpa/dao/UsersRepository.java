package com.xingeyes.boot.dbaccess.jpa.dao;

import com.xingeyes.boot.dbaccess.jpa.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 参数一 SysUser: 当前需要映射的实体
 * 参数二 Integer: 当前映射的实体中的OID的类型
 * Note:
 *   该接口继承了PagingAndSortingRepository,
 *   而PagingAndSortingRepository继承了CrudRepository接口
 */
public interface UsersRepository extends JpaRepository<SysUser,Integer> {

}
