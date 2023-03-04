package logic;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiAccessAlbumException;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.groups.responses.GetByIdObjectLegacyResponse;
import com.vk.api.sdk.objects.photos.Photo;
import com.vk.api.sdk.objects.photos.PhotoSizes;
import com.vk.api.sdk.objects.users.Fields;
import com.vk.api.sdk.objects.users.UserFull;
import com.vk.api.sdk.objects.users.responses.GetResponse;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static data.DataForConnection.*;

public class ApiService {
    public static final int MILLIS = 300;
    TransportClient transportClient;
    VkApiClient vk;
    UserActor actor;

    public ApiService() {
        transportClient = new HttpTransportClient();
        vk = new VkApiClient(transportClient);
        actor = new UserActor(USER_ID, TOKEN);
    }

    public List<UserFull>  getUsersByUserName(String name, List<Fields> fields, Integer count) throws ClientException, ApiException {
        return vk.users()
                .search(actor)
                .q(name)
                .count(count)
                .fields(fields)
                .execute()
                .getItems();
    }

    public List<GetResponse> getUserByUserID(String id, List<Fields> fields) throws ClientException, ApiException {
        return vk.users()
                .get(actor)
                .userIds(id)
                .fields(fields)
                .execute();
    }

    public List<GetByIdObjectLegacyResponse> getGroupsByUserID(Integer id, List<com.vk.api.sdk.objects.groups.Fields> fields) throws InterruptedException, ClientException, ApiException {
        List<List<GetByIdObjectLegacyResponse>> groupsInfo = new ArrayList<>();

        List<Integer> groups =  vk.groups()
                                    .get(actor)
                                    .userId(id)
                                    .execute()
                                    .getItems();
        String gr = groups.toString().replace("[", "").replace("]","");
        return vk.groups()
                .getByIdObjectLegacy(actor)
                .groupIds(gr)
                .fields(fields)
                .execute();
    }

    public List<URI> getPhotosByUserID(Integer id) throws ClientException, ApiException, InterruptedException {
        List<URI> urls = new ArrayList<>();

        try {
            List<Photo> wallPhotos = vk.photos()
                    .get(actor)
                    .ownerId(id)
                    .albumId("wall")
                    .execute()
                    .getItems();

            for (Photo photo: wallPhotos) {
                List<PhotoSizes> sizes = photo.getSizes();
                urls.add(sizes.get(sizes.size() - 1).getUrl());
            }
        }catch (ApiAccessAlbumException e){
            System.out.println(e);
        }
        try {
            List<Photo> profilePhotos = vk.photos()
                    .get(actor)
                    .ownerId(id)
                    .albumId("profile")
                    .execute()
                    .getItems();

            for (Photo photo: profilePhotos) {
                List<PhotoSizes> sizes = photo.getSizes();
                urls.add(sizes.get(sizes.size() - 1).getUrl());
            }
        }catch (ApiAccessAlbumException e){
            System.out.println(e);
        }

        try {
            List<Photo> savedPhotos = vk.photos()
                    .get(actor)
                    .ownerId(id)
                    .albumId("saved")
                    .execute()
                    .getItems();

            for (Photo photo: savedPhotos) {
                List<PhotoSizes> sizes = photo.getSizes();
                urls.add(sizes.get(sizes.size() - 1).getUrl());
            }
        }catch (ApiAccessAlbumException e){
            System.out.println(e);
        }

        return urls;
    }

}
