package com.nhnacademy.student.servlet;

import com.nhnacademy.student.domain.Gender;
import com.nhnacademy.student.domain.Student;
import com.nhnacademy.student.repository.StudentRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@WebServlet(name = "studentRegisterServlet", urlPatterns = "/student/register")
public class StudentRegisterServlet extends HttpServlet {

    private StudentRepository studentRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        // todo  init studentRepository
        studentRepository = (StudentRepository) config.getServletContext().getAttribute("studentRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // register.jsp에 register 상태라고 전달
        req.setAttribute("state", "register");

        // todo  /student/register.jsp forward 합니다.
        RequestDispatcher reqd = req.getRequestDispatcher("/student/register.jsp");
        reqd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String updateId = req.getParameter("updateId");
        String updateName = req.getParameter("updateName");
        String genderParam = req.getParameter("updateGender");
        String updateAge = req.getParameter("updateAge");

        // todo null check
        if (Objects.isNull(updateId) || Objects.isNull(updateName) || Objects.isNull(genderParam) ||  Objects.isNull(updateAge)) {
            throw new IllegalArgumentException("Id, Name, Gender, Age are mandatory");
        }

        Gender gender = Gender.M;
        if (genderParam.equals("F")) {
            gender = Gender.F;
        }

        // todo save 구현
        Student student = new Student(updateId, updateName, gender, Integer.parseInt(updateAge), LocalDateTime.now());
        studentRepository.save(student);
        log.info("저장한 학생 정보 : {}", studentRepository.getStudentById(updateId).toString());

        // todo redirect /student/view?id=student1
        // redirect
        resp.sendRedirect("/student/view?id=" + updateId);
    }

}