package com.nhnacademy.day03frontcontroller.controller;

import com.nhnacademy.day03frontcontroller.annotation.StopWatch;
import com.nhnacademy.day03frontcontroller.domain.Student;
import com.nhnacademy.day03frontcontroller.repository.StudentRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@StopWatch
public class StudentUpdateFormController implements Command{
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        StudentRepository studentRepository = (StudentRepository) req.getServletContext().getAttribute("studentRepository");

        // todo 학생조회
        Student student = studentRepository.getStudentById(req.getParameter("id"));
        req.setAttribute("student",student);

        // todo forward : /student/register.jsp
        return "/student/register.jsp";
    }
}
