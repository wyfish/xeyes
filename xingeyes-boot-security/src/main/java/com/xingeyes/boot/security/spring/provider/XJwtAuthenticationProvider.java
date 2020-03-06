package com.xingeyes.boot.security.spring.provider;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 身份验证提供者
 * 说明：
 * 登录认证是通过调用 AuthenticationManager 的 authenticate(token) 方法实现的，而 AuthenticationManager 又是通过调用
 * AuthenticationProvider 的 authenticate(Authentication authentication) 来完成认证的，所以通过定制 AuthenticationProvider
 * 也可以完成各种自定义的需求，我们这里只是简单的继承 DaoAuthenticationProvider 展示如何自定义，具体的大家可以根据各自的需求按需定制。
 */
public class XJwtAuthenticationProvider extends DaoAuthenticationProvider {

    public XJwtAuthenticationProvider(UserDetailsService userDetailsService) {
        setUserDetailsService(userDetailsService);
        setPasswordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 可以在此处覆写整个登录认证逻辑
        return super.authenticate(authentication);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        // 可以在此处覆写密码验证逻辑
        super.additionalAuthenticationChecks(userDetails, authentication);
    }

}
