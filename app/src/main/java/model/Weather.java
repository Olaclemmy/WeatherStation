package model;

/**
 * Created by speci on 3/25/2017.
 */

public class Weather {

    public Place place;
    public byte[] iconData;
    public CurrentCondition currentCondition = new CurrentCondition();
    public Temperature temperature = new Temperature();
    public Wind wind = new Wind();
    public Snow snow = new Snow();
    public Cloud cloud = new Cloud();

    public Weather() {
    }
}
