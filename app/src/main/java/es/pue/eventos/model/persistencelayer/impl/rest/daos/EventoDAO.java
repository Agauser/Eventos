package es.pue.eventos.model.persistencelayer.impl.rest.daos;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import es.pue.eventos.model.businesslayer.entities.Evento;
import es.pue.eventos.model.persistencelayer.api.IEventoDAO;


public class EventoDAO implements IEventoDAO {
    @Override
    public Evento getEventoByDorsal(String Dorsal) {
        new GetEventoByDorsal().execute(Dorsal);
        return null;
    }

    @Override
    public void eventosSave(List<Evento> eventos) {

            new SaveEvento().execute(eventos);
    }
}

class SaveEvento extends AsyncTask<List<Evento>,Intent,Boolean>
{
    @Override
    protected Boolean doInBackground(List<Evento>... params) {

        URL url= null;
        try {
            url = new URL("http://192.168.50.140");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type","application/json");
            connection.setDoOutput(true);
            connection.connect();

            Gson gson = new Gson();
            String json = gson.toJson((List<Evento>)params[0]);

            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(json);
            wr.flush();

            int responseCode = connection.getResponseCode(); // getting the response code
            final StringBuilder output = new StringBuilder("Request URL " + url);
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            StringBuilder responseOutput = new StringBuilder();
            System.out.println("output===============" + br);
            while((line = br.readLine()) != null ) {
                responseOutput.append(line);
            }
            br.close();


        } catch (Exception e) {
            Log.e("Error en el post",e.getMessage());
        }
        return null;

    }
}

class GetEventoByDorsal  extends AsyncTask<String,Intent,Boolean>
{
    @Override
    protected Boolean doInBackground(String... params) {
        try {
            HttpURLConnection c = null;
            URL u = null;
            u = new URL("http://192.168.50.140");
            c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setRequestProperty("Content-length", "0");
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);
            c.connect();
            int status = c.getResponseCode();

            switch (status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    Log.d("Respuesta HTTP",sb.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
        }
}
