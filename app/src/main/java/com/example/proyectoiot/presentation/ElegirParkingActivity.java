package com.example.proyectoiot.presentation;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoiot.R;
import com.example.proyectoiot.model.BonoParkingAdapter;
import com.example.proyectoiot.model.Parking;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ElegirParkingActivity extends AppCompatActivity {

    private BonoParkingAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ventana_emergente_bono);
        Intent intent = getIntent();
        int dias = intent.getIntExtra("days", 0);

        // Configura la referencia a la colección de Firestore
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference collection = firestore.collection("Parkings"); // Reemplaza con el nombre de tu colección

        // Configura la consulta para obtener los documentos de Firestore
        Query query = collection.orderBy("nombre"); // Ajusta según tu lógica de orden

        // Configura las opciones para el adaptador
        FirestoreRecyclerOptions<Parking> options = new FirestoreRecyclerOptions.Builder<Parking>()
                .setQuery(query, Parking.class)
                .build();

        // Crea el adaptador con las opciones
        adapter = new BonoParkingAdapter(options,dias);

        // Configura el RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Inicia la observación de Firestore cuando la actividad está en primer plano
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Detiene la observación de Firestore cuando la actividad está en segundo plano
        adapter.stopListening();
    }
}