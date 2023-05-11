package logic.service;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.groups.responses.GetByIdObjectLegacyResponse;
import com.vk.api.sdk.objects.users.UserFull;
import com.vk.api.sdk.objects.users.responses.GetResponse;
import com.vk.api.sdk.objects.wall.WallpostFull;
import data.Coordinate;
import logic.repo.ApiRepo;

import java.net.URI;
import java.util.List;
import java.util.Map;

import static data.DataForConnection.GROUPS_FIELDS;
import static data.DataForConnection.USER_FIELDS;

public class ApiService {

    private static ApiRepo apiRepo;

    public ApiService() {
        apiRepo = new ApiRepo();
    }

    public GetResponse getUser(String id) {
        List<UserFull> friends;
        try {
            return apiRepo.getUserByUserID(id, USER_FIELDS);
        } catch (ClientException | ApiException e) {
            return null;
        }
    }

    public List<UserFull> getFriends(Integer idInt) {
        List<UserFull> friends;
        try {
            friends = apiRepo.getFriendsByUserID(idInt);
            return friends;
        } catch (ClientException | ApiException e) {
            return null;
        }
    }

    public List<WallpostFull> getNotes(String id) {
        List<WallpostFull> notes;
        try {
            notes = apiRepo.getNotesByUserID(Integer.parseInt(id));
            return notes;
        } catch (ClientException | ApiException e) {
            System.out.println(e);
            return null;
        }
    }

    public List<Coordinate> getCoordinates(String id) {
        List<Coordinate> coordinates;
        try {
            coordinates = apiRepo.getCoordinatesFromPhotos(apiRepo.getPhotosByUserID(Integer.parseInt(id)));
            return coordinates;
        } catch (ClientException | ApiException e) {
            System.out.println(e);
            return null;
        }
    }

    public List<GetByIdObjectLegacyResponse> getGroups(Integer idInt) {
        List<GetByIdObjectLegacyResponse> groups;
        try {
            groups = apiRepo.getGroupsByUserID(idInt, GROUPS_FIELDS);
            return groups;
        } catch (InterruptedException | ClientException | ApiException e) {
            System.out.println(e);
            return null;
        }
    }

    public Map<String, List<Integer>> getMutualGroups(Integer id) {
        try {
            return apiRepo.getMutualGroupsByUserID(id);
        } catch (ClientException | InterruptedException | ApiException e) {
            System.out.println(e);
            return null;
        }
    }

    public List<String> getLocations(List<Coordinate> coordinates) {
        List<String> locations = null;
        if (coordinates != null) {
            LocationService locationService = new LocationService();
            locations = locationService.getListLocations(coordinates);
        }
        return locations;
    }

    public List<URI> getPhotos(Integer id){
        try {
            return apiRepo.getURIFromPhotos(apiRepo.getPhotosByUserID(id));
        } catch (ClientException | ApiException e) {
            return null;
        }
    }
}
