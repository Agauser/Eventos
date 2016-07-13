package es.pue.eventos.presenetationlayer.controllers.services;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import es.pue.eventos.presenetationlayer.controllers.androidextends.application.PueAndroidApplication;
import es.pue.eventos.presenetationlayer.listeners.GeoPositionListener;


public class GeoPositionService extends Service {

    private LocationManager locationManager;
    private GeoPositionListener geoPositionListener;


    @Override
    public void onCreate() {
        super.onCreate();

        locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);

        geoPositionListener = new GeoPositionListener(
                        ((PueAndroidApplication)
                        this.getApplication()).
                        getCurrentAsistencia());


       iniciarPosicionamiento();

    }

    private void iniciarPosicionamiento(){

        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                5000,
                0,
                geoPositionListener);
    }


    @Override
    public void onDestroy() {
        locationManager.removeUpdates(geoPositionListener);
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }




}
