
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

        //извлечение текста из записей пользователя
        List<WallpostFull> posts = apiService.getNotesByUserID(EXAMPLE_ID);
        postsToFile(posts,"posts.txt");

        //извлечение координат из фотографий с геометками
        List<Photo> photosByCoordinates = apiService.getPhotosByCoordinates(51, 39, 40000);
        Integer id = photosByCoordinates.get(10).getOwnerId();
        List<Photo> photosFromUserWithCoord = apiService.getPhotosByUserID(id);
        coordinatesToFile(apiService.getCoordinatesFromPhotos(photosFromUserWithCoord), "coordinates.txt");


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

        usersToFile(users, pathUsers);

        userToFile(user, groups, photosURI, pathUser);
    }

    private static void coordinatesToFile(List<Coordinate> coordinates, String path){
        try (Writer bufferedWriter = new BufferedWriter(new FileWriter(path))) {
            for (Coordinate coordinate: coordinates
                 ) {
                bufferedWriter.write(coordinate.toString() + "\n\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static void postsToFile(List<WallpostFull> posts, String pathPosts) {
        try (Writer bufferedWriter = new BufferedWriter(new FileWriter(pathPosts))) {
            for (WallpostFull post : posts
            ) {
                if(post.getText().isBlank()) continue;
                bufferedWriter.write("\"" + post.getText() + "\"\n\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void usersToFile(List<UserFull> users, String pathUsers) {
        try (Writer bufferedWriter = new BufferedWriter(new FileWriter(pathUsers))) {
            for (UserFull us : users) {
                bufferedWriter.write("ID: " + us.getId() + "\n");
                bufferedWriter.write("ScreenName: " + us.getScreenName() + "\n"
                        + "Имя Фамилия: " + us.getFirstName() + " " + us.getLastName() + "\n" +
                        "День рождения: " + us.getBdate() + "\n"
                        + "Страна: " + us.getCountry() + "\n"
                        + "Город: " + us.getCity() + "\n"
                        + "Пол: " + us.getSex() + "\n"
                        + "Интересы: " + us.getInterests() + "\n" + "Книги: " + us.getBooks() + "\n"
                        + "Статус: " + us.getStatus() + "\n"
                        + "Instagram: " + us.getInstagram() + "\n\n");
                /*bufferedWriter.write(us.toString() + "\n\n");*/
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void userToFile(List<GetResponse> user, List<GetByIdObjectLegacyResponse> groups, List<URI> photos, String pathUser) {
        try (Writer bufferedWriter = new BufferedWriter(new FileWriter(pathUser))) {
            GetResponse response = user.get(0);
            bufferedWriter.write("ID: " + response.getId() + "\n"
                    + "ScreenName: " + response.getScreenName() + "\n"
                    + "Имя Фамилия: " + response.getFirstName() + " " + response.getLastName() + "\n"
                    + "День рождения: " + response.getBdate() + "\n"
                    + "Страна: " + response.getCountry() + "\n"
                    + "Город: " + response.getCity() + "\n"
                    + "Пол: " + response.getSex() + "\n"
                    + "Интересы: " + response.getInterests() + "\n"
                    + "Книги: " + response.getBooks() + "\n"
                    + "Статус: " + response.getStatus() + "\n"
                    + "Instagram: " + response.getInstagram() + "\n\n");

            bufferedWriter.write("Группы:\n");
            for (GetByIdObjectLegacyResponse gr : groups) {
                bufferedWriter.write("ID группы: " + gr.getId() + "\n"
                        + "Название группы: " + gr.getName() + "\n"
                        + "Описание группы: " + gr.getDescription() + "\n"
                        + "Статус группы: " + gr.getStatus() + "\n\n");
            }

            bufferedWriter.write("Фотографии:\n");
            for (URI uri : photos) {
                bufferedWriter.write(uri + "\n\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}