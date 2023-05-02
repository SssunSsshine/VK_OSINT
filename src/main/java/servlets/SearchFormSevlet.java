package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/search")
public class SearchFormSevlet extends HttpServlet {
    private static final String JSP_PATH = "/WEB-INF/jsp/";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        switch (action){
            case "search_by_id":
                showSearchForm("is_id",req,resp);
                break;
            case "search_by_fullname":
                showSearchForm("is_fullname",req,resp);
                break;
        }
    }

    private void showSearchForm(String nameAttr, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher(JSP_PATH + "search-form.jsp");
        req.setAttribute(nameAttr, true);
        dispatcher.forward(req, resp);
    }
}
