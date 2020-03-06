package com.xingeyes.boot.security.spring.serivce;

import com.xingeyes.boot.security.spring.dao.XUser;

import java.util.Set;

/**
 * 用户管理
 */
public interface XUserService {
    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    XUser findByUsername(String username);

    /**
     * 查找用户的菜单权限标识集合
     * @param userName
     * @return
     */
    Set<String> findPermissions(String username);
}
