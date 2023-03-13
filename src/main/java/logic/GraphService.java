package logic;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.users.Fields;
import com.vk.api.sdk.objects.users.UserFull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphService {
    private static final Integer MAX_GRAPH_SIZE = 200;
    private final Map<Integer, List<UserFull>> graphFriends;
    private final ApiService apiService;
    private static Integer level = 0;

    public GraphService() {
        this.graphFriends = new HashMap<>();
        apiService = new ApiService();
    }

    public Map<Integer, List<UserFull>> getGraphFriends() {
        return graphFriends;
    }

    public void createFriendsGraph(UserFull user) throws ClientException, ApiException {
        if (user.getIsClosed() || user.getDeactivated() != null ||
                graphFriends.containsKey(user.getId()) && graphFriends.get(user.getId()).size() > 0) {
            return;
        }

        graphFriends.put(user.getId(), new ArrayList<>());
        try {
            Thread.sleep(350);
            if (level < 2) {
                level++;
            } else {
                level = 0;
                return;
            }
            List<UserFull> friends = apiService.getFriendsByUserID(user.getId());
            for (UserFull friend : friends) {
                graphFriends.get(user.getId()).add(friend);
            }
            if (graphFriends.size() < MAX_GRAPH_SIZE) {
                for (UserFull friend : graphFriends.get(user.getId())) {
                    if (graphFriends.size() < MAX_GRAPH_SIZE) {
                        createFriendsGraph(friend);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
