package cn.cityre.mis.sys.dao.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@PropertySource("test.properties")
@EnableCaching
public class TestConfig {

    @Autowired
    Environment env;

    @Bean
    public PlatformTransactionManager restTransaction() {
        return new DataSourceTransactionManager(restDataSource());
    }


    @Bean
    public DataSource restDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("jdbc.mis.connection.driver_class"));
        dataSource.setUrl(env.getProperty("jdbc.mis.connection.url"));
        dataSource.setUsername(env.getProperty("jdbc.mis.connection.username"));
        dataSource.setPassword(env.getProperty("jdbc.mis.connection.password"));
        return dataSource;
    }
}
