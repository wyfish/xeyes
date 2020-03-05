package com.xingeyes.boot.security.spring.serivce.impl;

import com.xingeyes.boot.security.spring.dao.XUser;
import com.xingeyes.boot.security.spring.serivce.XUserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class XUserServiceImpl implements XUserService {

    @Override
    public XUser findByUsername(String username) {
        XUser user = new XUser();
        user.setId(1L);
        user.setUsername(username);
        String password = new BCryptPasswordEncoder().encode("123");
        user.setPassword(password);
        return user;
    }

    @Override
    public Set<String> findPermissions(String username) {
        Set<String> permissions = new HashSet<>();
        permissions.add("sys:user:view");
        permissions.add("sys:user:add");
        permissions.add("sys:user:edit");
        permissions.add("sys:user:delete");
        return permissions;
    }
}
