package es.pue.eventos.model.persistencelayer.impl.sqllite.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Date;
import java.util.List;

import es.pue.eventos.model.businesslayer.entities.Corredor;
import es.pue.eventos.model.businesslayer.entities.Evento;
import es.pue.eventos.model.persistencelayer.api.IEventoDAO;
import es.pue.eventos.model.persistencelayer.impl.sqllite.helpers.SQLliteDBHelper;
import es.pue.eventos.utilitieslayer.AppUtils;


public class EventoDAO implements IEventoDAO {

    private SQLiteDatabase db=null;
    private SQLliteDBHelper dbHelper=null;

    public EventoDAO(SQLliteDBHelper dbHelper)
    {
     this.dbHelper=dbHelper;
    }

    @Override
    public Evento getEventoByDorsal(String Dorsal) {

        if(db==null || !db.isOpen())
        {
            db=dbHelper.getReadableDatabase();
        }

        Cursor cursor = db.query(
                AppUtils.TABLA_EVENTOS,
                null,null,null,null,null,null
        );

        Gson gson = new Gson();
        Evento evento=null;
        boolean encontrado=false;
        while (cursor.moveToNext()&&!encontrado)
        {
            evento=gson.fromJson(
                    cursor.getString(
                    cursor.getColumnIndex(AppUtils.TABLA_EVENTOS_EVENTO)),
                    new TypeToken<Evento>(){}.getType()
            );
            for(Corredor corredor:evento.getInscritos())
            {
                if(corredor.getDorsal().equals(Dorsal)){
                    encontrado=true;
                }
            }
        }
        cursor.close();
        return  evento;
    }

    @Override
    public void eventosSave(List<Evento> eventos) {

        if(db==null || !db.isOpen())
        {
            db=dbHelper.getWritableDatabase();
        }

        for(Evento evento:eventos)
        {

            Gson gson = new Gson();
            String json;
            ContentValues contentValues=new ContentValues();


            //Si nunca hemos guardado el evento, hacemos un insert
            if(evento.getInsertedDBDate()==null)
            {
                int _id=0;
                Cursor cursor=db.query(
                        "sqlite_sequence",
                        new String[]{"seq"},
                        "name=?",
                        new String[]{"eventos"},
                        null,
                        null,
                        null
                );



                if(cursor.moveToFirst())
                {
                    _id= cursor.getInt(cursor.getColumnIndex("seq"));
                }
                _id++;

                evento.set_id(_id);
                evento.setInsertedDBDate(new Date());
                json = gson.toJson(evento);
                contentValues.put(
                        AppUtils.TABLA_EVENTOS_EVENTO,json);

                db.insert(AppUtils.TABLA_EVENTOS,
                          null,
                          contentValues);
            }
            //Hacemos un update
            else{
                json = gson.toJson(evento);
                contentValues.put(
                        AppUtils.TABLA_EVENTOS_EVENTO,json);

                db.update(
                        AppUtils.TABLA_EVENTOS,
                        contentValues,
                        AppUtils.TABLA_EVENTOS_ID +"=?",
                        new String[]{String.valueOf(evento.get_id())}
                );
            }
        }
        if(db.isOpen())
        {
            db.close();
        }
    }
}
