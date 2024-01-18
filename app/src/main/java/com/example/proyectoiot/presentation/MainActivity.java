package com.example.proyectoiot.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectoiot.R;
import com.example.proyectoiot.model.Parking;
import com.example.proyectoiot.presentation.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectoiot.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.example.comun.Mqtt;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private FirebaseAuth auth;
    private TextView bienvenida;

    private Mqtt mqtt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            // Mostrar un Toast indicando que ha fallado al cargar el usuario
            Toast.makeText(this, "Fallo al cargar el usuario", Toast.LENGTH_SHORT).show();
        }
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top-level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications,R.id.navigation_mis_bonos)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);


        mqtt = new Mqtt();
        Button entradaButton = findViewById(R.id.Entrada);
        Button salidaButton = findViewById(R.id.Salida);
        Button luzButton = findViewById(R.id.Luz);

        //cambiarfecha();
        entradaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEntradaClick();
            }
        });

        salidaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSalidaClick();
            }
        });

        luzButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLuzClick();
            }
        });
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    public void buscarParking(View view) {


    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }




    public void cerrarsesion(MenuItem item) {
        // Cerrar la sesión actual del usuario
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish(); // Cierra la actividad actual
    }


    // Método para manejar el clic en el botón de Entrada
    public void onEntradaClick() {
        String topic = "entrada";
        String message = "1";
        mqtt.publicar(topic, message);
        Toast.makeText(requireContext(), "Enviando mensaje a " + topic, Toast.LENGTH_SHORT).show();
    }

    // Método para manejar el clic en el botón de Salida
    public void onSalidaClick() {
        String topic = "salida";
        String message = "1";
        mqtt.publicar(topic, message);
        Toast.makeText(requireContext(), "Enviando mensaje a " + topic, Toast.LENGTH_SHORT).show();
    }

    // Método para manejar el clic en el botón de Luz
    public void onLuzClick() {
        String topic = "luz";
        String message = "1";
        mqtt.publicar(topic, message);
        Toast.makeText(requireContext(), "Enviando mensaje a " + topic, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Desconecta el cliente MQTT al destruir la vista del fragmento
        mqtt.desconectar();
    }

}

