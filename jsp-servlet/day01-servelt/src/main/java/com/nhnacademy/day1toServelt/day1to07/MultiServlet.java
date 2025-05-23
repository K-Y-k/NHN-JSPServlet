package com.nhnacademy.day1toServelt.day1to07;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

public class MultiServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(MultiServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // class 파라미터명을 가져와서 배열에 넣음
        String[] values = req.getParameterValues("class");

        // context-param에 설정한 url 정보를 서로 다른 Servlet 에서 조회하기
        String url = getServletContext().getInitParameter("url");


        /**
         * PrintWriter out = resp.getWriter()를 호출할 때,
         * HttpServletResponse의 기본 설정이 text/html 및 ISO-8859-1로 되어 있다.
         * 이는 대부분의 경우, HTML 기반의 웹 응답에 적합한 기본값이다.
         */
        try (PrintWriter out = resp.getWriter()){
            out.println(String.join(",", values));

            out.printf("url:%s\n", url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
