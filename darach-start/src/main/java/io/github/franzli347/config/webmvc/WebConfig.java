package io.github.franzli347.config.webmvc;

import io.github.franzli347.interceptor.JwtInterceptor;
import io.github.franzli347.interceptor.PermissionInterceptor;
import io.github.franzli347.utils.AccessLimitInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * @author Franz
 * @description 设置
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Resource
    CorsProperties corsProperties;
    @Resource
    AccessLimitInterceptor accessLimitInterceptor;
    @Resource
    JwtInterceptor jwtInterceptor;
    @Resource
    PermissionInterceptor permissionInterceptor;

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
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**");
        registry.addInterceptor(permissionInterceptor)
                .addPathPatterns("/**");
    }
}
