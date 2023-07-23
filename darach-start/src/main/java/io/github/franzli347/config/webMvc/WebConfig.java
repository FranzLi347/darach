package io.github.franzli347.config.webMvc;

import io.github.franzli347.utils.AccessLimitInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * @author Franz
 * 设置
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    CorsProperties corsProperties;

    @Resource
    AccessLimitInterceptor accessLimitInterceptor;


    @Override
    public void addCorsMappings( CorsRegistry registry) {
        if (Boolean.FALSE.equals(corsProperties.getEnabled())) {
            return;
        }
        registry.addMapping(corsProperties.getPathPattern())
                .allowCredentials(corsProperties.getAllowCredentials())
                .allowedOriginPatterns("*")
                .allowedMethods(corsProperties.getAllowedMethods())
                .allowedHeaders(corsProperties.getAllowedHeaders())
                .exposedHeaders(corsProperties.getExposedHeaders());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accessLimitInterceptor);
    }
}
