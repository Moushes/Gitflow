package com.example.proyectoiot.model;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Bono {
    private String matricula;
    private int duracion; // asumiendo que la duración es un entero, ajusta según tus necesidades
    private String parking;

    private long fechaInicio;

    private int Disponibilidad;

    public int getDisponibilidad() {
        return Disponibilidad;
    }

    public void setDisponibilidad(int disponibilidad) {
        Disponibilidad = disponibilidad;
    }

    // Constructor
    public Bono(String matricula, int duracion,String parking) {
        this.matricula = matricula;
        this.duracion = duracion;
        this.parking = parking;
    }
    public Bono(){

    }

    // Métodos getters y setters (puedes generarlos automáticamente en tu IDE)
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getParking() {
        return parking;
    }

    public void setParking(String parking) {
        this.parking = parking;
    }

    public long getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(long fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void agregarReserva(String idUsuario) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        long fechaInicioReserva = System.currentTimeMillis(); // Obtiene la marca de tiempo actual

        Map<String, Object> nuevaReserva = new HashMap<>();
        nuevaReserva.put("idUsuario", idUsuario);
        nuevaReserva.put("parking", parking); // Ajusta según la estructura de la clase Parking
        nuevaReserva.put("duracion", duracion);
        nuevaReserva.put("matricula", matricula);
        nuevaReserva.put("disponibilidad", 1);
        nuevaReserva.put("fecha", fechaInicioReserva); // Agrega la fecha de inicio

        db.collection("Reservas").add(nuevaReserva)
                .addOnSuccessListener(documentReference -> {
                    System.out.println("Reserva añadida con ID: " + documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    System.err.println("Error al añadir la reserva: " + e.getMessage());
                });
    }



}

