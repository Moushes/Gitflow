package com.example.proyectoiot.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.proyectoiot.R;
import com.example.proyectoiot.databinding.FragmentHomeBinding;
import com.example.proyectoiot.presentation.AcercaDeActivity;
import com.example.proyectoiot.presentation.BuscarParkingActivity;
import com.example.proyectoiot.presentation.FaqActivity;
import com.example.proyectoiot.presentation.MainActivity;
import com.example.proyectoiot.ui.home.HomeViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private TextView usuario;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        usuario=root.findViewById(R.id.home_usuario);
        //final TextView textView = binding.textHome;
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Obtener una referencia al botón flotante
        //FloatingActionButton fabNotification = root.findViewById(R.id.fab_notification);
        Button faqbuttonme = root.findViewById(R.id.boton_faq);
        Button buscarParking=root.findViewById(R.id.boton_Buscar_Parking);
        Button acercaDebuttonme = root.findViewById(R.id.boton_AcercaDe);

        // Agregar un OnClickListener al botón flotante
        //fabNotification.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View view) {

           // }
        //});

        faqbuttonme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(),FaqActivity.class);
                view.getContext().startActivity(intent);

            }
        });
        acercaDebuttonme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), AcercaDeActivity.class);
                view.getContext().startActivity(intent);

            }
        });

        buscarParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(),BuscarParkingActivity.class);
                startActivity(intent);

            }
        });
        // Obtén la instancia de FirebaseAuth
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        // Obtiene el usuario actualmente autenticado
        FirebaseUser user = mAuth.getCurrentUser();
        obtenerInformacionUsuario(user.getUid());

        return root;
    }
    private void obtenerInformacionUsuario(String uid) {
        // Referencia a la colección "Usuarios" en Firestore

        DocumentReference usuarioRef = FirebaseFirestore.getInstance().collection("Usuarios").document(uid);

        usuarioRef.get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // El documento existe, obten el valor del campo "Nombre"
                        String nombreUsuario = documentSnapshot.getString("nombre");
                        // Actualiza la interfaz de usuario con el nombre obtenido
                        usuario.setText(nombreUsuario);
                    } else {
                        // El documento no existe
                        usuario.setText("!Hola¡");
                    }
                });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
