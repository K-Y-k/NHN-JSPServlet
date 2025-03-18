package com.nhnacademy.day2toServlet.day2to1;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Objects;

@WebServlet(name = "logoutServlet", urlPatterns = "/logout")
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

        // 리다이렉트 설정 클래스 생성하고 forward 작동
        RequestDispatcher rd = req.getRequestDispatcher("/day2to1/login.html");
        rd.forward(req,resp);

        // browser에 새로고침 -> request 객체가 유지됨.
        // log에 id가 출력되는것을 확인할 수 있음
    }
}