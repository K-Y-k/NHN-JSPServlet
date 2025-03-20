package com.nhnacademy.day04controllerfactory.controller;

import com.nhnacademy.day04controllerfactory.annotation.StopWatch;
import com.nhnacademy.day04controllerfactory.factory.RequestMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@StopWatch
@RequestMapping(value = "/student/register.do", method = RequestMapping.Method.GET)
public class StudentRegisterFormController implements Command{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        //todo  /student/register.jsp forward 합니다.
        return "/student/register.jsp";
    }
}
