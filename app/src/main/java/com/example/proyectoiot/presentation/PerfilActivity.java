package com.example.proyectoiot.presentation;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectoiot.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PerfilActivity extends AppCompatActivity {
    private TextView correo,nombre,matricula;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil);
        nombre=findViewById(R.id.nombre_perfil);
        correo=findViewById(R.id.correo_perfil);
        matricula=findViewById(R.id.matricula_perfil);
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        nombre.setText(usuario.getDisplayName());
        correo.setText(usuario.getEmail());
        String uid = usuario.getUid();
    }

}
