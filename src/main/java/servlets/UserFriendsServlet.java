package servlets;

import com.vk.api.sdk.objects.groups.responses.GetByIdObjectLegacyResponse;
import com.vk.api.sdk.objects.users.UserFull;
import com.vk.api.sdk.objects.users.responses.GetResponse;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import logic.repo.ApiRepo;
import logic.service.ApiService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/friends")
public class UserFriendsServlet extends HttpServlet {
    private static final String JSP_PATH = "/WEB-INF/jsp/";
    private static ApiService apiService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        apiService = new ApiService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        GetResponse user = (GetResponse) session.getAttribute("user");
        String id = user.getId().toString();
        Integer idInt = Integer.parseInt(id);

        List<UserFull> friends = apiService.getFriends(idInt);

        RequestDispatcher dispatcher = req.getRequestDispatcher(JSP_PATH + "friends.jsp");

        req.setAttribute("user", user);

        if (friends != null) {
            session.setAttribute("friends",friends);
            req.setAttribute("friends", friends);
        }

        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
