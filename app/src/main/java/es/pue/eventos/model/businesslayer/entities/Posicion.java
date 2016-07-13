package es.pue.eventos.model.businesslayer.entities;

import java.util.Date;

import es.pue.eventos.model.businesslayer.entities.base.EntityBase;


public class Posicion extends EntityBase {

    private double latitud;
    private double longitud;
    private double precision;
    private Date fecha;

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getPrecision() {
        return precision;
    }

    public void setPrecision(double precision) {
        this.precision = precision;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Posicion(){
        latitud=0;
        longitud=0;
        precision=0;
        fecha=null;
    }
}
