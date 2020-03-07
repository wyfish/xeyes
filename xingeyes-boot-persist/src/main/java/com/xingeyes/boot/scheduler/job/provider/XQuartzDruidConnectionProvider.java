package com.xingeyes.boot.scheduler.job.provider;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;

import java.sql.Connection;
import java.sql.SQLException;


/**
 * 如果嫌需要额外配置quart数据源很烦，也可以通过重写一个quartz里面的ConnectionProvider，共用你项目配置的数据库链接，
 * 这样每次更换数据库连接，就不需要额外在修改。
 * 这里实现复用Druid的数据库连接。
 */
@Setter
@Getter
public class XQuartzDruidConnectionProvider implements ConnectionProvider {

    // 数据库最大连接数
    public int maxConnection;

    // 数据库SQL查询每次连接返回执行到连接池，以确保它仍然是有效的。
    public String validationQuery;

    private boolean validateOnCheckout;

    private int idleConnectionValidationSeconds;

    public String maxCachedStatementsPerConnection;

    public static final int DEFAULT_DB_MAX_CONNECTIONS = 10;

    public static final int DEFAULT_DB_MAX_CACHED_STATEMENTS_PER_CONNECTION = 120;

    // Druid连接池
    private DruidDataSource datasource;

    private DataSourcesProperties dataSourcesProperties;

    @Override
    public Connection getConnection() throws SQLException {
        return datasource.getConnection();
    }

    @Override
    public void shutdown() throws SQLException {
        datasource.close();
    }

    @Override
    public void initialize() throws SQLException {

    }
}
