package com.example.proyectoiot.ui.dashboard;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.proyectoiot.model.Usuario;
import com.example.proyectoiot.presentation.ElegirParkingActivity;
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

        /*

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
         */

        Button botonSemanal = root.findViewById(R.id.botonSemanal);
        Button botonAnual = root.findViewById(R.id.BotonAnual);
        Button botonMensual = root.findViewById(R.id.botonMensual);

        botonSemanal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openElegirParkingActivity(7); // 7 days for weekly
            }
        });

        botonAnual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openElegirParkingActivity(365); // 365 days for yearly
            }
        });

        botonMensual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openElegirParkingActivity(30); // 30 days for monthly
            }
        });


        return root;



    }

    private void openElegirParkingActivity(int days) {
        Intent intent = new Intent(getActivity(), ElegirParkingActivity.class);
        intent.putExtra("days", days);
        startActivity(intent);
    }

    /*@Override
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
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}