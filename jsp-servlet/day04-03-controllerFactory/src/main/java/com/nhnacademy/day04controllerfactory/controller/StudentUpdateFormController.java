package com.nhnacademy.day04controllerfactory.controller;

import com.nhnacademy.day04controllerfactory.annotation.StopWatch;
import com.nhnacademy.day04controllerfactory.domain.Student;
import com.nhnacademy.day04controllerfactory.factory.RequestMapping;
import com.nhnacademy.day04controllerfactory.repository.StudentRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@StopWatch
@RequestMapping(value = "/student/update.do", method = RequestMapping.Method.GET)
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
