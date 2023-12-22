package com.example.proyectoiot.presentation;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoiot.R;
import com.example.proyectoiot.model.Parking;
import com.example.proyectoiot.model.ParkingAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;

public class BuscarParkingActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ParkingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_buscarparking);


        // Inicializar el RecyclerView
        recyclerView = findViewById(R.id.recyclerView);

        // Configurar el LayoutManager (puedes usar LinearLayoutManager, GridLayoutManager, etc.)
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Configurar el adaptador
        FirestoreRecyclerOptions<Parking> options = new FirestoreRecyclerOptions.Builder<Parking>()
                .setQuery(FirebaseFirestore.getInstance().collection("Parkings"), Parking.class)
                .build();

        adapter = new ParkingAdapter(options);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Iniciar la escucha del adaptador cuando la actividad está visible
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Detener la escucha del adaptador cuando la actividad no está visible
        adapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}
