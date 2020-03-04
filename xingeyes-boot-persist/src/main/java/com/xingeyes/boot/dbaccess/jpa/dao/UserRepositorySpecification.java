package com.xingeyes.boot.dbaccess.jpa.dao;

import com.xingeyes.boot.dbaccess.jpa.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * JpaSpecificationExecutor独立于其它Repository，能够执行多条件查询
 */
public interface UserRepositorySpecification extends JpaRepository<SysUser,Integer>, JpaSpecificationExecutor<SysUser> {

}
