package logic.service;

import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageResponse;
import com.byteowls.jopencage.model.JOpenCageReverseRequest;
import data.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class LocationService {
    private  static final String key = "0cf2811274904411992973e6f77dbb02";

    public String getLocationByCoordinates(Double lat, Double lng){
        JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder(key);

        JOpenCageReverseRequest request = new JOpenCageReverseRequest(lat, lng);

        JOpenCageResponse response = jOpenCageGeocoder.reverse(request);

        return response
                .getResults()
                .get(0)
                .getFormatted();
    }

    public List<String> getListLocations(List<Coordinate> coordinates){
        List<String> res = new ArrayList<>();
        for (Coordinate coordinate: coordinates
        ) {
            res.add(getLocationByCoordinates(Double.parseDouble(coordinate.getLat().toString()),
                                                        Double.parseDouble(coordinate.getLng().toString())));
        }
        return res;
    }
}
