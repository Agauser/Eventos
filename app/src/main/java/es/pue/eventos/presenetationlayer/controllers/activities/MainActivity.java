package es.pue.eventos.presenetationlayer.controllers.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.MapsInitializer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import es.pue.eventos.R;
import es.pue.eventos.model.businesslayer.entities.Asistencia;
import es.pue.eventos.model.businesslayer.entities.Corredor;
import es.pue.eventos.model.businesslayer.entities.Evento;
import es.pue.eventos.model.businesslayer.entities.Sesion;
import es.pue.eventos.model.servicelayer.manager.ServiceManager;
import es.pue.eventos.presenetationlayer.controllers.androidextends.application.PueAndroidApplication;
import es.pue.eventos.presenetationlayer.controllers.broadcastreceivers.LlamadasBroadcastReceiver;
import es.pue.eventos.presenetationlayer.controllers.services.GeoPositionService;
import es.pue.eventos.utilitieslayer.AppUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ServiceManager serviceManager= null;
    private EditText main_etDorsal=null;
    private PueAndroidApplication application;
    private final int GPS_PERMISIONS_REQUEST=1;
    private Intent intentService;
    private Intent intentMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendBroadcast(new Intent(this, LlamadasBroadcastReceiver.class));

        ((ImageButton)findViewById(R.id.main_ibtStartStop)).setOnClickListener(this);
        serviceManager=new ServiceManager(this);
        try {
            serviceManager.getEventoServices().createInitialLocalEventos();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        application= (PueAndroidApplication) this.getApplication();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        intentMap=new Intent(this,MapsActivity.class);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(intentMap);

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        main_etDorsal = (EditText) findViewById(R.id.main_etDorsal);
        main_etDorsal.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    private Timer timer = new Timer();
                    private final long DELAY = 1000; // milliseconds

                    @Override
                    public void afterTextChanged(final Editable s) {
                        timer.cancel();
                        timer = new Timer();
                        timer.schedule(
                                new TimerTask() {
                                    @Override
                                    public void run() {
                                 application.setEvento(getEventoByDorsal());
                                    }
                                },
                                DELAY
                        );
                    }
                }
        );


    }

    private void main_ibtStartStop_Visibility(int visibility)
    {
        ((ImageButton)findViewById(R.id.main_ibtStartStop)).setVisibility(visibility);
    }

    private Evento getEventoByDorsal(){

        String dorsal=main_etDorsal.getText().toString();
        final Evento evento=
                serviceManager.getEventoServices().getEventoByDorsal(dorsal,
                        AppUtils.PersistenceTechnologies.FLAT_FILE);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(evento!=null)
                {
                    ((ImageButton)findViewById(R.id.main_ibtStartStop))
                            .setVisibility(View.VISIBLE);
                }else{
                    ((ImageButton)findViewById(R.id.main_ibtStartStop))
                            .setVisibility(View.INVISIBLE);
                }
            }
        });
        return evento;
    }

    public void solicitarPermisosGPS(){

            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    GPS_PERMISIONS_REQUEST);

    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults){

            if(requestCode==GPS_PERMISIONS_REQUEST)
            {
                boolean permisosOK=true;
               for (int permission :grantResults){
                   if(permission==PackageManager.PERMISSION_DENIED){
                       permisosOK=false;
                   }
               }
                if(permisosOK){
                    iniciarAsistencia();
                }
                else{
                    Toast.makeText(this,"Sin permisos de gps no funciona!!",Toast.LENGTH_LONG).show();
                }
            }


    }
    private void iniciarAsistencia(){

        ((ImageButton) findViewById(R.id.main_ibtStartStop)).setImageResource(R.drawable.stop);
        ((EditText)findViewById(R.id.main_etDorsal)).setEnabled(false);

        Asistencia asistencia=serviceManager.getEventoServices().registrarInicioAsistencia(
                application.getEvento());

        if(asistencia!=null) {
            application.setCurrentAsistencia(asistencia);
            intentService=new Intent(this, GeoPositionService.class);
            startService(intentService);

        }
        else{
            Toast.makeText(this,"No hay sesiones ahora mismo!",Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.main_ibtStartStop){
            if(((ImageButton) v).getDrawable().getConstantState()
                    ==getDrawable(R.drawable.start).getConstantState()){

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    solicitarPermisosGPS();
                }
                else {
                    iniciarAsistencia();
                }
            }
            else{
                ((ImageButton) v).setImageResource(R.drawable.start);
                ((EditText)findViewById(R.id.main_etDorsal)).setEnabled(true);

                application.setCurrentAsistencia(
                        serviceManager.getEventoServices().registrarFinAsistencia(
                                application.getCurrentAsistencia()
                        ));

            }
        }
    }

    public void onDestroy(){
        if(intentService!=null)
        {
        stopService(intentService);
        }
        super.onDestroy();
    }
}
