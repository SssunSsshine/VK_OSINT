package servlets;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.users.UserFull;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.repo.ApiRepo;

import java.io.IOException;
import java.util.List;

import static data.DataForConnection.USER_FIELDS;

@WebServlet("/users-list")
public class UsersListServlet extends HttpServlet {
    private static final String JSP_PATH = "/WEB-INF/jsp/";
    private static ApiRepo apiService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        apiService = new ApiRepo();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        showUsersList(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private void showUsersList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("fullname");
        List<UserFull> users = null;
        try {
            users = apiService.getUsersByUserName(name, USER_FIELDS, 10);
            RequestDispatcher dispatcher = req.getRequestDispatcher(JSP_PATH + "users-list.jsp");
            req.setAttribute("users", users);
            dispatcher.forward(req, resp);
        } catch (ClientException | ApiException e) {
            throw new RuntimeException(e);
        }
    }
}
