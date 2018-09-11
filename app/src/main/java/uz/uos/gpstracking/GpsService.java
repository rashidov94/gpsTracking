package uz.uos.gpstracking;

import android.Manifest;
import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class GpsService extends Service {

    private LocationManager mLocationManager;


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    if (mLocationManager != null) {
                        Log.d("MyLog", "mLocationManager isn`t null");
                        if (mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                            Log.d("MyLog", "Network Provider is enabled");
                            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 1000, new LocationListener() {
                                @Override
                                public void onLocationChanged(Location location) {

                                    Log.d("MyLog", "Lat " + location.getLatitude());
                                    Log.d("MyLog", "Long " + location.getLongitude());

                                    mLocationManager.removeUpdates(this);

                                    stopSelf();
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
                            });
                        }
                    }
                }

        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Used only in case of bound services.
        return null;
    }
}
