package com.nhnacademy.student.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;

import java.io.IOException;

/**
 * 모든 요청의 character set을 설정하는 기능
 */
@WebFilter(
        filterName = "characterEncodingFilter",
        urlPatterns = "/*",
        initParams = {
                @WebInitParam(name = "encoding", value = "UTF-8")
        }
)
public class CharacterEncodingFilter implements Filter {
    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.encoding = filterConfig.getInitParameter("encoding");
    }

    // filterChain.doFilter(servletRequest, servletResponse)를 호출하지 않으면
    // 필터 체인에서 해당 필터 이후의 필터나 서블릿이 실행되지 않는다. 즉, 요청 처리가 중단된다.
    // 따라서, 필터에서 요청 처리 후 응답을 보내기 전에 다른 필터나 서블릿의 처리를 이어가려면
    // filterChain.doFilter()를 반드시 호출해야 한다.
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(this.encoding);
        servletResponse.setCharacterEncoding(this.encoding);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}