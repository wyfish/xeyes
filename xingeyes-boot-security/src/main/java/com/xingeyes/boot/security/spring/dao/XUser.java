package com.xingeyes.boot.security.spring.dao;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class XUser {
    private Long id;

    private String username;

    private String password;
}
