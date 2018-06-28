package com.kixeye.analytics.offertool.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

// https://docs.snowflake.net/manuals/user-guide/jdbc-configure.html
// http://www.baeldung.com/spring-boot-custom-auto-configuration
@Configuration
public class SnowflakeDataSourceConfiguration
{
    @Value("${kixeye.snowflake.username}") String username;
    @Value("${kixeye.snowflake.password}") String password;
    @Value("${kixeye.snowflake.account}") String account;
    @Value("${kixeye.snowflake.db}") String db;
    @Value("${kixeye.snowflake.schema}") String schema;
    @Value("${kixeye.snowflake.connection-string") String connString;

    public Connection getConnection() throws SQLException
    {
        Properties properties = new Properties();
        properties.put("user", this.username);
        properties.put("password", this.password);
        properties.put("account", this.account);
        properties.put("db", this.db);
        properties.put("schema", this.schema);

        return DriverManager.getConnection(this.connString, properties);
    }

    @Bean
    @ConfigurationProperties(prefix="kixeye.snowflake")
    public DataSource dataSource()
    {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("net.snowflake.client.jdbc.SnowflakeDriver");
        dataSource.setUrl(this.connString); // "jdbc:mysql://localhost:3306/myDb?createDatabaseIfNotExist=true");
        dataSource.setUsername(this.username);
        dataSource.setPassword(this.password);

        return dataSource;
    }
}
