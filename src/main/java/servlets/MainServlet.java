package servlets;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.users.responses.GetResponse;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.ApiService;

import java.io.IOException;

import static data.DataForConnection.FIELDS;

@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {
    private static final String JSP_PATH = "/WEB-INF/jsp/";
    private static ApiService apiService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        apiService = new ApiService();
    }

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
            case "user":
                showUserInfo(req,resp);
                break;
            case "user_list":
                break;
        }
    }
    private void showSearchForm(String nameAttr,HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher(JSP_PATH +"search-form.jsp");
        req.setAttribute(nameAttr, true);
        dispatcher.forward(req, resp);
    }

    private void showUserInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");


        GetResponse user = null;
        try {
            user = apiService.getUserByUserID(id, FIELDS);
            RequestDispatcher dispatcher = req.getRequestDispatcher(JSP_PATH +"user-info.jsp");
            req.setAttribute("user",user);

            dispatcher.forward(req, resp);
        } catch (ClientException e) {
            throw new RuntimeException(e);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }


}
