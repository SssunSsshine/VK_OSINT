package logic;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiAccessAlbumException;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.friends.responses.GetMutualTargetUidsResponse;
import com.vk.api.sdk.objects.groups.responses.GetByIdObjectLegacyResponse;
import com.vk.api.sdk.objects.groups.responses.IsMemberUserIdsResponse;
import com.vk.api.sdk.objects.photos.Photo;
import com.vk.api.sdk.objects.photos.PhotoSizes;
import com.vk.api.sdk.objects.users.Fields;
import com.vk.api.sdk.objects.users.UserFull;
import com.vk.api.sdk.objects.users.UserMin;
import com.vk.api.sdk.objects.users.responses.GetResponse;
import com.vk.api.sdk.objects.wall.GetFilter;
import com.vk.api.sdk.objects.wall.WallpostFull;
import data.Coordinate;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static data.DataForConnection.*;

public class ApiService {
    public static final int MAX_COUNT = 100;
    TransportClient transportClient;
    VkApiClient vk;
    UserActor actor;
    ServiceActor serviceActor;

    public ApiService() {
        transportClient = new HttpTransportClient();
        vk = new VkApiClient(transportClient);
        actor = new UserActor(USER_ID, TOKEN);
        serviceActor = new ServiceActor(APP_ID, SERV_KEY);
    }

    public List<UserFull> getUsersByUserName(String name, List<Fields> fields, Integer count) throws ClientException, ApiException {
        return vk.users()
                .search(actor)
                .q(name)
                .count(count)
                .fields(fields)
                .execute()
                .getItems();
    }

    public GetResponse getUserByUserID(String id, List<Fields> fields) throws ClientException, ApiException {
        List<GetResponse> users = vk.users()
                .get(actor)
                .userIds(id)
                .fields(fields)
                .execute();
        if (users.size() == 0) {
            return null;
        }
        return users.get(0);
    }

    public List<GetResponse> getUsersByUsersID(List<String> ids, List<Fields> fields) throws ClientException, ApiException {
        return vk.users()
                .get(actor)
                .userIds(ids)
                .fields(fields)
                .execute();
    }

    public List<GetByIdObjectLegacyResponse> getGroupsByUserID(Integer id, List<com.vk.api.sdk.objects.groups.Fields> fields) throws InterruptedException, ClientException, ApiException {
        List<List<GetByIdObjectLegacyResponse>> groupsInfo = new ArrayList<>();

        List<Integer> groups = vk.groups()
                .get(actor)
                .userId(id)
                .execute()
                .getItems();
        String gr = groups.toString().replace("[", "").replace("]", "");
        return vk.groups()
                .getByIdObjectLegacy(actor)
                .groupIds(gr)
                .fields(fields)
                .execute();
    }

    public List<URI> getURIFromPhotos(List<Photo> photos) {
        List<URI> uris = new ArrayList<>();
        for (Photo photo : photos) {
            List<PhotoSizes> sizes = photo.getSizes();
            uris.add(sizes.get(sizes.size() - 1).getUrl());
        }
        return uris;
    }

    public List<Coordinate> getCoordinatesFromPhotos(List<Photo> photos) {
        List<Coordinate> coordinates = new ArrayList<>();
        for (Photo photo : photos
        ) {
            if (photo.getLat() != null) {
                Coordinate coordinate = new Coordinate(photo.getLat(), photo.getLng());
                coordinates.add(coordinate);
            }
        }

        if (coordinates.size() == 0) return null;
        return coordinates;
    }

    public List<Photo> getPhotosByUserID(Integer id) throws ClientException, ApiException {
        List<Photo> photos = new ArrayList<>();
        try {
            List<Photo> wallPhotos = vk.photos()
                    .get(actor)
                    .ownerId(id)
                    .albumId("wall")
                    .execute()
                    .getItems();

            photos = Stream.concat(photos.stream(), wallPhotos.stream()).collect(Collectors.toList());

        } catch (ApiAccessAlbumException e) {
            System.out.println(e);
        }
        try {
            List<Photo> profilePhotos = vk.photos()
                    .get(actor)
                    .ownerId(id)
                    .albumId("profile")
                    .execute()
                    .getItems();
            photos = Stream.concat(photos.stream(), profilePhotos.stream()).collect(Collectors.toList());
        } catch (ApiAccessAlbumException e) {
            System.out.println(e);
        }

        try {
            List<Photo> savedPhotos = vk.photos()
                    .get(actor)
                    .ownerId(id)
                    .albumId("saved")
                    .execute()
                    .getItems();
            photos = Stream.concat(photos.stream(), savedPhotos.stream()).collect(Collectors.toList());
        } catch (ApiAccessAlbumException e) {
            System.out.println(e);
        }
        return photos;
    }

    public List<Photo> getPhotosByCoordinates(Number lat, Number lng, Integer radius) throws ClientException, ApiException {
        return vk.photos()
                .search(actor)
                .lat(lat)
                .lng(lng)
                .radius(radius)
                .count(MAX_COUNT)
                .execute()
                .getItems()
                .stream()
                .filter(photo -> photo.getOwnerId() > 0)
                .collect(Collectors.toList());
    }

    public List<WallpostFull> getNotesByUserID(Integer id) throws ClientException, ApiException {
        List<WallpostFull> list;
        List<WallpostFull> res = new ArrayList<>();
        int j = 0;
        do {
            list = vk.wall()
                    .get(actor)
                    .ownerId(id)
                    .offset(j)
                    .filter(GetFilter.OWNER)
                    .count(MAX_COUNT)
                    .execute()
                    .getItems();
            res = Stream.concat(res.stream(), list.stream()).collect(Collectors.toList());
            j += MAX_COUNT;
        } while (list.size() != 0);
        return res;
    }

    public List<GetMutualTargetUidsResponse> getMutualFriendsByUserID(Integer id) throws ClientException, ApiException {
        List<UserFull> friends = getFriendsByUserID(id);
        List<Integer> targetUids = new ArrayList<>();

        for (UserFull friend : friends
        ) {
            if (!friend.getIsClosed() && friend.getDeactivated() == null) {
                targetUids.add(friend.getId());
            }
        }

        Integer[] targetUidsArr = targetUids.stream()
                .filter(Objects::nonNull)
                .toArray(Integer[]::new);
        return vk.friends()
                .getMutualWithTargetUids(actor, targetUidsArr)
                .sourceUid(id)
                .execute()
                .stream()
                .filter(resp -> resp.getCommonCount() > 0)
                .collect(Collectors.toList());
    }

    public List<UserFull> getFriendsByUserID(Integer id) throws ClientException, ApiException {
        return vk.friends()
                .getWithFields(actor, Fields.DOMAIN, Fields.PHOTO_200)
                .userId(id)
                .execute()
                .getItems();
    }

    public Map<String, List<Integer>> getMutualGroupsByUserID(Integer id) throws ClientException, InterruptedException, ApiException {
        List<GetByIdObjectLegacyResponse> groups = getGroupsByUserID(id, new ArrayList<>())
                .stream()
                .filter(group -> group.getIsClosed().getValue().equals("0"))
                .collect(Collectors.toList());

        Integer[] friends = getFriendsByUserID(id).stream()
                .map(UserMin::getId)
                .toArray(Integer[]::new);

        Map<String, List<Integer>> map = new HashMap<>();

        for (GetByIdObjectLegacyResponse group : groups) {
            List<Integer> areMembers;
            try {
                areMembers = vk.groups()
                        .isMemberWithUserIds(actor, group.getId().toString(), friends)
                        .execute()
                        .stream()
                        .filter(IsMemberUserIdsResponse::isMember)
                        .map(IsMemberUserIdsResponse::getUserId)
                        .collect(Collectors.toList());
            } catch (Exception e) {
                Thread.sleep(325);
                continue;
            }

            map.put(group.getName() + " " + group.getId(), areMembers);
            Thread.sleep(325);
        }

        Map<String, List<Integer>> resMap = new HashMap<>();
        for (Map.Entry<String, List<Integer>> entry : map.entrySet()
        ) {
            if (entry.getValue().size() > 0)
                resMap.put(entry.getKey(), entry.getValue());
        }

        return resMap;
    }
}
