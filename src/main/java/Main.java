
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.groups.responses.GetByIdObjectLegacyResponse;
import com.vk.api.sdk.objects.photos.Photo;
import com.vk.api.sdk.objects.users.Fields;
import com.vk.api.sdk.objects.users.UserFull;
import com.vk.api.sdk.objects.users.responses.GetResponse;
import com.vk.api.sdk.objects.wall.WallpostFull;
import data.Coordinate;
import logic.repo.ApiRepo;
import logic.repo.FileRepository;
import logic.service.GraphService;
import logic.service.LocationService;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static data.DataForConnection.USER_FIELDS;
import static data.DataForConnection.USER_ID;

public class Main {

    public static final Integer EXAMPLE_LOC_ID = 741461470;
    public static final Integer EXAMPLE_ID = 4610337;
    public static final Integer KRAMARENKO_ID = 158586728;

    public static void main(String[] args) throws ClientException, ApiException, InterruptedException, IOException {

        ApiRepo apiService = new ApiRepo();
        FileRepository fileRepository = new FileRepository();
        LocationService locationService = new LocationService();
        GraphService graphService = new GraphService();

        List<Fields> fields = new ArrayList<>();
        fields.add(Fields.COUNTRY);
        fields.add(Fields.CITY);
        fields.add(Fields.BDATE);
        fields.add(Fields.STATUS);
        fields.add(Fields.SEX);
        fields.add(Fields.BOOKS);
        fields.add(Fields.INTERESTS);

        List<Photo> photosByCoordinates = apiService.getPhotosByCoordinates(51, 39, 40000);
        Integer id = photosByCoordinates.get(10).getOwnerId();

        List<UserFull> users = apiService.getUsersByUserName("Ирина Воронина", USER_FIELDS,10);
        String photo = String.valueOf(users.get(0).getPhoto100());
        graphService.createExampleGraph(apiService.getUserByUserID(USER_ID.toString(), new ArrayList<>()));
        graphService.graphToImage();

        /*graphService.createFriendsGraph(apiService.getUserByUserID(USER_ID.toString(), new ArrayList<>()).get(0));
        Map<Integer, List<UserFull>> contactMatrix = graphService.getGraphFriends();*/

        //fileRepository.mutualGroupsWithFriendsToFile(apiService.getMutualGroupsByUserID(KRAMARENKO_ID), "mutualGroups.txt");

        fileRepository.mutualFriendsIDsToFile(apiService.getMutualFriendsByUserID(KRAMARENKO_ID), "mutualFriends.txt");

        /*fileRepository.friendsToFile(apiService.getFriendsByUserID(KRAMARENKO_ID).stream()
                .map(UserMin::getId).collect(Collectors.toList()), "friends.txt");*/

        //извлечение текста из записей пользователя
        List<WallpostFull> posts = apiService.getNotesByUserID(EXAMPLE_ID);
        fileRepository.postsToFile(posts,"posts.txt");

        //извлечение координат из фотографий с геометками

        List<Photo> photosFromUserWithCoord = apiService.getPhotosByUserID(id);
        fileRepository.coordinatesToFile(apiService.getCoordinatesFromPhotos(photosFromUserWithCoord), "coordinates.txt");

        //вывод названия места по координатам
        List<Coordinate> coordinates = apiService.getCoordinatesFromPhotos(photosFromUserWithCoord);
        for (Coordinate coordinate: coordinates
             ) {
            System.out.println(locationService.getLocationByCoordinates(Double.parseDouble(coordinate.getLat().toString()),Double.parseDouble(coordinate.getLng().toString())));
        }

        GetResponse user = apiService.getUserByUserID(EXAMPLE_ID.toString(), fields);

        List<GetByIdObjectLegacyResponse> groups = apiService.getGroupsByUserID(user.getId(), new ArrayList<>());
        List<URI> photosURI = apiService.getURIFromPhotos(apiService.getPhotosByUserID(user.getId()));

        String pathUsers = "users.txt";
        String pathUser = "user.txt";

        fileRepository.usersToFile(users, pathUsers);

       // fileRepository.userToFile(user, groups, photosURI, pathUser);
    }
}