package data;

public class Coordinate {
    Float lng;
    Float lat;

    @Override
    public String toString() {
        return "(" +
                "lng=" + lng +
                ", lat=" + lat +
                ')';
    }

    public Coordinate(Float lat, Float lng) {
        this.lng = lng;
        this.lat = lat;
    }

    public void setLng(Float lng) {
        this.lng = lng;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLng() {
        return lng;
    }

    public Float getLat() {
        return lat;
    }
}
