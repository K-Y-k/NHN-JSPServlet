package com.nhnacademy.day04controllerfactory.factory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * path에 실행할 command 맵핑 역할의 인터페이스
 */
@Target(value = {ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
    enum Method{
        POST, GET
    }
    Method method() default Method.GET;

    String value();
}
