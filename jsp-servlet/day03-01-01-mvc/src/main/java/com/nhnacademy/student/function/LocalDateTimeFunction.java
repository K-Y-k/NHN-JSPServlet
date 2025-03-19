package com.nhnacademy.student.function;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 날짜 및 시간을 특정 형식으로 포맷팅하는 기능을 제공 클래스
 */
public class LocalDateTimeFunction {
    public LocalDateTimeFunction() {
        throw new IllegalStateException("Utility class");
    }

    public static String formatDate(LocalDateTime localDateTime, String pattern){
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }
}