package servlets;

import com.vk.api.sdk.objects.groups.responses.GetByIdObjectLegacyResponse;
import com.vk.api.sdk.objects.users.responses.GetResponse;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import logic.service.ApiService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/groups")
public class UserGroupsServlet extends HttpServlet {
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

        Map<String, List<Integer>> mapMutualGroups = apiService.getMutualGroups(idInt);
        List<GetByIdObjectLegacyResponse> groups = apiService.getGroups(idInt);

        RequestDispatcher dispatcher = req.getRequestDispatcher(JSP_PATH + "groups.jsp");

        req.setAttribute("user", user);
        if (groups != null) {
            session.setAttribute("groups", groups);
            req.setAttribute("groups", groups);
        }
        if (mapMutualGroups != null) {
            session.setAttribute("mutualGroups", mapMutualGroups);
            req.setAttribute("mutualGroups", mapMutualGroups);
        }

        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
