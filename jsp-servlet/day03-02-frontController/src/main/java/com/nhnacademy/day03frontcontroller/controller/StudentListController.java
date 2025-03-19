package com.nhnacademy.day03frontcontroller.controller;


import com.nhnacademy.day03frontcontroller.annotation.StopWatch;
import com.nhnacademy.day03frontcontroller.domain.Student;
import com.nhnacademy.day03frontcontroller.repository.StudentRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@StopWatch
public class StudentListController implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        long start = System.currentTimeMillis();

        StudentRepository studentRepository = (StudentRepository) req.getServletContext().getAttribute("studentRepository");

        // student list 구하기
        List<Student> studentList = studentRepository.getStudents();
        req.setAttribute("studentList", studentList);


        long end = System.currentTimeMillis();
        long result = end - start;
        log.info("result : {}", result);
        System.out.printf("result={%d}", result);

        // view를 return
        return "/student/list.jsp";
    }
}