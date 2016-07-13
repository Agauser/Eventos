package es.pue.eventos.model.servicelayer.manager;

import android.content.Context;

import es.pue.eventos.model.persistencelayer.impl.flatfile.manager.FlatFilePersistenceManager;
import es.pue.eventos.model.persistencelayer.impl.rest.manager.RestPersistenceManager;
import es.pue.eventos.model.persistencelayer.impl.sqllite.manager.SqlLitePersistenceManager;
import es.pue.eventos.model.persistencelayer.manager.PersistenceManager;
import es.pue.eventos.model.servicelayer.api.IEventoService;
import es.pue.eventos.model.servicelayer.impl.EventoService;
import es.pue.eventos.utilitieslayer.AppUtils;


public class ServiceManager {

    private Context context;

    //Singletones de gestores de persistencia
    private FlatFilePersistenceManager flatFilePersistenceManager;
    private RestPersistenceManager restPersistenceManager;
    private SqlLitePersistenceManager sqlLitePersistenceManager;

    //Servicios
   private IEventoService eventoServices;


   //Constructor
    public ServiceManager(Context context){
        this.context=context;
        flatFilePersistenceManager=(FlatFilePersistenceManager)
                PersistenceManager.getPersistenceManager(
                AppUtils.PersistenceTechnologies.FLAT_FILE,context);

        restPersistenceManager=(RestPersistenceManager)
                PersistenceManager.getPersistenceManager(
                        AppUtils.PersistenceTechnologies.REST,context);

        sqlLitePersistenceManager=(SqlLitePersistenceManager)
                PersistenceManager.getPersistenceManager(
                        AppUtils.PersistenceTechnologies.SQL_LITE,context);
    }

    //Inicialización del gestor de servicios
    public IEventoService getEventoServices(){
        if(eventoServices==null){
            eventoServices=
                    new EventoService(
                            flatFilePersistenceManager,
                            restPersistenceManager,
                            sqlLitePersistenceManager
                            );
        }
        return eventoServices;
    }

}
