package com.example.proyectoiot.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.comun.Mqtt;
import com.example.proyectoiot.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;

public class bonoPagadoActivity extends Fragment {
    private Mqtt mqtt;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_misbonos, container, false);
        // Inicializa la instancia de Mqtt
        mqtt = new Mqtt();
        Button entradaButton = view.findViewById(R.id.Entrada);
        Button salidaButton = view.findViewById(R.id.Salida);
        Button luzButton = view.findViewById(R.id.Luz);

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

        return view;

        // Puedes suscribirte a los temas aquí si es necesario
        // mqtt.suscribir("entrada", new MqttCallbackListener());
        // mqtt.suscribir("salida", new MqttCallbackListener());
        // mqtt.suscribir("luz", new MqttCallbackListener());
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
