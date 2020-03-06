package com.xingeyes.boot.security.spring.interceptor.filter;


import com.xingeyes.boot.security.spring.util.XSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录认证检查过滤器
 */
public class XJwtAuthenticationFilter extends BasicAuthenticationFilter {

    @Autowired
    public XJwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 获取token, 并检查登录状态
        XSecurityUtil.checkAuthentication(request);
        chain.doFilter(request, response);
    }
}
