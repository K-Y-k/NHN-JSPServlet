package com.nhnacademy.day04controllerfactory.listener;


import com.nhnacademy.day04controllerfactory.domain.Gender;
import com.nhnacademy.day04controllerfactory.domain.Student;
import com.nhnacademy.day04controllerfactory.repository.JsonStudentRepository;
import com.nhnacademy.day04controllerfactory.repository.MapStudentRepository;
import com.nhnacademy.day04controllerfactory.repository.StudentRepository;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.time.LocalDateTime;
import java.util.Random;

@WebListener
public class WebApplicationListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // 서블릿 컨텍스트 생성
        ServletContext context = sce.getServletContext();
        StudentRepository studentRepository = new MapStudentRepository();

        // student 1 ~ 10 생성하기
        for (int i = 1; i <= 10; i++) {
            Random rand = new Random();

            // id
            String id = "student" + i;

            // 이름
            String name = "아카데미" + i;

            // 성별
            int randomGender = rand.nextInt(0, 2);
            Gender gender = Gender.M;
            if (randomGender == 1){
                gender = Gender.F;
            }

            // 나이 : random 처리 : 20~30
            int randomAge = rand.nextInt(20, 31);

            Student student = new Student(id, name, gender, randomAge, LocalDateTime.now());
            studentRepository.save(student);
        }

        // application scope에서 studentRepository 객체에 접근할 수 있도록 구현하기
        // 서블릿 컨텍스트에 studentRepository 등록
        context.setAttribute("studentRepository", studentRepository);
    }
}