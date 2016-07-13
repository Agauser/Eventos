package es.pue.eventos.model.businesslayer.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.pue.eventos.model.businesslayer.entities.base.EntityBase;


public class Asistencia extends EntityBase {

    private Date incio;
    private Date fin;
    private List<Posicion> recorrido;
    private Corredor corredor;

    public Date getIncio() {
        return incio;
    }

    public void setIncio(Date incio) {
        this.incio = incio;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    public List<Posicion> getRecorrido() {
        return recorrido;
    }

    public void setRecorrido(List<Posicion> recorrido) {
        this.recorrido = recorrido;
    }

    public Corredor getCorredor() {
        return corredor;
    }

    public void setCorredor(Corredor corredor) {
        this.corredor = corredor;
    }

    public Asistencia(){
        incio=null;
        fin=null;
        recorrido=new ArrayList<Posicion>();
        corredor=null;
    }
}
