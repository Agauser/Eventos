package es.pue.eventos.model.businesslayer.entities;

import es.pue.eventos.model.businesslayer.entities.base.EntityBase;


public class Corredor extends EntityBase {

    private String nombre;
    private String email;
    private String dorsal;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDorsal() {
        return dorsal;
    }

    public void setDorsal(String dorsal) {
        this.dorsal = dorsal;
    }

    public Corredor(){
        nombre="";
        email="";
        dorsal="";
    }
}
