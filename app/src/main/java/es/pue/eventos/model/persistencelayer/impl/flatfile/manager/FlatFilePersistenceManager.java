package es.pue.eventos.model.persistencelayer.impl.flatfile.manager;

import android.content.Context;

import es.pue.eventos.model.persistencelayer.api.IEventoDAO;
import es.pue.eventos.model.persistencelayer.impl.flatfile.daos.EventoDAO;
import es.pue.eventos.model.persistencelayer.manager.PersistenceManager;


public class FlatFilePersistenceManager extends PersistenceManager {

    private Context context;

    private IEventoDAO eventoDAO;

    public FlatFilePersistenceManager(Context context){

        this.context=context;
    }

    @Override
    public IEventoDAO getEventoDAO() {
        if(eventoDAO==null)
        {
            eventoDAO=new EventoDAO(context);
        }
        return eventoDAO;
    }
}
