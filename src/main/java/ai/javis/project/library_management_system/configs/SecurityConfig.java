package ai.javis.project.library_management_system.configs;

import ai.javis.project.library_management_system.payloads.ApiResponse;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.logging.Logger;

@Configuration
public class SecurityConfig {
    @Autowired
    private CustomAuthenticationFilter customAuthenticationFilter;
    @Bean
    public FilterRegistrationBean<CustomAuthenticationFilter> customAuthicationFilter(){
        FilterRegistrationBean<CustomAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(customAuthenticationFilter);
        registrationBean.addUrlPatterns(
                "/books/add/*",
                "/books/update/*",
                "/books/delete/*",
                "/books/lend/*",
                "/books/return/*",
                "/user/update/*",
                "/lend/get"
        );
        return registrationBean;
    }
}
