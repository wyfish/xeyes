package com.xingeyes.boot.security.spring.serivce.impl;

import com.xingeyes.boot.security.spring.dao.XJwtUserDetails;
import com.xingeyes.boot.security.spring.dao.XUser;
import com.xingeyes.boot.security.spring.serivce.XUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户登录认证信息查询
 */
public class XUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private XUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        XUser user = userService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("该用户不存在");
        }
        // 用户权限列表，根据用户拥有的权限标识与如 @PreAuthorize("hasAuthority('sys:menu:view')") 标注的接口对比，决定是否可以调用接口
        Set<String> permissions = userService.findPermissions(username);
        List<GrantedAuthority> grantedAuthorities = permissions.stream().map(XGrantedAuthorityImpl::new).collect(Collectors.toList());
        return new XJwtUserDetails(username, user.getPassword(), grantedAuthorities);
    }

}
