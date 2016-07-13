package es.pue.eventos.model.persistencelayer.api;

import android.content.Context;

import java.util.List;

import es.pue.eventos.model.businesslayer.entities.Evento;


public interface IEventoDAO {

    Evento getEventoByDorsal(String Dorsal);

    void eventosSave(List<Evento> eventos);

}
