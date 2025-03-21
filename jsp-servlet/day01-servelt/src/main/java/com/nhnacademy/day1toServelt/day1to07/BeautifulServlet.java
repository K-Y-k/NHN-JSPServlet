package com.nhnacademy.day1toServelt.day1to07;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

public class BeautifulServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(BeautifulServlet.class.getName());

    /**
     * form 태그로 전달 받은 내용을 가공해서 출력
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        
        // 전달받은 html 파라미터명의 데이터를 받음
        String html = req.getParameter("html");

        /**
         * PrintWriter out = resp.getWriter()를 호출할 때,
         * HttpServletResponse의 기본 설정이 text/html 및 ISO-8859-1로 되어 있다.
         * 즉 여기서 나타낼 문자 형식이 달라서 응답 메시지에서 재설정한 것이다.
         */
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("utf-8");

        try (PrintWriter out = resp.getWriter()) {
            out.println(Jsoup.parse(html));
        } catch (Exception ex){
            log.info(ex.getMessage());
        }
    }
}
