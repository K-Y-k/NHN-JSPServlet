package com.nhnacademy.day2toServlet.day2to4;



import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import java.io.File;
import java.io.IOException;

@Slf4j
@WebServlet(name = "fileDownloadServlet", urlPatterns = "/file/fileDownload")
public class FileDownloadServlet extends HttpServlet {
    private static final String UPLOAD_DIR = "/Users/KOR/IdeaProjects-Ultimate/jsp-day02/src/main/upload";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName = req.getParameter("fileName");

        if (Objects.isNull(fileName) || fileName.isEmpty()){
            resp.sendError(400,"fileName parameter is needed");
            return;
        }

        String filePath = UPLOAD_DIR + File.separator + fileName;
        Path path = Path.of(filePath);

        if (!Files.exists(path)) {
            log.error("File not found:" + fileName);
            resp.sendError(404,"File not found:" + fileName);
            return;
        }

        resp.setContentType("application/octet-stream");
        resp.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        try (InputStream is = Files.newInputStream(path);
             OutputStream os = resp.getOutputStream()){

            byte[] buffer = new byte[4096];

            int n;
            while (-1 != (n = is.read(buffer))) {
                os.write(buffer, 0, n);
            }
        } catch (IOException ex) {
            log.error("", ex);
        }
    }
}