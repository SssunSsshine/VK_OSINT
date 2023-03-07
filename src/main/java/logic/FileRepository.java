package logic;

import com.vk.api.sdk.objects.groups.responses.GetByIdObjectLegacyResponse;
import com.vk.api.sdk.objects.users.UserFull;
import com.vk.api.sdk.objects.users.responses.GetResponse;
import com.vk.api.sdk.objects.wall.WallpostFull;
import data.Coordinate;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URI;
import java.util.List;

public class FileRepository {
    public void coordinatesToFile(List<Coordinate> coordinates, String path){
        try (Writer bufferedWriter = new BufferedWriter(new FileWriter(path))) {
            for (Coordinate coordinate: coordinates
            ) {
                bufferedWriter.write(coordinate.toString() + "\n\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void postsToFile(List<WallpostFull> posts, String pathPosts) {
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

    public void usersToFile(List<UserFull> users, String pathUsers) {
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

    public void userToFile(List<GetResponse> user, List<GetByIdObjectLegacyResponse> groups, List<URI> photos, String pathUser) {
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
