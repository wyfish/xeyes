package com.xingeyes.boot.dbaccess.mybatis.service.impl;

import com.xingeyes.boot.dbaccess.mybatis.dao.SysUserMapper;
import com.xingeyes.boot.dbaccess.mybatis.model.SysUser;
import com.xingeyes.boot.dbaccess.mybatis.service.XSysUserDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class XSysUserDemoServiceImpl implements XSysUserDemoService {
    @Autowired
    private SysUserMapper sysUserMapper;


    @Override
    public List<SysUser> findAll() {
        return sysUserMapper.selectAll();
    }
}
