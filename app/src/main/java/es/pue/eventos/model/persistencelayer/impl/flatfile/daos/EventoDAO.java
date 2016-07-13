package es.pue.eventos.model.persistencelayer.impl.flatfile.daos;


import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import es.pue.eventos.model.businesslayer.entities.Corredor;
import es.pue.eventos.model.businesslayer.entities.Evento;
import es.pue.eventos.model.businesslayer.entities.Sesion;
import es.pue.eventos.model.persistencelayer.api.IEventoDAO;


public class EventoDAO implements IEventoDAO {

    private Context context;

    public EventoDAO(Context context){
        this.context=context;
    }

    @Override
    public Evento getEventoByDorsal(String dorsal) {

        List<Evento>eventos =getEventosFromFlatFile();

        Evento evento=null;

        for(Evento eventoTemp:eventos)
        {
            for(Corredor corredor:eventoTemp.getInscritos())
            {
                if(corredor.getDorsal().equals(dorsal))
                {
                    eventoTemp.setInscritos(new ArrayList<Corredor>());
                    eventoTemp.getInscritos().add(corredor);
                    evento=eventoTemp;
                    break;
                }
            }
        }
        return evento;
    }

    @Override
    public void eventosSave(List<Evento> eventos) {

        Gson gson = new Gson();
        String json = gson.toJson(eventos);
        OutputStreamWriter osr=null;
        try{
            osr= new OutputStreamWriter(
                    context.openFileOutput("eventos.json",
                            Context.MODE_PRIVATE));
            osr.write(json);
            osr.close();
        }catch(Exception ex){
            throw
                    new RuntimeException(
                            "Petazo al guardar los eventos en disco!!!");
        }
    }

    private List<Evento> getEventosFromFlatFile(){

        Gson gson = new Gson();

        String eventosDesdeFiceroJSON="";
        try{
            BufferedReader br=
                    new BufferedReader(
                            new InputStreamReader(
                                    context.openFileInput("eventos.json")
                            )
                    );
            eventosDesdeFiceroJSON=br.readLine();
            br.close();
        }
        catch(Exception ex){}
        ArrayList<Evento> eventos =
                gson.fromJson(eventosDesdeFiceroJSON, new TypeToken<List<Evento>>(){}.getType());

        return  eventos;

    }



}
