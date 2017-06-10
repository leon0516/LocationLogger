package pub.sharecode.leon.locationlogger.model;

import org.litepal.crud.DataSupport;

import pub.sharecode.leon.locationlogger.utils.TimeUtils;

/**
 * Created by leon on 17-6-8.
 */

public class LocationData extends DataSupport {
    private long time;
    //经度
    private double longitude;
    //维度
    private double latitude;
    //速度
    private float speed;

    public LocationData() {
    }

    public LocationData(long time, double longitude, double latitude, float speed) {
        this.time = time;
        this.longitude = longitude;
        this.latitude = latitude;
        this.speed = speed;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "Time:" + TimeUtils.millis2String(time) + ";Longitude:" + longitude +
                ";Latitude:" + latitude + ";Speed:" + speed;
    }
}
