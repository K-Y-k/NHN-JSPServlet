package com.nhnacademy.day04reflection.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 의존성을 주입해주기 위한 @Autowired annotation 정의
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Autowired {
}