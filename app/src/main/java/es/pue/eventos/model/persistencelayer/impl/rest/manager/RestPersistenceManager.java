package es.pue.eventos.model.persistencelayer.impl.rest.manager;

import es.pue.eventos.model.persistencelayer.api.IEventoDAO;
import es.pue.eventos.model.persistencelayer.impl.rest.daos.EventoDAO;
import es.pue.eventos.model.persistencelayer.manager.PersistenceManager;


public class RestPersistenceManager extends PersistenceManager {

    private IEventoDAO eventoDAO;

    @Override
    public IEventoDAO getEventoDAO() {
        if(eventoDAO==null)
        {
            eventoDAO=new EventoDAO();
        }
        return eventoDAO;
    }
}
