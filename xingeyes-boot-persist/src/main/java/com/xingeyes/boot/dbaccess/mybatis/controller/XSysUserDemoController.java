package com.xingeyes.boot.dbaccess.mybatis.controller;

import com.xingeyes.boot.dbaccess.mybatis.service.XSysUserDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
@RequestMapping("user")
public class XSysUserDemoController {
    @Autowired
    private XSysUserDemoService xSysUserDemoService;


    @GetMapping(value="/findAll")
    public Object findAll() {
        return xSysUserDemoService.findAll();
    }
}
