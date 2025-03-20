package com.nhnacademy.day04controllerfactory.controller;

import com.nhnacademy.day04controllerfactory.annotation.StopWatch;
import com.nhnacademy.day04controllerfactory.domain.Gender;
import com.nhnacademy.day04controllerfactory.domain.Student;
import com.nhnacademy.day04controllerfactory.factory.RequestMapping;
import com.nhnacademy.day04controllerfactory.repository.StudentRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@StopWatch
@RequestMapping(value = "/student/update.do", method = RequestMapping.Method.POST)
public class StudentUpdateController implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        StudentRepository studentRepository = (StudentRepository) req.getServletContext().getAttribute("studentRepository");

        String id = req.getParameter("id");
        String updateId = req.getParameter("updateId");
        String updateName = req.getParameter("updateName");
        String genderParam = req.getParameter("updateGender");
        String updateAge = req.getParameter("updateAge");
        log.info("id : {}, name : {}, gender : {}, age : {}", updateId, updateName, genderParam, updateAge);

        // todo null check
        if (Objects.isNull(updateId) || Objects.isNull(updateName) || Objects.isNull(genderParam) || Objects.isNull(updateAge)) {
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
        return "redirect: /student/view.do?id=" + id;
    }
}
