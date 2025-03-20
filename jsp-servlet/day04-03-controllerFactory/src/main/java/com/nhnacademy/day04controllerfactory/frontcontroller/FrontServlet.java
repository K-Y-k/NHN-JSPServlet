package com.nhnacademy.day04controllerfactory.frontcontroller;

import com.nhnacademy.day04controllerfactory.controller.*;
import com.nhnacademy.day04controllerfactory.factory.ControllerFactory;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static com.nhnacademy.day04controllerfactory.factory.ControllerFactory.CONTROLLER_FACTORY_CLASS;
import static jakarta.servlet.RequestDispatcher.*;

@Slf4j
@WebServlet(name = "frontServlet", urlPatterns = "*.do")
public class FrontServlet extends HttpServlet {
    private static final String REDIRECT_PREFIX="redirect";
    private ControllerFactory controllerFactory;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init();

        // 각 컨트롤러를 담아놓은 ServletContext 생성
        ServletContext servletContext = config.getServletContext();

        // servletContext에서 받아온 controllerFactory를 주입
        controllerFactory = (ControllerFactory) servletContext.getAttribute(CONTROLLER_FACTORY_CLASS);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //todo 공통 처리 - 응답 content-type, character encoding 지정.
        resp.setContentType("text/html;charset=utf-8");
        resp.setCharacterEncoding("utf-8");

        try {
            // todo 실제 로직을 처리할 Command(Controller) 결정
            String method = req.getMethod();
            String servletPath = req.getServletPath();

            // 현재 url 경로에 맞는 Controller 가져옴
            Command command = (Command) controllerFactory.getBean(method, servletPath);
            String view = command.execute(req, resp);
            log.info("view : {}", view);

            // 실제 요청을 처리한 controller가 'view'라는 request 속성값으로 view를 전달해 줌.
            if (view.startsWith(REDIRECT_PREFIX)) {
                log.error("redirect-url : {}", view.substring(REDIRECT_PREFIX.length()+1));

                // todo  `redirect:`로 시작하면 redirect 처리.
                resp.sendRedirect(view.substring(REDIRECT_PREFIX.length()+1));
            } else {
                //todo redirect 아니면 JSP에게 view 처리를 위임하여 그 결과를 include시킴.
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
}