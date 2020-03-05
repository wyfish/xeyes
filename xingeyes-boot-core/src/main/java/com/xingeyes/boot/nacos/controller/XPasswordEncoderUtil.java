package com.xingeyes.boot.nacos.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 使用特定密码来代替Nacos中默认的nacos用户密码
 */
public class XPasswordEncoderUtil {
    public static void main(String[] args) {
        // update password
        System.out.println(new BCryptPasswordEncoder().encode("Win_dos14"));
    }
}
