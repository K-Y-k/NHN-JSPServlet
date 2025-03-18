package com.nhnacademy.day2toServlet.day2to3;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.annotation.WebListener;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;


/**
 * 웹 애플리케이션 초기화 시 방문수 저장한 파일에서 counter 값을 읽어온다.
 * 읽어온 counter 값은 ServletContext 에 속성 값으로 저장
 */
@Slf4j
@WebListener
public class ServletContextListener implements jakarta.servlet.ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ServletContext servletContext = sce.getServletContext();
        String counterFileName = servletContext.getInitParameter("counterFileName");
        String counterFilePath = "/WEB-INF/classes/" + counterFileName;
        String realFilePath = servletContext.getRealPath(counterFilePath);
        log.error("path:{}",realFilePath);

        File target = new File(realFilePath);

        if (target.exists()) {
            try (FileInputStream fileInputStream = new FileInputStream(target);
                 InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
                 BufferedReader br = new BufferedReader(inputStreamReader);) {

                long c = Long.parseLong(br.readLine());
                servletContext.setAttribute("counter",c);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        log.error("init counter : {}", servletContext.getAttribute("counter"));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        String counterFileName = servletContext.getInitParameter("counterFileName");
        String counterFilePath = "/WEB-INF/classes/" + counterFileName;
        String realFilePath = servletContext.getRealPath(counterFilePath);

        try (FileOutputStream fileOutputStream = new FileOutputStream(realFilePath);
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
             BufferedWriter fw = new BufferedWriter(outputStreamWriter);){
            fw.write( String.valueOf(servletContext.getAttribute("counter")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("distory-counter:" + servletContext.getAttribute("counter"));

    }
}