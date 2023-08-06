package io.github.franzli347.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Franz Li
 * 权限控制
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionLimit {

    /**
     * 登录拦截
     */
    boolean limit() default true;



    /**
     * 需要的角色
     */
    String[] needRole() default {};

}

