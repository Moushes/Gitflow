package com.example.proyectoiot.ui.Parking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.proyectoiot.databinding.FragmentParkingBinding;

public class ParkingFragment extends Fragment {

    private FragmentParkingBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ParkingViewModel ParkingViewModel =
                new ViewModelProvider(this).get(ParkingViewModel.class);

        binding = FragmentParkingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textParking;
        ParkingViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}