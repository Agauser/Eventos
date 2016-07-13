package es.pue.eventos.presenetationlayer.controllers.activities;

import android.app.Application;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import es.pue.eventos.R;
import es.pue.eventos.model.businesslayer.entities.Asistencia;
import es.pue.eventos.model.businesslayer.entities.Posicion;
import es.pue.eventos.presenetationlayer.controllers.androidextends.application.PueAndroidApplication;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    private PueAndroidApplication application;
    private GoogleMap mMap;
    private UpdateMap updateMap=null;
    public  boolean active = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Log.d("onCreate",this.toString());
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        application= (PueAndroidApplication) this.getApplication();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        if(application!=null&&
               application.getCurrentAsistencia()!=null&&
               application.getCurrentAsistencia().getRecorrido()!=null
               ){

            updateMap=new UpdateMap();
           updateMap.execute(application.getCurrentAsistencia(),mMap,this);


          if(application.getCurrentAsistencia().getRecorrido().size()>0) {
              mMap.moveCamera(
                      CameraUpdateFactory.newLatLng(
                              new LatLng(
                                      application.getCurrentAsistencia().getRecorrido().get(0).getLatitud()
                                      ,
                                      application.getCurrentAsistencia().getRecorrido().get(0).getLongitud()
                              )));
          }
           mMap.setMyLocationEnabled(true);
       }
    }

    @Override
    protected void onResume() {
        super.onResume();
        active=true;

        if(!(updateMap!=null && updateMap.getStatus()== AsyncTask.Status.RUNNING))
        {
            Log.d("onResume","ON Resume");
            updateMap=new UpdateMap();
            updateMap.execute(application.getCurrentAsistencia(),mMap,this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("OnPause",this.toString());
        active=false;
    }

    @Override
    protected void onDestroy() {
        Log.d("OnDestroy",this.toString());
        active=false;
        super.onDestroy();
    }
}



class UpdateMap  extends AsyncTask<Object, Object, Boolean> {

    protected Boolean doInBackground(Object... params ) {
        Log.d("doInBackground","inicio");

        final Asistencia asistencia=(Asistencia) params[0];
        final GoogleMap mapa=(GoogleMap) params[1];
        final FragmentActivity activity=(FragmentActivity) params[2];
        long pasada=0;
        while (asistencia!=null&&mapa!=null&&((MapsActivity)activity).active){

         /*   activity.runOnUiThread(new Thread(new Runnable() {
                public void run() {
                    mapa.clear();
                    dibujarPolilineas(asistencia,mapa);
                    dibujarPuntos(asistencia,mapa);
                }
            }));*/
            publishProgress(asistencia,mapa);

            Log.d("doInBackground",String.valueOf(pasada)+activity.toString());
            pasada++;

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    protected void onProgressUpdate(Object... params) {
        ((GoogleMap)params[1]).clear();
        dibujarPolilineas((Asistencia) params[0],(GoogleMap) params[1]);
        dibujarPuntos((Asistencia) params[0],(GoogleMap) params[1]);
       // setProgressPercent(progress[0]);
    }
    protected void onPostExecute(Long result) {
       // showDialog("Downloaded " + result + " bytes");
    }

    private void dibujarPuntos(Asistencia asistencia,GoogleMap mMap){

        for(Posicion posicion:asistencia.getRecorrido())
        {
            mMap.addMarker(new MarkerOptions().position(
                    new LatLng(posicion.getLatitud(), posicion.getLongitud())
            ).title(posicion.getFecha().toString()));
        }
    }

    private void dibujarPolilineas(Asistencia asistencia,GoogleMap mMap){

        for(int i=0;i< asistencia.getRecorrido().size();i++)
        {
            if(i+1<asistencia.getRecorrido().size()) {
                Posicion posicionIncial =asistencia.getRecorrido().get(i);
                Posicion posicionFinal=asistencia.getRecorrido().get(i+1);

                mMap.addPolyline(new PolylineOptions()
                        .add(new LatLng(
                                        posicionIncial.getLatitud(),
                                        posicionIncial.getLongitud()),
                                new LatLng(
                                        posicionFinal.getLatitud(),
                                        posicionFinal.getLongitud()))
                        .width(5)
                        .color(Color.RED));
            }
        }
    }

}
