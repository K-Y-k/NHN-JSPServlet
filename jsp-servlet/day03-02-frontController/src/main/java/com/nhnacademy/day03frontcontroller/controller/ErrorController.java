package com.nhnacademy.day03frontcontroller.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import static jakarta.servlet.RequestDispatcher.*;

@Slf4j
public class ErrorController implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        // error 발생시 error 관련 정보는 이미 request에 담겨있다.
        req.setAttribute("status_code", req.getAttribute(ERROR_STATUS_CODE));

        // todo exception_type
        req.setAttribute("exception_type", req.getAttribute(ERROR_EXCEPTION_TYPE));

        // todo message
        req.setAttribute("message", req.getAttribute(ERROR_MESSAGE));

        // todo exception
        req.setAttribute("exception", req.getAttribute(ERROR_EXCEPTION));

        // todo request_uri
        req.setAttribute("request_uri", req.getAttribute(ERROR_REQUEST_URI));

        // todo /error.jsp forward 처리
        return "/error/error.jsp";
    }
}