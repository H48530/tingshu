package com.util;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 */
public class DBUtil {
    private static DataSource dataSource = null;

    private static void initDataSource() {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        Properties properties = PropertiesUtil.get();

        mysqlDataSource.setServerName(properties.getProperty("mysql.host"));
        mysqlDataSource.setPort(Integer.parseInt(properties.getProperty("mysql.port")));
        mysqlDataSource.setUser(properties.getProperty("mysql.username"));
        mysqlDataSource.setPassword(properties.getProperty("mysql.password"));
        mysqlDataSource.setDatabaseName(properties.getProperty("mysql.database"));
        mysqlDataSource.setCharacterEncoding("utf8");
        mysqlDataSource.setUseSSL(false);
        mysqlDataSource.setServerTimezone("Asia/Shanghai");

        dataSource = mysqlDataSource;
    }

    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            synchronized (DBUtil.class) {
                if (dataSource == null) {
                    initDataSource();
                }
            }
        }
        return dataSource.getConnection();
    }
}
