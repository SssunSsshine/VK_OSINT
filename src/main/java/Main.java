
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.groups.responses.GetByIdObjectLegacyResponse;
import com.vk.api.sdk.objects.photos.Photo;
import com.vk.api.sdk.objects.users.Fields;
import com.vk.api.sdk.objects.users.UserFull;
import com.vk.api.sdk.objects.users.responses.GetResponse;
import com.vk.api.sdk.objects.wall.WallpostFull;
import data.Coordinate;
import logic.ApiService;
import logic.FileRepository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static final Integer EXAMPLE_ID = 4610337;

    public static void main(String[] args) throws ClientException, ApiException, InterruptedException {
        ApiService apiService = new ApiService();
        FileRepository fileRepository = new FileRepository();

        //извлечение текста из записей пользователя
        List<WallpostFull> posts = apiService.getNotesByUserID(EXAMPLE_ID);
        fileRepository.postsToFile(posts,"posts.txt");

        //извлечение координат из фотографий с геометками
        List<Photo> photosByCoordinates = apiService.getPhotosByCoordinates(51, 39, 40000);
        Integer id = photosByCoordinates.get(10).getOwnerId();
        List<Photo> photosFromUserWithCoord = apiService.getPhotosByUserID(id);
        fileRepository.coordinatesToFile(apiService.getCoordinatesFromPhotos(photosFromUserWithCoord), "coordinates.txt");


        List<Fields> fields = new ArrayList<>();
        fields.add(Fields.COUNTRY);
        fields.add(Fields.CITY);
        fields.add(Fields.BDATE);
        fields.add(Fields.STATUS);
        fields.add(Fields.SEX);
        fields.add(Fields.BOOKS);
        fields.add(Fields.INTERESTS);

        List<UserFull> users = apiService.getUsersByUserName("Ирина Воронина", fields, 100);

        List<GetResponse> user = apiService.getUserByUserID(EXAMPLE_ID.toString(), fields);

        List<GetByIdObjectLegacyResponse> groups = apiService.getGroupsByUserID(user.get(0).getId(), new ArrayList<>());

        List<URI> photosURI = apiService.getURIFromPhotos(apiService.getPhotosByUserID(user.get(0).getId()));

        String pathUsers = "users.txt";
        String pathUser = "user.txt";

        fileRepository.usersToFile(users, pathUsers);

        fileRepository.userToFile(user, groups, photosURI, pathUser);
    }


}