package com.nhnacademy.day2toServlet.day2to4;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

@MultipartConfig(
        fileSizeThreshold   = 1024 * 1024 * 1,  // 1 MB
        maxFileSize         = 1024 * 1024 * 10, // 10 MB
        maxRequestSize      = 1024 * 1024 * 100, // 100 MB
        //location 위치는 적절히 변경합니다.
        location = "/Users/KOR/IdeaProjects-Ultimate/jsp-day02/src/main/upload"
)
@Slf4j
@WebServlet(name = "fileUploadServlet", urlPatterns = "/file/fileUpload")
public class FileUploadServlet extends HttpServlet {
    private static final String CONTENT_DISPOSITION = "Content-Disposition";
    private static final String UPLOAD_DIR = "/Users/KOR/IdeaProjects-Ultimate/jsp-day02/src/main/upload";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        for (Part part : req.getParts()) {
            String contentDisposition = part.getHeader(CONTENT_DISPOSITION);

            if (contentDisposition.contains("filename=")) {
                String fileName = extractFileName(contentDisposition);

                if (part.getSize() > 0) {
                    part.write(UPLOAD_DIR + File.separator + fileName);
                    part.delete();
                }
            } else {
                String formValue = req.getParameter(part.getName());
                log.error("{}={}", part.getName(), formValue);
            }
        }

        resp.sendRedirect("/");
    }

    private String extractFileName(String contentDisposition) {
        log.error("contentDisposition:{}",contentDisposition);

        for (String token : contentDisposition.split(";")) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=")+2, token.length()-1);
            }
        }

        return null;
    }
}