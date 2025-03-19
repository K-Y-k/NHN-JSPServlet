package com.nhnacademy.day03frontcontroller.controller;

import com.nhnacademy.day03frontcontroller.annotation.StopWatch;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@StopWatch
public class StudentRegisterFormController implements Command{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        //todo  /student/register.jsp forward 합니다.
        return "/student/register.jsp";
    }
}
