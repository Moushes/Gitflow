package com.example.proyectoiot.presentation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoiot.R;
import com.example.proyectoiot.model.Bono;
import com.example.proyectoiot.model.BonoCompradosAdapter;
import com.example.proyectoiot.model.HistorialAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class HistorialActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    // Add other UI elements as needed
    private HistorialAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_historial);
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference collection = firestore.collection("Reservas"); // Reemplaza con el nombre de tu colección

        // Use the same query condition as in cambiarfecha method
        Query query = collection.whereEqualTo("idUsuario", FirebaseAuth.getInstance().getCurrentUser().getUid())
                .orderBy("fecha");

        // Configura las opciones para el adaptador
        FirestoreRecyclerOptions<Bono> options = new FirestoreRecyclerOptions.Builder<Bono>()
                .setQuery(query, Bono.class)
                .build();

        // Crea el adaptador con las opciones
        adapter = new HistorialAdapter(options);

        // Configura el RecyclerView
        recyclerView =findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
