package com.nhnacademy.day1toServelt.day1to14;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

@Slf4j
public class LoginServlet extends HttpServlet {
    private String initParamId;
    private String initParamPwd;

    @Override
    public void init(ServletConfig config) throws ServletException {
        initParamId = config.getInitParameter("id");
        initParamPwd = config.getInitParameter("pwd");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // session이 있으면 가져오고 없으면 null
        HttpSession session = req.getSession(false);

        // 세션이 없으면 login.html로 이동
        if (Objects.isNull(session) || Objects.isNull(session.getAttribute("id"))) {
            resp.sendRedirect("/day1to14/login.html");
        } else {
            // 세션이 있으면 로그인 성공이라는 텍스트와 세션에 있는 로그인한 아이디 출력
            resp.setContentType("text/html");
            resp.setCharacterEncoding("utf-8");

            try (PrintWriter out = resp.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<meta charset='utf-8'>");
                out.println("</head>");
                out.println("<body>");
                out.println("login success : id =" + session.getAttribute("id") + "<br/>");
                out.println("<a href='/logout'>logout</a>");
                out.println("</body>");
                out.println("</html>");
            }

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // parameter로 전달된 아이디, 비밀번호와
        String id = req.getParameter("id");
        String pwd = req.getParameter("pwd");

        // <init-param>에 설정된 아이디, 비밀번호 대조하여
        if (initParamId.equals(id) && initParamPwd.equals(pwd)) {
            // 맞으면
            // session이 있으면 가져오고 없으면 생성
            HttpSession session = req.getSession();

            // 세션에 로그인한 아이디를 attribute로 등록
            session.setAttribute("id",id);

            // /login 으로 이동
            resp.sendRedirect("/login");
        } else {
            // 틀리면 /login.html로 이동
            log.error("아이디/패스워드가 일치하지 않습니다.");
            resp.sendRedirect("/day1to14/login.html");
        }
    }

}