package com.xingeyes.boot.dbaccess.mybatis.service;

import com.xingeyes.boot.dbaccess.mybatis.model.SysUser;

import java.util.List;

public interface XSysUserDemoService {
    /**
     * 查找所有用户
     * @return
     */
    List<SysUser> findAll();
}
