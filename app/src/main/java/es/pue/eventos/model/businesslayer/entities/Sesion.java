package es.pue.eventos.model.businesslayer.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.pue.eventos.model.businesslayer.entities.base.EntityBase;


public class Sesion extends EntityBase {

    private Date fechaIncio;
    private Date fechaFin;
    private List<Asistencia> asistencias;

    public Date getFechaIncio() {
        return fechaIncio;
    }

    public void setFechaIncio(Date fechaIncio) {
        this.fechaIncio = fechaIncio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public List<Asistencia> getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(List<Asistencia> asistencias) {
        this.asistencias = asistencias;
    }

    public Sesion(){
        fechaIncio=null;
        fechaFin=null;
        asistencias=new ArrayList<Asistencia>();
    }
}
