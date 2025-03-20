package com.nhnacademy.day04controllerfactory.controller;

import com.nhnacademy.day04controllerfactory.annotation.StopWatch;
import com.nhnacademy.day04controllerfactory.domain.Gender;
import com.nhnacademy.day04controllerfactory.domain.Student;
import com.nhnacademy.day04controllerfactory.factory.RequestMapping;
import com.nhnacademy.day04controllerfactory.repository.StudentRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@StopWatch
@RequestMapping(value = "/student/register.do", method = RequestMapping.Method.POST)
public class StudentRegisterController implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        StudentRepository studentRepository = (StudentRepository) req.getServletContext().getAttribute("studentRepository");

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
        return "redirect: /student/view.do?id=" + updateId;
    }
}