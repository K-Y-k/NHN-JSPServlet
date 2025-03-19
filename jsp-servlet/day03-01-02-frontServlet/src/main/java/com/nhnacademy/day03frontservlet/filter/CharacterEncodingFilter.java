package com.nhnacademy.day03frontcontroller.filter;

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
        /**
         * 필터는 서블릿 실행 전,후로 진행되는 흐름이지만
         * 서블릿이 실행될 때 클라이언트에게 응답메시지가 전송되기 떄문에
         * servletResponse.setCharacterEncoding(this.encoding); 이 코드는 클라이언트에게 영향이 가므로
         * filterChain.doFilter() 위에 작성해야 한다.
         */
        servletRequest.setCharacterEncoding(this.encoding);
        servletResponse.setCharacterEncoding(this.encoding);
        filterChain.doFilter(servletRequest, servletResponse);

        /**
         * 즉, filterChain.doFilter() 밑에는 서버에서만 영향이 되는 부분의 로직만 작성할 수 있다.
         */
    }
}