package com.example.proyectoiot.presentation;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectoiot.R;

public class VistaParkingActivity extends AppCompatActivity {
    private TextView Nombre,localizacion,plazaslibres;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_parking);
        // Asociar TextViews con sus respectivos elementos en el layout
        Nombre = findViewById(R.id.parkingNombre); // Asegúrate de que coincida con el ID en tu layout
        localizacion = findViewById(R.id.parkingCalle); // Asegúrate de que coincida con el ID en tu layout
        plazaslibres = findViewById(R.id.parkingPlazas);
        String nombre = getIntent().getStringExtra("Nombre");
        String lugar= getIntent().getStringExtra("Lugar");
        String plazas = getIntent().getStringExtra("Plazas");

        Nombre.setText(nombre);
        localizacion.setText(lugar);
        plazaslibres.setText(plazas);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // You can also use Intent to navigate back without finishing the MainActivity
    }

}
