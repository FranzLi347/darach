package io.github.franzli347.darach.utils;

import java.lang.annotation.*;

/**
 * @author Franz
 * @Description: 限流
 **/
@Inherited
@Documented
@Target({ElementType.FIELD, ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLimit {

    /**
     * 限定次数
     *
     * @return
     */
    int limit() default 5;

    /**
     * 时间段内
     *
     * @return
     */
    int sec() default 5;
}
