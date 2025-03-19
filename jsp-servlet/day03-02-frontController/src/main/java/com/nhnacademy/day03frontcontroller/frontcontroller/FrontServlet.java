package com.nhnacademy.day03frontcontroller.frontcontroller;

import com.nhnacademy.day03frontcontroller.annotation.StopWatchControllerProxy;
import com.nhnacademy.day03frontcontroller.controller.*;
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
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, IOException {

        //todo 공통 처리 - 응답 content-type, character encoding 지정.
        resp.setContentType("text/html;charset=utf-8");
        resp.setCharacterEncoding("utf-8");

        try {
            // todo 실제 로직을 처리할 Command(Controller) 결정
            // 실제 요청을 처리한 controller가 'view'라는 request 속성값으로 view를 전달해 줌.
            Command command = resolveCommand(req.getServletPath(), req.getMethod());

            /**
             * Command의 execute() 메소드에 실행 전,후로 시간 측정 방식
             *
             * 문제점
             * - 시간 측정은 성능이 떨어지는 부분만 측정하는데 여기서는 전체를 측정 해버린다.
             * - 다른 관심사의 코드가 들어간다.
             */
            //long start = System.currentTimeMillis();
            //String view = command.execute(req, resp);
            //long end = System.currentTimeMillis();
            //long result = end - start;
            //log.info("result : {}", result);

            /**
             * 어노테이션과 프록시 패턴 적용 방식 = AOP 프로그래밍
             * 1.관심사를 분리함
             * 2.중복코드 해결
             */
            /**
             * 현재 문제점
             * - 여러 요청이 있을 때 지속되는 StopWatchControllerProxy 생성으로 메모리 문제
             * 
             * 해결법 -> ControllerFactory에서 등록하는 과정에서 StopWatchControllerProxy를 적용하기
             */
            Command stopWatchControllerProxy = new StopWatchControllerProxy(command);
            String view = stopWatchControllerProxy.execute(req, resp);
            log.info("view : {}", view);


            if (view.startsWith(REDIRECT_PREFIX)) {
                log.error("redirect-url : {}", view.substring(REDIRECT_PREFIX.length()+1));

                // todo  `redirect:`로 시작하면 redirect 처리.
                resp.sendRedirect(view.substring(REDIRECT_PREFIX.length()+1));
            } else {
                //todo redirect 아니면 JSP에게 view 처리를 위임하여 그 결과를 include시킴.
                // include는 제어권을 현재 서블릿으로 유지하는데
                // forward는 제어권이 이동하는 url로 영구적으로 적용된다.
                // 즉 include는 커밋되는 것이 아니고 forward는 커밋된 것
                // 현재 여기서는 메서드의 코드가 끝이 나는 부분이므로 forward로 해도된다.
                RequestDispatcher rd = req.getRequestDispatcher(view);
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

    private Command resolveCommand(String servletPath, String method){
        //todo resolveCommand 수정 http-method를 고려
        Command command = null;

        /**
         * 문제점
         * 1. 분기 처리 확장 문제
         * 2. 여러 요청이 있을 때 지속되는 컨트롤러 생성으로 메모리 문제
         *
         * 해결법 -> 처음에 실행할 때 해당 컨트롤러 구현체를 등록한 ControllerFactory 구조로 적용
         */
        if ("/student/list.do".equals(servletPath) && "GET".equalsIgnoreCase(method)) {
            command = new StudentListController();
        } else if ("/student/view.do".equals(servletPath) && "GET".equalsIgnoreCase(method)) {
            command = new StudentViewController();
        } else if ("/student/delete.do".equals(servletPath) && "POST".equalsIgnoreCase(method)) {
            command = new StudentDeleteController();
        } else if ("/student/update.do".equals(servletPath) && "GET".equalsIgnoreCase(method)) {
            command = new StudentUpdateFormController();
        } else if ("/student/update.do".equals(servletPath) && "POST".equalsIgnoreCase(method)) {
            command = new StudentUpdateController();
        } else if ("/student/register.do".equals(servletPath) && "GET".equalsIgnoreCase(method)) {
            command = new StudentRegisterFormController();
        } else if ("/student/register.do".equals(servletPath) && "POST".equalsIgnoreCase(method)) {
            command = new StudentRegisterController();
        } else if ("/error.do".equals(servletPath)){
            command = new ErrorController();
        }

        return command;
    }

}