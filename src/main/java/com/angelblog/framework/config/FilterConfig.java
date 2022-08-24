package com.angelblog.framework.config;

import java.util.Map;
import javax.servlet.DispatcherType;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.google.common.collect.Maps;
import com.angelblog.common.xss.XssFilter;

/**
 * Filter配置
 *
 * @author Alcedo
 */
@Configuration
public class FilterConfig
{
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean
    public FilterRegistrationBean xssFilterRegistration()
    {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new XssFilter());
        registration.addUrlPatterns("/*");
        registration.setName("xssFilter");
        registration.setOrder(Integer.MAX_VALUE);
        Map<String, String> initParameters = Maps.newHashMap();
        initParameters.put("excludes", "/admin/system/notice/*");
        registration.setInitParameters(initParameters);
        return registration;
    }
}
