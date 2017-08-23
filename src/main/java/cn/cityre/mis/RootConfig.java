package cn.cityre.mis;

import cn.cityre.mis.core.dao.config.AccountDaoConfig;
import cn.cityre.mis.core.dao.config.MisDaoConfig;
import net.sf.ehcache.CacheManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * Created by 刘大磊 on 2017/8/22 9:02.
 */
@Configuration
@Import(value = {MisDaoConfig.class, AccountDaoConfig.class})
@ComponentScan("cn.cityre.mis.**.service")
public class RootConfig {
    private static final Logger log = Logger.getLogger(RootConfig.class);

    @Value("${mailserver.host}")
    private String host;
    @Value("${mailserver.port}")
    private Integer port;
    @Value("${mailserver.username}")
    private String username;
    @Value("${mailserver.password}")
    private String password;
    @Value("${mailserver.default_encoding}")
    private String encoding;
    @Value("${mailserver.smtp.auth}")
    private boolean auth;
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
     * 发送邮件
     *
     * @return
     */
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(host);
        javaMailSender.setPort(port);
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);
        javaMailSender.setDefaultEncoding(encoding);
        javaMailSender.getJavaMailProperties().put("mail.smtp.auth", auth);
        return javaMailSender;
    }
}
