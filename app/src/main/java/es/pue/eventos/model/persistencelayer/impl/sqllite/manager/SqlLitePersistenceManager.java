package es.pue.eventos.model.persistencelayer.impl.sqllite.manager;

import android.content.Context;

import es.pue.eventos.model.persistencelayer.api.IEventoDAO;
import es.pue.eventos.model.persistencelayer.impl.sqllite.daos.EventoDAO;
import es.pue.eventos.model.persistencelayer.impl.sqllite.helpers.SQLliteDBHelper;
import es.pue.eventos.model.persistencelayer.manager.PersistenceManager;
import es.pue.eventos.utilitieslayer.AppUtils;


public class SqlLitePersistenceManager extends PersistenceManager {

    private IEventoDAO eventoDAO;
    private SQLliteDBHelper dbHelper=null;
    private Context context;

    public SqlLitePersistenceManager(Context context){
        this.context=context;
        this.dbHelper=
                new SQLliteDBHelper(
                        context,
                        AppUtils.EVENTOS_DB,
                        null,
                        AppUtils.EVENTOS_DB_VERSION
                );
    }

    @Override
    public IEventoDAO getEventoDAO() {
        if(eventoDAO==null)
        {
            eventoDAO=new EventoDAO(dbHelper);
        }
        return eventoDAO;
    }
}
