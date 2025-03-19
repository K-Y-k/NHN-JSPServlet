package com.nhnacademy.student.servlet;

import com.nhnacademy.student.repository.StudentRepository;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebServlet(name = "studentDeleteServlet", urlPatterns = "/student/delete")
public class StudentDeleteServlet extends HttpServlet {

    private StudentRepository studentRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        // todo init studentRepository
        studentRepository = (StudentRepository) config.getServletContext().getAttribute("studentRepository");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, IOException {
        // todo get parameter  : id , id가 존재하지 않을경우 throw new RuntimeException("...")
        String id = req.getParameter("id");
        if (id == null) {
            throw new RuntimeException("id is Mandatory");
        }

        studentRepository.deleteById(id);

        // todo /student/list <-- redirect
        resp.sendRedirect("/student/list");


        // forward는 현재 요청되었던 데이터를 유지한다.
        // 즉, 현재 요청 데이터가 있는 상태이고 forward를 해버리면 이동하는 url의 post 매핑으로 진행된다.
        // 여기서는 해당 url의 get 매핑을 원하므로 redirect를 사용한다.
        // RequestDispatcher reqd = req.getRequestDispatcher("/student/list");
        // reqd.forward(req, resp);
    }
}