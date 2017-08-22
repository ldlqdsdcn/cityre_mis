package cn.cityre.mis;

import com.jolbox.bonecp.BoneCPDataSource;
import net.sf.ehcache.CacheManager;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnJndi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

/**
 * Created by 刘大磊 on 2017/8/22 9:02.
 */
@Configuration
@MapperScan(basePackages = {
        "cn.cityre.mis.sys.dao" }, sqlSessionFactoryRef = "misSqlSessionFactory")
public class RootConfig implements EnvironmentAware {
    private static final Logger logger = Logger.getLogger(RootConfig.class);
    Environment env;

    /**
     * ehcache配置
     *
     * @return
     */
    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
        return ehCacheManagerFactoryBean;
    }

    @Bean
    public EhCacheCacheManager ehCacheCacheManager(CacheManager cacheManager) {
        EhCacheCacheManager ehCacheCacheManager = new EhCacheCacheManager();
        ehCacheCacheManager.setCacheManager(cacheManager);
        ehCacheCacheManager.setTransactionAware(true);
        return ehCacheCacheManager;
    }
    @Bean(name = "misSqlSessionFactory")
    public SqlSessionFactory supportSqlSessionFactory(@Qualifier("misDataSource") DataSource supportDataSource)
            throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(supportDataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactory.setConfigLocation(resolver.getResource("classpath:mybatis-config.xml"));
        sqlSessionFactory.setMapperLocations(resolver.getResources("classpath:cn/cityre/mis/sys/mapper/*Mapper.xml"));
        return sqlSessionFactory.getObject();
    }

    @Bean(name = {"misDataSource"})
    @ConditionalOnJndi("jdbc/mis")
    public DataSource supportDataSourceJNDI() {
        logger.info("Load restDataSource from JNDI");
        final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
        dsLookup.setResourceRef(true);
        DataSource dataSource = dsLookup.getDataSource("jdbc/supporting");
        return dataSource;
    }

    @Bean(name = {"misDataSource"}, destroyMethod = "close")
    @ConditionalOnMissingBean(value = DataSource.class, name = "misDataSource")
    public DataSource supportDataSource() {
        logger.info("Create restDataSource");
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
    /**
     * 配置事务管理器
     */
    @Bean
    @Primary
    public DataSourceTransactionManager transactionManager(@Qualifier("misDataSource")DataSource dateSource) throws Exception{
        return new DataSourceTransactionManager(dateSource);
    }
    @Override
    public void setEnvironment(Environment environment) {
        this.env=environment;
    }
}
