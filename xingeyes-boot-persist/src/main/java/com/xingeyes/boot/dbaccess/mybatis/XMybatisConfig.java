package com.xingeyes.boot.dbaccess.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * MyBatis配置类，配置相关扫描路径，包括DAO，Model，XML映射文件的扫描
 */
@Configuration
@MapperScan("com.xingeyes.boot.dbaccess.mybatis.dao") // 扫描DAO
public class XMybatisConfig {

    @Autowired
    private DataSource dataSource;

    /**
     * 注入 SqlSessionFactory 实例，Mybatis核心类
     * 可以增加其它方法自定义SqlSessionFactory访问
     * @return
     * @throws Exception
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {

        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setTypeAliasesPackage("com.xingeyes.boot.dbaccess.mybatis.model");    // 扫描Model

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources("classpath*:**/mapping/*.xml"));    // 扫描映射文件

        return sessionFactory.getObject();
    }
}
