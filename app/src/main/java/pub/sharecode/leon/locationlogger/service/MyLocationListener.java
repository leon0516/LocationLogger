package pub.sharecode.leon.locationlogger.service;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import pub.sharecode.leon.locationlogger.model.LocationData;


/**
 * Created by leon on 17-6-8.
 */
public class MyLocationListener implements LocationListener {
    private String TAG = this.getClass().getSimpleName();

    @Override
    public void onLocationChanged(Location location) {
        LocationData locationData = new LocationData();
        locationData.setLatitude(location.getLatitude());
        locationData.setLongitude(location.getLongitude());
        locationData.setTime(location.getTime());
        locationData.setSpeed(location.getSpeed());
        if (locationData.save())
            Log.d(TAG, locationData.toString());
        else
            Log.e(TAG, "Location Data Saved Error!");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

}
