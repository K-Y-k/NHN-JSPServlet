package com.nhnacademy.day04controllerfactory.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = {ElementType.TYPE}) // 클래스를 대상으로 하려면 ElementType.TYPE으로 해야 한다.
@Retention(RetentionPolicy.RUNTIME)
public @interface StopWatch {
}