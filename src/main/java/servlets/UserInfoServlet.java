package servlets;

import com.vk.api.sdk.objects.users.responses.GetResponse;
import com.vk.api.sdk.objects.wall.WallpostFull;
import data.Coordinate;
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
import java.util.stream.Collectors;

@WebServlet("/user-info")
public class UserInfoServlet extends HttpServlet {
    private static final String JSP_PATH = "/WEB-INF/jsp/";
    private static ApiService apiService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        apiService = new ApiService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        showUserInfo(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private void showUserInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.invalidate();
        String id = req.getParameter("id");

        GetResponse user = apiService.getUser(id);

        if (user == null) {
            return;
        }
        session.setAttribute("user", user);

        List<WallpostFull> notes = apiService.getNotes(id);

        List<String> locations = apiService.getLocations(apiService.getCoordinates(id));

        RequestDispatcher dispatcher = req.getRequestDispatcher(JSP_PATH + "user-info.jsp");
        req.setAttribute("user", user);

        if (notes != null) {
            notes = notes.stream().filter(note -> !note.getText().isBlank()).collect(Collectors.toList());
            if (notes.size() != 0) {
                session.setAttribute("notes", notes);
                req.setAttribute("notes", notes);
            }
        }
        if (locations != null) {
            session.setAttribute("location", locations);
            req.setAttribute("locations", locations);
        }

        dispatcher.forward(req, resp);
    }

}
