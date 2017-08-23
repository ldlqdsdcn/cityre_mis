package cn.cityre.mis;

import cn.cityre.mis.core.dao.config.MisDaoConfig;
import net.sf.ehcache.CacheManager;
import org.apache.log4j.Logger;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.sql.DataSource;

/**
 * Created by 刘大磊 on 2017/8/22 9:02.
 */
@Configuration
@Import(value = {MisDaoConfig.class})
@ComponentScan("cn.cityre.mis.**.service")
public class RootConfig implements EnvironmentAware {
    private static final Logger logger = Logger.getLogger(RootConfig.class);

    Environment env;

    /*@MapperScan(basePackages = {
        "cn.cityre.mis.sys.dao"}, sqlSessionFactoryRef = "misSqlSessionFactory")
@MapperScan(basePackages = {
        "cn.cityre.mis.sys.dao"}, sqlSessionFactoryRef = "misSqlSessionFactory")
@MapperScan(basePackages = {
        "cn.cityre.mis.sys.dao"}, sqlSessionFactoryRef = "misSqlSessionFactory")
@MapperScan(basePackages = {
        "cn.cityre.mis.sys.dao"}, sqlSessionFactoryRef = "misSqlSessionFactory")
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
    <property name="basePackage" value="cn.cityre.mis.center.*.dao"/>
    <property name="sqlSessionFactoryBeanName" value="sqlSessionFactoryCenter"/>
</bean>*/

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

    /**
     * 中心库
     *
     * @param centerDataSource
     * @return
     * @throws Exception
     */
//    @Bean(name = "centerSqlSessionFactory")
//    public SqlSessionFactory centerSqlSessionFactory(@Qualifier("centerDataSource") DataSource centerDataSource)
//            throws Exception {
//        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
//        sqlSessionFactory.setDataSource(centerDataSource);
//        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        sqlSessionFactory.setConfigLocation(resolver.getResource("classpath:mybatis-config.xml"));
//        sqlSessionFactory.setMapperLocations(resolver.getResources("classpath:cn/cityre/mis/center/mapper/*Mapper.xml"));
//        return sqlSessionFactory.getObject();
//    }

    /**
     * 中心账户库
     *
     * @param accountDataSource
     * @return
     * @throws Exception
     */
/*    @Bean(name = "accountSqlSessionFactory")
    public SqlSessionFactory accountSqlSessionFactory(@Qualifier("accountDataSource") DataSource accountDataSource)
            throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(accountDataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactory.setConfigLocation(resolver.getResource("classpath:mybatis-config.xml"));
        sqlSessionFactory.setMapperLocations(resolver.getResources("classpath:cn/cityre/mis/account/mapper*//*Mapper.xml"));
        return sqlSessionFactory.getObject();
    }*/

    /**
     * 城市中心库
     *
     * @param cityre_centerDataSource
     * @return
     * @throws Exception
     */
/*    @Bean(name = "cityre_centerSqlSessionFactory")
    public SqlSessionFactory cityre_centerSqlSessionFactory(@Qualifier("cityre_centerDataSource") DataSource cityre_centerDataSource)
            throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(cityre_centerDataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactory.setConfigLocation(resolver.getResource("classpath:mybatis-config.xml"));
        sqlSessionFactory.setMapperLocations(resolver.getResources("classpath:cn/cityre/mis/cityre_center/mapper*//*Mapper.xml"));
        return sqlSessionFactory.getObject();
    }*/

    /**
     * 中心库jndi
     *
     * @return
     */
//    @Bean(name = {"centerDataSource"})
//    @ConditionalOnJndi("jdbc/center")
//    public DataSource centerDataSourceJNDI() {
//        logger.info("Load centerDataSource from JNDI");
//        final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
//        dsLookup.setResourceRef(true);
//        DataSource dataSource = dsLookup.getDataSource("jdbc/center");
//        return dataSource;
//    }

    /**
     * 中心库 jdbc
     *
     * @return
     */
/*    @Bean(name = {"centerDataSource"}, destroyMethod = "close")
    //@ConditionalOnMissingBean(value = BoneCPDataSource.class, name = "centerDataSource")
    public DataSource supportDataSource() {
        logger.info("Create centerDataSource");
        BoneCPDataSource dataSource = new BoneCPDataSource();
        dataSource.setDriverClass(env.getProperty("jdbc.center.connection.driver_class"));
        dataSource.setJdbcUrl(env.getProperty("jdbc.center.connection.url"));
        dataSource.setUsername(env.getProperty("jdbc.center.connection.username"));
        dataSource.setPassword(env.getProperty("jdbc.center.connection.password"));
        dataSource.setIdleConnectionTestPeriodInMinutes(60);
        dataSource.setIdleMaxAgeInMinutes(240);
        dataSource.setMaxConnectionsPerPartition(10);
        dataSource.setMinConnectionsPerPartition(1);
        dataSource.setPartitionCount(2);
        dataSource.setAcquireIncrement(5);
        dataSource.setStatementsCacheSize(100);
        dataSource.setInitSQL("SET NAMES 'utf8mb4'");
        return dataSource;
    }*/

    /**
     * 中心账户库jndi
     *
     * @return
     */
//    @Bean("accountDataSource")
//    @ConditionalOnJndi("jdbc/account")
//    public DataSource accountDataSourceJNDI() {
//        logger.info("Load accountDataSource from JNDI");
//        final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
//        dsLookup.setResourceRef(true);
//        DataSource dataSource = dsLookup.getDataSource("jdbc/account");
//        return dataSource;
//    }

    /**
     * 中心账户库 jdbc
     *
     * @return
     */
/*    @Bean(name = {"accountDataSource"}, destroyMethod = "close")
    //@ConditionalOnMissingBean(value = BoneCPDataSource.class, name = "accountDataSource")
    public DataSource accountDataSource() {
        logger.info("Create accountDataSource");
        BoneCPDataSource dataSource = new BoneCPDataSource();
        dataSource.setDriverClass(env.getProperty("jdbc.account.connection.driver_class"));
        dataSource.setJdbcUrl(env.getProperty("jdbc.account.connection.url"));
        dataSource.setUsername(env.getProperty("jdbc.account.connection.username"));
        dataSource.setPassword(env.getProperty("jdbc.account.connection.password"));
        dataSource.setIdleConnectionTestPeriodInMinutes(60);
        dataSource.setIdleMaxAgeInMinutes(240);
        dataSource.setMaxConnectionsPerPartition(10);
        dataSource.setMinConnectionsPerPartition(1);
        dataSource.setPartitionCount(2);
        dataSource.setAcquireIncrement(5);
        dataSource.setStatementsCacheSize(100);
        dataSource.setInitSQL("SET NAMES 'utf8mb4'");
        return dataSource;
    }*/

    /**
     * 城市中心库jndi
     *
     * @return
     */
//    @Bean(name = {"cityre_centerDataSource"})
//    @ConditionalOnJndi("jdbc/cityre_center")
//    public DataSource cityre_centerDataSourceJNDI() {
//
//        logger.info("Load cityre_centerDataSource from JNDI");
//        final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
//        dsLookup.setResourceRef(true);
//        DataSource dataSource = dsLookup.getDataSource("jdbc/cityre_center");
//        return dataSource;
//    }

    /**
     * 城市中心库 jdbc
     *
     * @return
     */
/*    @Bean(name = {"cityre_centerDataSource"}, destroyMethod = "close")
    //@ConditionalOnMissingBean(value = BoneCPDataSource.class, name = "cityre_centerDataSource")
    public DataSource cityre_centerDataSource() {
        logger.info("Create cityre_centerDataSource");
        BoneCPDataSource dataSource = new BoneCPDataSource();
        dataSource.setDriverClass(env.getProperty("jdbc.cityre_center.connection.driver_class"));
        dataSource.setJdbcUrl(env.getProperty("jdbc.cityre_center.connection.url"));
        dataSource.setUsername(env.getProperty("jdbc.cityre_center.connection.username"));
        dataSource.setPassword(env.getProperty("jdbc.cityre_center.connection.password"));
        dataSource.setIdleConnectionTestPeriodInMinutes(60);
        dataSource.setIdleMaxAgeInMinutes(240);
        dataSource.setMaxConnectionsPerPartition(10);
        dataSource.setMinConnectionsPerPartition(1);
        dataSource.setPartitionCount(2);
        dataSource.setAcquireIncrement(5);
        dataSource.setStatementsCacheSize(100);
        dataSource.setInitSQL("SET NAMES 'utf8mb4'");
        return dataSource;
    }*/

    /**
     * 配置事务管理器
     */
    @Bean
    public DataSourceTransactionManager transactionManager(@Qualifier("misDataSource") DataSource dateSource) throws Exception {
        return new DataSourceTransactionManager(dateSource);
    }

    /**
     * 发送邮件
     *
     * @return
     */
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(env.getProperty("mailserver.host"));
        javaMailSender.setPort(Integer.parseInt(env.getProperty("mailserver.port")));
        javaMailSender.setUsername(env.getProperty("mailserver.username"));
        javaMailSender.setPassword(env.getProperty("mailserver.password"));
        javaMailSender.setDefaultEncoding(env.getProperty("mailserver.default_encoding"));
        javaMailSender.getJavaMailProperties().put("mail.smtp.auth", Boolean.parseBoolean(env.getProperty("mailserver.smtp.auth")));
        return javaMailSender;
    }

    @Bean
    public MapperScannerConfigurer misMapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("cn.cityre.mis.sys.dao");
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("misSqlSessionFactory");
        return mapperScannerConfigurer;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment;
    }
}
