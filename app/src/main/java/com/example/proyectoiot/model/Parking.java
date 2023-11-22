package com.example.proyectoiot.model;

public class Parking {
    private String nombre;
    private String direccion;
    private GeoPunto posicion;
    private int plazas;


    public Parking() {
    }
    public Parking (String nombre, String direccion, double longitud,
                    double latitud, int plazas){
        this.nombre = nombre;
        this.direccion = direccion;
        posicion = new GeoPunto(longitud, latitud);
        this.plazas=plazas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public GeoPunto getPosicion() {
        return posicion;
    }

    public void setPosicion(GeoPunto posicion) {
        this.posicion = posicion;
    }
    public int getPlazas() {
        return plazas;
    }

    public void setPlazas (int plazas) {
        this.plazas = plazas;
    }
    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", direccion=" + direccion +
                ", posicion=" + posicion +
                ",plazas=" +plazas+
                '}';
    }
}
