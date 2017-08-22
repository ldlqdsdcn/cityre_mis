package cn.cityre.mis.core.web.config;

import cn.cityre.mis.core.web.security.MisRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Created by 刘大磊 on 2017/8/21 16:15.
 * spring mvc 配置入口
 */
@Configuration
@ComponentScan("cn.cityre.mis.**.controller")
@EnableWebMvc
public class WebAppConfig extends WebMvcConfigurerAdapter {
    /**
     * 设置viewResolver
     *
     * @return
     */
    @Bean
    public ViewResolver viewResolver() {
        //jsp视图解析器
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        resolver.setExposeContextBeansAsAttributes(true);
        return resolver;

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        registry.addResourceHandler("/**").addResourceLocations("/").setCachePeriod(0);
    }

    /**
     * 配置静态资源的处理
     * 将请求交由Servlet处理,不经过DispatchServlet
     */
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /**
     * shiro Realm
     *
     * @return
     */
    @Bean
    public Realm getRealm() {
        return new MisRealm();
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager(Realm realm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(realm);
        return defaultWebSecurityManager;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager)
    {
        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
        //hiro的核心安全接口,这个属性是必须的
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //身份认证失败，则跳转到登录页面的配置
        shiroFilterFactoryBean.setLoginUrl("/login");
        //权限认证失败，则跳转到指定页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/error/noprivileges");
        //无需授权就可以访问的页面
        shiroFilterFactoryBean.setFilterChainDefinitions(" /js/**=anon\n" +
                "/fonts/**=anon\n" +
                "/css/**=anon\n" +
                "/login.jsp=anon\n" +
                "/login=anon\n" +
                "/languages=anon\n" +
                "/common/addCookie=anon\n" +
                "/common/changeLanguage=anon\n" +
                "/doLogin=anon\n" +
                "/common/frameLogin.jsp=anon\n" +
                "/error/**=anon\n" +
                "/**=authc");

        return shiroFilterFactoryBean;
    }
}
