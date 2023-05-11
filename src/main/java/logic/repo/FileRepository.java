package logic.repo;

import com.vk.api.sdk.objects.friends.responses.GetMutualTargetUidsResponse;
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
import java.util.Map;

public class FileRepository {

    public void groupsToFile(GetResponse user, List<GetByIdObjectLegacyResponse> groups, Map<String, List<Integer>> map, String path) {
        try (Writer bufferedWriter = new BufferedWriter(new FileWriter(path))) {
            bufferedWriter.write("ID: " + user.getId() + "\n"
                            + "ScreenName: " + user.getScreenName() + "\n"
                            + "Имя Фамилия: " + user.getFirstName() + " " + user.getLastName() + "\n\n");
            bufferedWriter.write("Группы:\n");
            for (GetByIdObjectLegacyResponse gr : groups) {
                bufferedWriter.write("ID группы: " + gr.getId() + "\n"
                        + "Название группы: " + gr.getName() + "\n"
                        + "Описание группы: " + gr.getDescription() + "\n"
                        + "Статус группы: " + gr.getStatus() + "\n\n");
            }

            bufferedWriter.write("Список общих групп с друзьями (Формат id группы: список id друзей)\n");
            for (Map.Entry<String, List<Integer>> entry : map.entrySet()
            ) {
                bufferedWriter.write(entry.getKey()
                        + ": " + entry.getValue() + "\n\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void mutualFriendsIDsToFile(List<GetMutualTargetUidsResponse> mutualFriends, String path) {
        try (Writer bufferedWriter = new BufferedWriter(new FileWriter(path))) {
            bufferedWriter.write("Список общих друзей с друзьями\n");
            for (GetMutualTargetUidsResponse mutFriend : mutualFriends
            ) {

                bufferedWriter.write(mutFriend.getId()
                        + ": " + mutFriend.getCommonFriends() + "\n\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void friendsToFile(GetResponse user, List<UserFull> friends, String path) {
        try (Writer bufferedWriter = new BufferedWriter(new FileWriter(path))) {
            bufferedWriter.write("ID: " + user.getId() + "\n"
                    + "ScreenName: " + user.getScreenName() + "\n"
                    + "Имя Фамилия: " + user.getFirstName() + " " + user.getLastName() + "\n\n");
            bufferedWriter.write("Друзья:\n");
            for (UserFull friend : friends
            ) {
                bufferedWriter.write("ID: " + friend.getId() + "\n"
                        + "ScreenName: " + friend.getScreenName() + "\n"
                        + "Имя Фамилия: " + friend.getFirstName() + " " + friend.getLastName() + "\n\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void coordinatesToFile(List<Coordinate> coordinates, String path) {
        try (Writer bufferedWriter = new BufferedWriter(new FileWriter(path))) {
            for (Coordinate coordinate : coordinates
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
                if (post.getText().isBlank()) continue;
                bufferedWriter.write("\"" + post.getText() + "\"\n\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void userToFile(GetResponse user, List<String> locations, List<WallpostFull> posts, String pathUser) {
        try (Writer bufferedWriter = new BufferedWriter(new FileWriter(pathUser))) {
            bufferedWriter.write("ID: " + user.getId() + "\n"
                    + "ScreenName: " + user.getScreenName() + "\n"
                    + "Имя Фамилия: " + user.getFirstName() + " " + user.getLastName() + "\n"
                    + "День рождения: " + user.getBdate() + "\n"
                    + "Страна: " + user.getCountry() + "\n"
                    + "Город: " + user.getCity() + "\n"
                    + "Пол: " + user.getSex() + "\n"
                    + "Интересы: " + user.getInterests() + "\n"
                    + "Книги: " + user.getBooks() + "\n"
                    + "Статус: " + user.getStatus() + "\n\n");
            if (locations != null) {
                bufferedWriter.write("Посещаемые локации: \n");
                for (String location : locations) {
                    bufferedWriter.write(location + "\n");
                }
            }
            bufferedWriter.write("\n");
            if (posts != null) {
                bufferedWriter.write("Записи: \n");
                for (WallpostFull post : posts) {
                    bufferedWriter.write(post.getText() + "\n");
                }
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

    /*public void userToFile(GetResponse user, List<GetByIdObjectLegacyResponse> groups, List<URI> photos, String pathUser) {
        try (Writer bufferedWriter = new BufferedWriter(new FileWriter(pathUser))) {
            bufferedWriter.write("ID: " + user.getId() + "\n"
                    + "ScreenName: " + user.getScreenName() + "\n"
                    + "Имя Фамилия: " + user.getFirstName() + " " + user.getLastName() + "\n"
                    + "День рождения: " + user.getBdate() + "\n"
                    + "Страна: " + user.getCountry() + "\n"
                    + "Город: " + user.getCity() + "\n"
                    + "Пол: " + user.getSex() + "\n"
                    + "Интересы: " + user.getInterests() + "\n"
                    + "Книги: " + user.getBooks() + "\n"
                    + "Статус: " + user.getStatus() + "\n"
                    + "Instagram: " + user.getInstagram() + "\n\n");

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
    }*/

    public void photosToFile(GetResponse user, List<URI> photos, String path){
        try (Writer bufferedWriter = new BufferedWriter(new FileWriter(path))) {
            bufferedWriter.write("ID: " + user.getId() + "\n"
                    + "ScreenName: " + user.getScreenName() + "\n"
                    + "Имя Фамилия: " + user.getFirstName() + " " + user.getLastName() + "\n\n");
            bufferedWriter.write("Фотографии:\n");
            for (URI uri : photos) {
                bufferedWriter.write(uri + "\n\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void userToFile(GetResponse user, String pathUser) {
        try (Writer bufferedWriter = new BufferedWriter(new FileWriter(pathUser))) {
            bufferedWriter.write("ID: " + user.getId() + "\n"
                    + "ScreenName: " + user.getScreenName() + "\n"
                    + "Имя Фамилия: " + user.getFirstName() + " " + user.getLastName() + "\n"
                    + "День рождения: " + user.getBdate() + "\n"
                    + "Страна: " + user.getCountry() + "\n"
                    + "Город: " + user.getCity() + "\n"
                    + "Пол: " + user.getSex() + "\n"
                    + "Интересы: " + user.getInterests() + "\n"
                    + "Книги: " + user.getBooks() + "\n"
                    + "Статус: " + user.getStatus());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
