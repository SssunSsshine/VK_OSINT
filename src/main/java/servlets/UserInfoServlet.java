package servlets;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.groups.responses.GetByIdObjectLegacyResponse;
import com.vk.api.sdk.objects.users.User;
import com.vk.api.sdk.objects.users.UserFull;
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
import logic.ApiService;
import logic.LocationService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static data.DataForConnection.FIELDS;

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
        String id = req.getParameter("id");
        Integer idInt = Integer.parseInt(id);
        GetResponse user = null;
        try {
            user = apiService.getUserByUserID(id, FIELDS);

            List<WallpostFull> notes = getNotes(id);

            List<Coordinate> coordinates = getCoordinates(id);

            List<String> locations = getLocations(coordinates);

            Map<String, List<Integer>> mapMutualGroups = getMutualGroups(idInt);

            List<GetByIdObjectLegacyResponse> groups = getGroups(idInt);

            List<UserFull> friends = getFriends(idInt);

            RequestDispatcher dispatcher = req.getRequestDispatcher(JSP_PATH + "user-info.jsp");
            req.setAttribute("user", user);

            if (notes != null)
                req.setAttribute("notes", notes);
            if (locations != null)
                req.setAttribute("locations", locations);
            if (groups != null) {
                req.setAttribute("groups", groups);
            }
            if (friends != null) {
                req.setAttribute("friends", friends);
            }
            if (mapMutualGroups != null) {
                req.setAttribute("mutualGroups", mapMutualGroups);
            }

            dispatcher.forward(req, resp);
        } catch (ClientException | ApiException e) {
            System.out.println(e);
        }
    }

    private static List<UserFull> getFriends(Integer idInt) {
        List<UserFull> friends;
        try {
            friends = apiService.getFriendsByUserID(idInt);
            return friends;
        } catch (ClientException | ApiException e) {
            return null;
        }
    }

    private static List<WallpostFull> getNotes(String id) {
        List<WallpostFull> notes;
        try {
            notes = apiService.getNotesByUserID(Integer.parseInt(id));
            return notes;
        } catch (ClientException | ApiException e) {
            System.out.println(e);
            return null;
        }
    }

    private static List<Coordinate> getCoordinates(String id) {
        List<Coordinate> coordinates;
        try {
            coordinates = apiService.getCoordinatesFromPhotos(apiService.getPhotosByUserID(Integer.parseInt(id)));
            return coordinates;
        } catch (ClientException | ApiException e) {
            System.out.println(e);
            return null;
        }
    }

    private static List<GetByIdObjectLegacyResponse> getGroups(Integer idInt) {
        List<GetByIdObjectLegacyResponse> groups;
        try {
            groups = apiService.getGroupsByUserID(idInt, new ArrayList<>());
            return groups;
        } catch (InterruptedException | ClientException | ApiException e) {
            System.out.println(e);
            return null;
        }
    }

    private static Map<String, List<Integer>> getMutualGroups(Integer id) {
        try {
            return apiService.getMutualGroupsByUserID(id);
        } catch (ClientException | InterruptedException | ApiException e) {
            System.out.println(e);
            return null;
        }
    }

    private static List<String> getLocations(List<Coordinate> coordinates) {
        List<String> locations = null;
        if (coordinates != null) {
            LocationService locationService = new LocationService();
            locations = locationService.getListLocations(coordinates);
        }
        return locations;
    }
}
