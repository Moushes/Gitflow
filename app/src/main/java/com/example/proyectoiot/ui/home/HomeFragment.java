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
import com.example.proyectoiot.presentation.FaqActivity;
import com.example.proyectoiot.presentation.MainActivity;
import com.example.proyectoiot.ui.home.HomeViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Obtener una referencia al botón flotante
        FloatingActionButton fabNotification = root.findViewById(R.id.fab_notification);
        Button faqbuttonme = root.findViewById(R.id.boton_faq);

        // Agregar un OnClickListener al botón flotante
        fabNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        faqbuttonme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(),FaqActivity.class);
                view.getContext().startActivity(intent);

            }
        });


        return root;



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
