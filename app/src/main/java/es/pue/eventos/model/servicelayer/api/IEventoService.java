package es.pue.eventos.model.servicelayer.api;

import java.text.ParseException;

import es.pue.eventos.model.businesslayer.entities.Asistencia;
import es.pue.eventos.model.businesslayer.entities.Evento;
import es.pue.eventos.utilitieslayer.AppUtils;


public interface IEventoService {

    Evento getEventoByDorsal(String dorsal
    ,  AppUtils.PersistenceTechnologies persistenceTechnologies);

    Asistencia registrarInicioAsistencia(Evento evento);

    Asistencia registrarFinAsistencia(Asistencia asistencia);

    void saveEvento(Evento evento);

    void createInitialLocalEventos() throws ParseException;

}
