package logic;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.util.mxCellRenderer;
import com.vk.api.sdk.objects.users.UserFull;
import org.jgraph.graph.DefaultEdge;
import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class GraphService {
    private static final Integer MAX_GRAPH_SIZE = 100;
    //private final Map<Integer, List<UserFull>> graphFriends;
    private final ApiService apiService;
    private static Integer level = 0;
    private static Graph<UserFull, DefaultEdge> g;
    public GraphService() {
        //this.graphFriends = new HashMap<>();
        apiService = new ApiService();
        g = new DefaultDirectedGraph<>(DefaultEdge.class);
    }


    public void createGraph(UserFull user) throws IOException {
        if (user.getIsClosed() || user.getDeactivated() != null ||
                (g.containsVertex(user) && g.edgesOf(user).size() > 1)) {
            int k = g.edgesOf(user).size();
            return;
        }
        if(!g.containsVertex(user))
            g.addVertex(user);
        try {
            Thread.sleep(350);
            if (level < 3) {
                level++;
            } else {
                level--;
                return;
            }
            List<UserFull> friends = apiService.getFriendsByUserID(user.getId());
            int i = 0;
            for (UserFull friend : friends) {
                if(i > 10){
                    break;
                }
                if(!g.containsVertex(friend))
                    g.addVertex(friend);
                if(!g.containsEdge(user, friend) && !g.containsEdge(friend, user))
                    g.addEdge(user, friend);
                i++;
            }

            if (g.vertexSet().size() < MAX_GRAPH_SIZE) {
                for (UserFull friend : friends) {
                    if (g.vertexSet().size() < MAX_GRAPH_SIZE) {
                        createGraph(friend);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void givenAdaptedGraph_whenWriteBufferedImage_thenFileShouldExist() throws IOException {
        File imgFile = new File("graph.png");
        imgFile.createNewFile();

        JGraphXAdapter<UserFull, DefaultEdge> graphAdapter =
                new JGraphXAdapter<>(g);
        mxIGraphLayout layout = new mxCompactTreeLayout(graphAdapter);
        layout.execute(graphAdapter.getDefaultParent());

        BufferedImage image =
                mxCellRenderer.createBufferedImage(graphAdapter, null, 1, Color.WHITE, false,null);
        ImageIO.write(image, "PNG", imgFile);
    }

    /*public Map<Integer, List<UserFull>> getGraphFriends() {
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
    }*/
}
