package com.nhnacademy.day04controllerfactory.controller;

import com.nhnacademy.day04controllerfactory.annotation.StopWatch;
import com.nhnacademy.day04controllerfactory.domain.Student;
import com.nhnacademy.day04controllerfactory.factory.RequestMapping;
import com.nhnacademy.day04controllerfactory.repository.StudentRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@StopWatch
@RequestMapping(value = "/student/view.do", method = RequestMapping.Method.GET)
public class StudentViewController implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        StudentRepository studentRepository = (StudentRepository) req.getServletContext().getAttribute("studentRepository");

        String id = req.getParameter("id");
        log.info("view get method parmeter - id={}", id);

        // todo id null check
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("ID is Mandatory");
        }

        // todo student 조회
        Student findStudent = studentRepository.getStudentById(id);
        req.setAttribute("student", findStudent);

        // todo /student/view.jsp <-- forward
        req.setAttribute("update_link", "update.do?id=" + id);
        return "/student/view.jsp";
    }
}