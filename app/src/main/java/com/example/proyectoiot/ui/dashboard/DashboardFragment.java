package com.example.proyectoiot.ui.dashboard;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoiot.R;
import com.example.proyectoiot.databinding.FragmentDashboardBinding;
import com.example.proyectoiot.model.Parking;
import com.example.proyectoiot.model.ParkingAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;


public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private RecyclerView recyclerView;
    private ParkingAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Inicializar el RecyclerView
        recyclerView = root.findViewById(R.id.recyclerView);

        // Configurar el LayoutManager (puedes usar LinearLayoutManager, GridLayoutManager, etc.)
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Configurar el adaptador
        FirestoreRecyclerOptions<Parking> options = new FirestoreRecyclerOptions.Builder<Parking>()
                .setQuery(FirebaseFirestore.getInstance().collection("Parkings"), Parking.class)
                .build();

        adapter = new ParkingAdapter(options);
        recyclerView.setAdapter(adapter);

        // Agregar un OnClickListener al botón flotante para abrir el mapa de Google
        FloatingActionButton fabMap = root.findViewById(R.id.fab_map);
        fabMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Abrir el mapa de Google
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=latitude,longitude(label)");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Iniciar la escucha del adaptador cuando el fragmento está visible
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        // Detener la escucha del adaptador cuando el fragmento no está visible
        adapter.stopListening();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}