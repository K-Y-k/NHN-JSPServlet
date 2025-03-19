package com.nhnacademy.day03frontservlet.servlet;

import com.nhnacademy.day03frontservlet.domain.Gender;
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
@WebServlet(name = "studentUpdateServlet", urlPatterns = "/student/update")
public class StudentUpdateServlet extends HttpServlet {
    private StudentRepository studentRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        //todo init studentRepository
        studentRepository = (StudentRepository) config.getServletContext().getAttribute("studentRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //todo 학생조회
        Student student = studentRepository.getStudentById(req.getParameter("id"));
        req.setAttribute("student",student);

        //todo forward : /student/register.jsp
        req.setAttribute("view", "/student/register.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String updateId = req.getParameter("updateId");
        String updateName = req.getParameter("updateName");
        String genderParam = req.getParameter("updateGender");
        String updateAge = req.getParameter("updateAge");
        log.info("id : {}, name : {}, gender : {}, age : {}", updateId, updateName, genderParam, updateAge);

        // todo null check
        if ( Objects.isNull(updateId) || Objects.isNull(updateName) || Objects.isNull(genderParam) || Objects.isNull(updateAge)) {
            throw new IllegalArgumentException("Id, Name, Gender, Age are Mandatory");
        }

        Gender updateGender = Gender.M;
        if (genderParam.equals("F")) {
            updateGender = Gender.F;
        }

        // todo student 저장
        // 기존 student를 찾고 각 필드 값 설정
        Student findStudent = studentRepository.getStudentById(id);
        findStudent.setName(updateName);
        findStudent.setGender(updateGender);
        findStudent.setAge(Integer.parseInt(updateAge));

        studentRepository.update(findStudent);
        log.info("Student updated : {}", findStudent);

        // todo /student/view?id=student1 <-- redirect
        req.setAttribute("view", "redirect: /student/view.do?id=" + findStudent.getId());
    }
}
