package com.nhnacademy.day04controllerfactory.controller;

import com.nhnacademy.day04controllerfactory.annotation.StopWatch;
import com.nhnacademy.day04controllerfactory.factory.RequestMapping;
import com.nhnacademy.day04controllerfactory.repository.StudentRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@StopWatch
@RequestMapping(value = "/student/delete.do", method = RequestMapping.Method.POST)
public class StudentDeleteController implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("id");
        log.error("id:{}",id);

        StudentRepository studentRepository = (StudentRepository) req.getServletContext().getAttribute("studentRepository");
        studentRepository.deleteById(id);

        // view를 return
        return "redirect: /student/list.do";
    }
}