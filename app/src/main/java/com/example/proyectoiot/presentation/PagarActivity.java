package com.example.proyectoiot.presentation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectoiot.R;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.comun.Mqtt;
import com.example.proyectoiot.model.Bono;
import com.example.proyectoiot.model.Parking;
import com.example.proyectoiot.model.ParkingAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.io.UnsupportedEncodingException;

public class PagarActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static String matricula="";
    Intent intent = getIntent();
    private Mqtt mqtt = new Mqtt();
    private FirebaseFirestore db;
    private Bono bono;
    //int dias = intent.getIntExtra("Dias", 0);
    //String parking = intent.getStringExtra("Nombre");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pagar);
        // Obtén la instancia de FirebaseAuth
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        // Obtiene el usuario actualmente autenticado
        FirebaseUser user = mAuth.getCurrentUser();
        // El usuario está autenticado, puedes obtener su ID
        String idUsuario = user.getUid();

        Button privado = findViewById(R.id.continuar);

        privado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mqtt.publicar("privada", "1");
                bono.agregarReserva(idUsuario, "Cala", 30, matricula);

            }
        });

        MqttCallback mqttCallback = new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                Log.i(TAG, "Conexión perdida: " + cause);
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.i(TAG, "Mensaje recibido en topic: " + topic + " -> " + new String(message.getPayload()));
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                Log.i(TAG, "Entrega completa: " + token);
            }
        };

        mqtt.suscribir("privada", mqttCallback);

    }
}
