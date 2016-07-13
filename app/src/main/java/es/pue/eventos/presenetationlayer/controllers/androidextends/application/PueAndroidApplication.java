package es.pue.eventos.presenetationlayer.controllers.androidextends.application;

import android.app.Application;

import es.pue.eventos.model.businesslayer.entities.Asistencia;
import es.pue.eventos.model.businesslayer.entities.Evento;


public class PueAndroidApplication extends Application{


    private Evento evento;

    private Asistencia currentAsistencia;


    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Asistencia getCurrentAsistencia() {
        return currentAsistencia;
    }

    public void setCurrentAsistencia(Asistencia currentAsistencia) {
        this.currentAsistencia = currentAsistencia;
    }
}
