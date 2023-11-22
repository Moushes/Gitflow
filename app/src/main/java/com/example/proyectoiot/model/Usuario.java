package com.example.proyectoiot.model;

public class Usuario {
    private String nombre;
    private String correo;
    private String matricula;
    private String uid;

    public Usuario(String nombre,String correo,String matricula){
        this.nombre=nombre;
        this.correo=correo;
        this.matricula=matricula;
    }
    public Usuario(String nombre, String correo, String matricula, String uid) {
        this.nombre = nombre;
        this.correo = correo;
        this.matricula = matricula;
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNombre() {return nombre;}

    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getCorreo() {return correo;}

    public void setCorreo(String correo) {this.correo = correo;}

    public String getMatricula() {return matricula;}

    public void setMatricula(String matricula) {this.matricula = matricula;}

    /*@Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", correo=" + correo +
                ", matricula=" + matricula +
                '}';*/
}
