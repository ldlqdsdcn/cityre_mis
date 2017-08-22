package cn.cityre.mis.core.web.config;

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
        registry.addResourceHandler("/**").addResourceLocations("/") .setCachePeriod(0);
    }

    /**
     * 配置静态资源的处理
     * 将请求交由Servlet处理,不经过DispatchServlet
     */
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }


}
