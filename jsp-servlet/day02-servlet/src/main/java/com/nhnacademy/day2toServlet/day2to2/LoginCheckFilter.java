package com.nhnacademy.day2toServlet.day2to2;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


/**
 * 로그인 상태 체크를 담당하는 필터
 * : 이 필터는 사용자가 로그인하지 않은 상태로 보호된 페이지에 접근하려 할 때
 *   로그인 페이지로 리다이렉트시키는 역할을 한다.
 */
@Slf4j
//@WebFilter(
//        filterName = "lgoinCheckFilter",
//        urlPatterns = "/*",
//        initParams = {
//                @WebInitParam(name = "exclude-urls",value = "/login\n" + "/logout\n" + "/day2to1/login.html\n"  )
//        }
//)
public class LoginCheckFilter implements Filter {
    private final Set<String> excludeUrls = new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String urls = filterConfig.getInitParameter("exclude-urls");
        log.error("exclude-urls:{}",urls);
        Arrays.stream(urls.split("\n"))
                .map(String::trim)
                .forEach(excludeUrls::add);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String requestUri = ((HttpServletRequest) servletRequest).getRequestURI();

        // excludeUrls에 포함되지 않는다면
        if(!excludeUrls.contains(requestUri)){
            HttpSession session = ((HttpServletRequest) servletRequest).getSession(false);

            if (Objects.isNull(session)) {
                ((HttpServletResponse) servletResponse).sendRedirect("/day2to1/login.html");
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
