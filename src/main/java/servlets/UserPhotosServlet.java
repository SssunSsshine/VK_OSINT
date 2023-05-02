package servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.ApiService;

import java.io.IOException;

@WebServlet("/photos")
public class UserPhotosServlet extends HttpServlet {

    private static final String JSP_PATH = "/WEB-INF/jsp/";
    private static ApiService apiService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        apiService = new ApiService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
