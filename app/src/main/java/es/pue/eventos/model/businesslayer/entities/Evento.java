package es.pue.eventos.model.businesslayer.entities;

import java.util.ArrayList;
import java.util.List;

import es.pue.eventos.model.businesslayer.entities.base.EntityBase;


public class Evento extends EntityBase {

    private String nombre;
    private String descripcion;
    private int numeroPlazas;
    private List<Corredor> inscritos;
    private List<Sesion> sesiones;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getNumeroPlazas() {
        return numeroPlazas;
    }

    public void setNumeroPlazas(int numeroPlazas) {
        this.numeroPlazas = numeroPlazas;
    }

    public List<Corredor> getInscritos() {
        return inscritos;
    }

    public void setInscritos(List<Corredor> inscritos) {
        this.inscritos = inscritos;
    }

    public List<Sesion> getSesiones() {
        return sesiones;
    }

    public void setSesiones(List<Sesion> sesiones) {
        this.sesiones = sesiones;
    }

    public Evento(){
          nombre="";
          descripcion="";
          numeroPlazas=0;
          inscritos=new ArrayList<Corredor>();
          sesiones=new ArrayList<Sesion>();
    }

}
