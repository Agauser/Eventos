package es.pue.eventos.model.persistencelayer.manager;

import android.content.Context;

import es.pue.eventos.model.persistencelayer.api.IEventoDAO;
import es.pue.eventos.model.persistencelayer.impl.flatfile.manager.FlatFilePersistenceManager;
import es.pue.eventos.model.persistencelayer.impl.rest.manager.RestPersistenceManager;
import es.pue.eventos.model.persistencelayer.impl.sqllite.manager.SqlLitePersistenceManager;
import es.pue.eventos.utilitieslayer.AppUtils;

public abstract class PersistenceManager {

    //Metodos Abstractos
    public abstract IEventoDAO getEventoDAO();

    public static PersistenceManager getPersistenceManager
            (AppUtils.PersistenceTechnologies persisistenceTechnologies, Context context){

        PersistenceManager persistenceManager=null;

        switch (persisistenceTechnologies){
            case FLAT_FILE:
                persistenceManager= new FlatFilePersistenceManager(context);
                break;
            case REST:
                persistenceManager= new RestPersistenceManager();
                break;
            case SQL_LITE:
                persistenceManager= new SqlLitePersistenceManager(context);
                break;
        }

        return  persistenceManager;
    }
}
