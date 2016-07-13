package es.pue.eventos.presenetationlayer.listeners;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import java.util.Date;

import es.pue.eventos.model.businesslayer.entities.Asistencia;
import es.pue.eventos.model.businesslayer.entities.Posicion;


public class GeoPositionListener implements LocationListener {

    private Asistencia asistencia;

    public GeoPositionListener(Asistencia asistencia){
        this.asistencia=asistencia;
    }

    @Override
    public void onLocationChanged(Location location) {
        Posicion p = new Posicion();
        p.setLatitud(location.getLatitude());
        p.setLongitud(location.getLongitude());
        p.setPrecision(location.getAccuracy());
        p.setFecha(new Date());

        asistencia.getRecorrido().add(p);

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
