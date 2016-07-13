package es.pue.eventos.model.servicelayer.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.pue.eventos.model.businesslayer.entities.Asistencia;
import es.pue.eventos.model.businesslayer.entities.Corredor;
import es.pue.eventos.model.businesslayer.entities.Evento;
import es.pue.eventos.model.businesslayer.entities.Sesion;
import es.pue.eventos.model.persistencelayer.impl.flatfile.manager.FlatFilePersistenceManager;
import es.pue.eventos.model.persistencelayer.impl.rest.manager.RestPersistenceManager;
import es.pue.eventos.model.persistencelayer.impl.sqllite.manager.SqlLitePersistenceManager;
import es.pue.eventos.model.servicelayer.api.IEventoService;
import es.pue.eventos.utilitieslayer.AppUtils;


public class EventoService implements IEventoService {

    //Singletones de gestores de persistencia
    private FlatFilePersistenceManager flatFilePersistenceManager;
    private RestPersistenceManager restPersistenceManager;
    private SqlLitePersistenceManager sqlLitePersistenceManager;

    //Constructor
    public EventoService(
            FlatFilePersistenceManager flatFilePersistenceManager,
            RestPersistenceManager restPersistenceManager,
            SqlLitePersistenceManager sqlLitePersistenceManager){
        this.flatFilePersistenceManager=flatFilePersistenceManager;
        this.restPersistenceManager=restPersistenceManager;
        this.sqlLitePersistenceManager=sqlLitePersistenceManager;
    }

    @Override
    public Evento getEventoByDorsal(
            String dorsal,
            AppUtils.PersistenceTechnologies persistenceTechnologies) {

        Evento evento=null;

        switch (persistenceTechnologies){
            case FLAT_FILE:
                evento=flatFilePersistenceManager.getEventoDAO().getEventoByDorsal(dorsal);
                break;
            case SQL_LITE:
                evento=sqlLitePersistenceManager.getEventoDAO().getEventoByDorsal(dorsal);
                break;
            case REST:
                evento=restPersistenceManager.getEventoDAO().getEventoByDorsal(dorsal);
                break;
        }
        return  evento;
    }

    @Override
    public Asistencia registrarInicioAsistencia(Evento evento) {

        Date fecha= new Date();
        Asistencia asistencia=null;

        for(Sesion sesion:evento.getSesiones())
        {
            if(fecha.after(sesion.getFechaIncio())
                    &&fecha.before(sesion.getFechaFin()))
            {
                asistencia= new Asistencia();
                asistencia.setIncio(new Date());
                asistencia.setCorredor(evento.getInscritos().get(0));
                sesion.getAsistencias().add(asistencia);
                break;
            }
        }
        return asistencia;
    }

    @Override
    public Asistencia registrarFinAsistencia(Asistencia asistencia) {

        if(asistencia!=null){
         asistencia.setFin(new Date());
        }
       return  asistencia;
    }

    @Override

    public void saveEvento(Evento evento) {

    }

    @Override
    public void createInitialLocalEventos() throws ParseException {
        List<Evento> eventos = new ArrayList<Evento>();
        Evento evento = new Evento();
        eventos.add(evento);

        evento.setNombre("Evento1");
        evento.setDescripcion("Descripcion1");
        evento.setNumeroPlazas(50);

        List<Corredor> corredores = new ArrayList<Corredor>();

        Corredor corredor1 = new Corredor();
        corredor1.setNombre("corredor1");
        corredor1.setDorsal("00001");
        corredor1.setEmail("corredor1@eventos.com");
        corredores.add(corredor1);

        Corredor corredor2 = new Corredor();
        corredor2.setNombre("corredor2");
        corredor2.setDorsal("00002");
        corredor2.setEmail("corredor2@eventos.com");
        corredores.add(corredor2);

        Corredor corredor3 = new Corredor();
        corredor3.setNombre("corredor3");
        corredor3.setDorsal("00003");
        corredor3.setEmail("corredor3@eventos.com");
        corredores.add(corredor3);

        Corredor corredor4 = new Corredor();
        corredor4.setNombre("corredor4");
        corredor4.setDorsal("00004");
        corredor4.setEmail("corredor4@eventos.com");
        corredores.add(corredor4);

        Corredor corredor5 = new Corredor();
        corredor5.setNombre("corredor5");
        corredor5.setDorsal("00005");
        corredor5.setEmail("corredor5@eventos.com");
        corredores.add(corredor5);

        Corredor corredor6 = new Corredor();
        corredor6.setNombre("corredor6");
        corredor6.setDorsal("00006");
        corredor6.setEmail("corredor6@eventos.com");
        corredores.add(corredor6);

        evento.setInscritos(corredores);


        Sesion sesion1 = new Sesion();
        sesion1.setFechaIncio(new SimpleDateFormat("dd/MM/yy HH:mm:ss").parse("15/04/2016 9:00:00"));
        sesion1.setFechaFin(new SimpleDateFormat("dd/MM/yy HH:mm:ss").parse("15/04/2017 14:00:00"));
        evento.getSesiones().add(sesion1);

        Sesion sesion2 = new Sesion();
        sesion2.setFechaIncio(new SimpleDateFormat("dd/MM/yy HH:mm:ss").parse("02/01/2017 10:00:00"));
        sesion2.setFechaFin(new SimpleDateFormat("dd/MM/yy HH:mm:ss").parse("02/01/2017 18:00:00"));
        evento.getSesiones().add(sesion2);

        Evento evento2 = new Evento();
        eventos.add(evento2);

        evento2.setNombre("evento21");
        evento2.setDescripcion("Descripcion1");
        evento2.setNumeroPlazas(50);

        List<Corredor> corredores2 = new ArrayList<Corredor>();

        Corredor corredor21 = new Corredor();
        corredor21.setNombre("corredor21");
        corredor21.setDorsal("00021");
        corredor21.setEmail("corredor21@evento2s.com");
        corredores2.add(corredor21);

        Corredor corredor22 = new Corredor();
        corredor22.setNombre("corredor22");
        corredor22.setDorsal("00022");
        corredor22.setEmail("corredor22@evento2s.com");
        corredores2.add(corredor22);

        Corredor corredor23 = new Corredor();
        corredor23.setNombre("corredor23");
        corredor23.setDorsal("00023");
        corredor23.setEmail("corredor23@evento2s.com");
        corredores2.add(corredor23);

        Corredor corredor24 = new Corredor();
        corredor24.setNombre("corredor24");
        corredor24.setDorsal("00024");
        corredor24.setEmail("corredor24@evento2s.com");
        corredores2.add(corredor24);

        Corredor corredor25 = new Corredor();
        corredor25.setNombre("corredor25");
        corredor25.setDorsal("00005");
        corredor25.setEmail("corredor25@evento2s.com");
        corredores2.add(corredor25);

        Corredor corredor26 = new Corredor();
        corredor26.setNombre("corredor26");
        corredor26.setDorsal("00026");
        corredor26.setEmail("corredor26@evento2s.com");
        corredores2.add(corredor26);

        evento2.setInscritos(corredores2);

        Sesion sesion21 = new Sesion();
        sesion21.setFechaIncio(new SimpleDateFormat("dd/MM/yy HH:mm:ss").parse("01/01/2018 10:00:00"));
        sesion21.setFechaFin(new SimpleDateFormat("dd/MM/yy HH:mm:ss").parse("01/01/2018 18:00:00"));
        evento2.getSesiones().add(sesion21);

        Sesion sesion22 = new Sesion();
        sesion22.setFechaIncio(new SimpleDateFormat("dd/MM/yy HH:mm:ss").parse("02/01/2018 10:00:00"));
        sesion22.setFechaFin(new SimpleDateFormat("dd/MM/yy HH:mm:ss").parse("02/01/2018 18:00:00"));
        evento2.getSesiones().add(sesion22);

        flatFilePersistenceManager.getEventoDAO().eventosSave(eventos);

        sqlLitePersistenceManager.getEventoDAO().eventosSave(eventos);

        restPersistenceManager.getEventoDAO().eventosSave(eventos);

    }
}
