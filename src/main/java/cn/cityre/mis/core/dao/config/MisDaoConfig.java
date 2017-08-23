package cn.cityre.mis.core.dao.config;

import com.jolbox.bonecp.BoneCPDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnJndi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import javax.sql.DataSource;

/**
 * Created by 刘大磊 on 2017/8/23 14:10.
 */
@Configuration
@MapperScan(basePackages = {"cn.cityre.mis.sys.dao"}, sqlSessionFactoryRef = "misSqlSessionFactory")
public class MisDaoConfig {
    private static final Logger log = Logger.getLogger(MisDaoConfig.class);
    @Autowired
    Environment env;

    /**
     * mis 库 jndi
     *
     * @return
     */
    @Bean("misDataSource")
    @ConditionalOnJndi("jdbc/mis")
    public DataSource misDataSourceJNDI() {
        log.info("Load misDataSource from JNDI");
        final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
        dsLookup.setResourceRef(true);
        DataSource dataSource = dsLookup.getDataSource("jdbc/mis");
        return dataSource;
    }

    /**
     * mis库 jdbc
     *
     * @return
     */
    @Bean(name = "misDataSource", destroyMethod = "close")
    @ConditionalOnMissingBean(value = BoneCPDataSource.class, name = "misDataSource")
    public DataSource misDataSource() {
        log.info("Create misDataSource");
        BoneCPDataSource dataSource = new BoneCPDataSource();
        dataSource.setDriverClass(env.getProperty("jdbc.mis.connection.driver_class"));
        dataSource.setJdbcUrl(env.getProperty("jdbc.mis.connection.url"));
        dataSource.setUsername(env.getProperty("jdbc.mis.connection.username"));
        dataSource.setPassword(env.getProperty("jdbc.mis.connection.password"));
        dataSource.setIdleConnectionTestPeriodInMinutes(60);
        dataSource.setIdleMaxAgeInMinutes(240);
        dataSource.setMaxConnectionsPerPartition(10);
        dataSource.setMinConnectionsPerPartition(1);
        dataSource.setPartitionCount(2);
        dataSource.setAcquireIncrement(5);
        dataSource.setStatementsCacheSize(100);
        dataSource.setInitSQL("SET NAMES 'utf8mb4'");
        return dataSource;
    }

    @Bean(name = "misSqlSessionFactory")
    public SqlSessionFactory supportSqlSessionFactory(@Qualifier("misDataSource") DataSource misDataSource)
            throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(misDataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactory.setConfigLocation(resolver.getResource("classpath:mybatis-config.xml"));
        sqlSessionFactory.setMapperLocations(resolver.getResources("classpath:cn/cityre/mis/sys/mapper/*Mapper.xml"));
        return sqlSessionFactory.getObject();
    }

}
