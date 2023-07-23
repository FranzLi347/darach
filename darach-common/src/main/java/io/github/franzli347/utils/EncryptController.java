package io.github.franzli347.utils;


import java.lang.annotation.*;

/**
 * @Description: 加密
 * @Author: FranzLi
 */
@Inherited
@Documented
@Target({ElementType.FIELD, ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EncryptController{

    /**
     * 传入参数是否加密
     */
    boolean requestEncrypt() default false;

    /**
     * 返回参数是否加密
     */
    boolean responseEncrypt() default false;

}
