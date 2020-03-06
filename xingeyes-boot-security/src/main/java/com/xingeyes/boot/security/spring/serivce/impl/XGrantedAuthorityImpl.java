package com.xingeyes.boot.security.spring.serivce.impl;

import org.springframework.security.core.GrantedAuthority;

/**
 * 权限封装
 */
public class XGrantedAuthorityImpl implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    private String authority;

    public XGrantedAuthorityImpl(String authority) {
        this.authority = authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }

}
