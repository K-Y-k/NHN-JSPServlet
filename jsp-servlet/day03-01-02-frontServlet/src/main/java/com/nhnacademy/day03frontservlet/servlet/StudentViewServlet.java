package com.nhnacademy.day03frontservlet.servlet;

import com.nhnacademy.day03frontservlet.domain.Student;
import com.nhnacademy.day03frontservlet.repository.StudentRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@WebServlet(name = "studentViewServlet", urlPatterns = "/student/view")
public class StudentViewServlet extends HttpServlet {
    private StudentRepository studentRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        studentRepository = (StudentRepository) config.getServletContext().getAttribute("studentRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, IOException {
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
        req.setAttribute("view", "/student/view.jsp");
    }
}