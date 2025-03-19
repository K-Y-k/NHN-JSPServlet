package com.nhnacademy.day03frontservlet.frontservlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static jakarta.servlet.RequestDispatcher.*;

@Slf4j
@WebServlet(name = "frontServlet", urlPatterns = "*.do")
public class FrontServlet extends HttpServlet {
    private static final String REDIRECT_PREFIX="redirect";

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // todo 공통 처리 - 응답 content-type, character encoding 지정.
        resp.setContentType("text/html;charset=utf-8");
        resp.setCharacterEncoding("utf-8");

        try {
            // 실제 요청 처리할 servlet을 결정
            String servletPath = resolveServlet(req.getServletPath());
            log.info("servletPath : {}", servletPath);
            RequestDispatcher rd = req.getRequestDispatcher(servletPath);
            /**
             * 여기서 forward를 사용하면 제어권이 이동하는 url로 넘어간다.
             * 제어권이 넘어가면 현재 url의 아래 코드는 실행되지 않고 끝나게 되어 
             * include로 현재 url로 다시 돌아오도록 한 것
             */
            rd.include(req, resp);

            // 실제 요청을 처리한 servlet이 'view'라는 request 속성값으로 view를 전달해 줌.
            String view = (String) req.getAttribute("view");
            log.info("view : {}", view);

            if (view.startsWith(REDIRECT_PREFIX)) {
                log.error("redirect-url : {}", view.substring(REDIRECT_PREFIX.length()+1));

                // todo  `redirect:`로 시작하면 redirect 처리.
                resp.sendRedirect(view.substring(REDIRECT_PREFIX.length()+1));
            } else {
                //todo redirect 아니면 JSP에게 view 처리를 위임하여 그 결과를 include시킴.
                rd = req.getRequestDispatcher(view);
                rd.include(req, resp);
            }
        } catch(Exception ex) {
            // todo 공통 error 처리 - ErrorServlet 참고해서 처리
            // error 발생시 error 관련 정보는 이미 request에 담겨있다.
            req.setAttribute("status_code", req.getAttribute(ERROR_STATUS_CODE));
            req.setAttribute("exception_type", req.getAttribute(ERROR_EXCEPTION_TYPE));
            req.setAttribute("message", req.getAttribute(ERROR_MESSAGE));
            req.setAttribute("exception", req.getAttribute(ERROR_EXCEPTION));
            req.setAttribute("request_uri", req.getAttribute(ERROR_REQUEST_URI));

            // todo /error.jsp forward 처리
            RequestDispatcher reqd = req.getRequestDispatcher("/error/error.jsp");
            reqd.forward(req, resp);
        }
    }

    private String resolveServlet(String servletPath) {
        String processingServlet = null;

        // todo 실행할 servlet 결정하기
        if ("/student/list.do".equals(servletPath)) {
            processingServlet = "/student/list";
        } else if("/student/register.do".equals(servletPath)) {
            processingServlet = "/student/register";
        } else if("/student/update.do".equals(servletPath)) {
            processingServlet = "/student/update";
        } else if("/student/view.do".equals(servletPath)) {
            processingServlet = "/student/view";
        } else if("/student/delete.do".equals(servletPath)) {
            processingServlet = "/student/delete";
        }

        return processingServlet;
    }

}