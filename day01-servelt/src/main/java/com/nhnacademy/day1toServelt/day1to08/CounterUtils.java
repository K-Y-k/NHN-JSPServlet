package com.nhnacademy.day1toServelt.day1to08;

import jakarta.servlet.ServletContext;

import java.util.Optional;

public final class CounterUtils {
    private CounterUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static void increaseCounter(ServletContext servletContext){
        Long counter = Optional.ofNullable((Long)servletContext.getAttribute("counter")).orElse(0L);
        counter = counter+1;
        servletContext.setAttribute("counter",counter);
    }
}