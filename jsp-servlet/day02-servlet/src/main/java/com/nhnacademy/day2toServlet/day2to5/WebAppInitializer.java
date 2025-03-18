package com.nhnacademy.day2toServlet.day2to5;


import jakarta.servlet.*;
import jakarta.servlet.annotation.HandlesTypes;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpSessionListener;

import java.util.Set;

@HandlesTypes(value = {
        HttpServlet.class,
        Filter.class,
        ServletContextListener.class,
        HttpSessionListener.class })
public class WebAppInitializer implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
        //servletContext.setInitParameter("url","https://nhnacademy.com");
        servletContext.setInitParameter("counterFileName","counter.dat");
    }
}
