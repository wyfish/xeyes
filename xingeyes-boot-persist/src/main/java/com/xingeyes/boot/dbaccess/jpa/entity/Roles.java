package com.xingeyes.boot.dbaccess.jpa.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="t_roles")
@Setter
@Getter
public class Roles {
    @Id    //主键id
    @GeneratedValue(strategy= GenerationType.IDENTITY)//主键生成策略
    @Column(name="role_id")//数据库字段名
    private Integer role_id;

    @Column(name="role_name")
    private String role_name;


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Roles{");
        sb.append("role_id=").append(role_id);
        sb.append(", role_name='").append(role_name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
