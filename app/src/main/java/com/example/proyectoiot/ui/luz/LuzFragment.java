package com.example.proyectoiot.ui.luz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.proyectoiot.databinding.FragmentLuzBinding;

public class LuzFragment extends Fragment {

    private FragmentLuzBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LuzViewModel LuzViewModel =
                new ViewModelProvider(this).get(LuzViewModel.class);

        binding = FragmentLuzBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textLuz;
        LuzViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}