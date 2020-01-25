package ru.javawebinar.basejava.web;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.Storage;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String htmlStart = "<html><head></head><body>\n";
        String htmlEnd = "</body></html>";
        List<Resume> resumeList = storage.getAllSorted();
        String table = "<table border=\"1\"><tbody>";
        for (
                int i = 0; i < resumeList.size(); i++) {
            Resume resume = resumeList.get(i);
            table = table + "<tr>\n" +
                    "<td>" + (i + 1) + "</td>\n" +
                    "<td>" + resume.getFullName() + "</td>\n" +
                    "<td>" + resume.getUuid() + "</td>\n" +
                    "</tr>\n";
        }
        table = htmlStart + table + "</tbody></table>" + htmlEnd;
        response.getWriter().write(table);
    }
}
