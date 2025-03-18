package com.nhnacademy.day1toServelt.day1to14;

import com.nhnacademy.day1toServelt.day1to13.CookieUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Objects;

public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // session이 있으면 가져오고 없으면 null
        HttpSession session = req.getSession(false);

        if (Objects.nonNull(session)) {
            session.invalidate();
        }

        // JSESSIONID cookie 삭제 처리
        Cookie cookie =  CookieUtils.getCookie(req,"JSESSIONID");
        if (Objects.nonNull(cookie)) {
            cookie.setValue("");
            cookie.setMaxAge(0);
            resp.addCookie(cookie);
        }

        // /login.html로 이동
        resp.sendRedirect("/day1to14/login.html");
    }
}