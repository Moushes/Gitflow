package com.example.proyectoiot.model;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Bono {
    private String matricula;
    private int duracion; // asumiendo que la duración es un entero, ajusta según tus necesidades
    private Parking parking;

    private long fechaInicio;

    private int Disponibilidad;

    public int getDisponibilidad() {
        return Disponibilidad;
    }

    public void setDisponibilidad(int disponibilidad) {
        Disponibilidad = disponibilidad;
    }

    // Constructor
    public Bono(String matricula, int duracion, Parking parking, long fechaInicio) {
        this.matricula = matricula;
        this.duracion = duracion;
        this.parking = parking;
        this.fechaInicio = fechaInicio;
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

    public Parking getParking() {
        return parking;
    }

    public void setParking(Parking parking) {
        this.parking = parking;
    }

    public long getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(long fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void agregarReserva(String idUsuario, String nombreParking, int duracion, String matricula) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference usuarioRef = db.collection("Usuarios").document(idUsuario);

        long fechaInicioReserva = System.currentTimeMillis(); // Obtiene la marca de tiempo actual

        Map<String, Object> nuevaReserva = new HashMap<>();
        nuevaReserva.put("parking", nombreParking);
        nuevaReserva.put("duracion", duracion);
        nuevaReserva.put("matricula", matricula);
        nuevaReserva.put("disponibilidad", 1);
        nuevaReserva.put("fecha", fechaInicioReserva); // Agrega la fecha de inicio

        usuarioRef.collection("Reservas").add(nuevaReserva)
                .addOnSuccessListener(documentReference -> {
                    System.out.println("Reserva añadida con ID: " + documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    System.err.println("Error al añadir la reserva: " + e.getMessage());
                });
    }

}

