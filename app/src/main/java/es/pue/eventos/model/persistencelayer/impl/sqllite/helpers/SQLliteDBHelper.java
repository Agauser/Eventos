package es.pue.eventos.model.persistencelayer.impl.sqllite.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import es.pue.eventos.utilitieslayer.AppUtils;


public class SQLliteDBHelper extends SQLiteOpenHelper{

    public SQLliteDBHelper(Context context, String name,
                           SQLiteDatabase.CursorFactory factory,
                           int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ");
        sb.append(AppUtils.TABLA_EVENTOS);
        sb.append(" (");
        sb.append(AppUtils.TABLA_EVENTOS_ID);
        sb.append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sb.append(AppUtils.TABLA_EVENTOS_EVENTO);
        sb.append(" TEXT");
        sb.append(" )");

        Log.d("Creación base de datos",sb.toString());

        db.execSQL(sb.toString());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if(oldVersion==1 && newVersion==2)
        {

        }

        if(oldVersion==1 && newVersion==3)
        {

        }
        if(oldVersion==2 && newVersion==3)
        {

        }

    }
}
