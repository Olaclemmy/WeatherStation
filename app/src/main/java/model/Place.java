package model;

/**
 * Created by speci on 3/25/2017.
 */

public class Place {

    private float lon;
    private float lat;
    private long sunSet;
    private long sunRise;
    private String country;
    private String city;
    private long lastUpdate;

    public Place() {
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public long getSunSet() {
        return sunSet;
    }

    public void setSunSet(long sunSet) {
        this.sunSet = sunSet;
    }

    public long getSunRise() {
        return sunRise;
    }

    public void setSunRise(long sunRise) {
        this.sunRise = sunRise;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
