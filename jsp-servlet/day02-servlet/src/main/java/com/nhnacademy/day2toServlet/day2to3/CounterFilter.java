package com.nhnacademy.day2toServlet.day2to3;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;


@Slf4j
@WebFilter(filterName = "counterFilter", urlPatterns = "/*")
public class CounterFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        CounterUtils.increaseCounter(servletRequest.getServletContext());
        filterChain.doFilter(servletRequest,servletResponse);
        log.error("counter:{}",servletRequest.getServletContext().getAttribute("counter"));
    }
}
