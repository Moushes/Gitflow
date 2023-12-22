package com.example.proyectoiot.presentation;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.comun.Mqtt;
import com.example.proyectoiot.R;

public class bonoPagadoActivity extends AppCompatActivity {

    private Mqtt mqtt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_bonopagado);

        // Inicializa la instancia de Mqtt
        mqtt = new Mqtt();

        // Puedes suscribirte a los temas aquí si es necesario
        // mqtt.suscribir("entrada", new MqttCallbackListener());
        // mqtt.suscribir("salida", new MqttCallbackListener());
        // mqtt.suscribir("luz", new MqttCallbackListener());
    }

    // Método para manejar el clic en el botón de Entrada
    public void onEntradaClick(View view) {
        String topic = "entrada";
        String message = "1";
        mqtt.publicar(topic, message);
        Toast.makeText(this, "Enviando mensaje a " + topic, Toast.LENGTH_SHORT).show();
    }

    // Método para manejar el clic en el botón de Salida
    public void onSalidaClick(View view) {
        String topic = "salida";
        String message = "1";
        mqtt.publicar(topic, message);
        Toast.makeText(this, "Enviando mensaje a " + topic, Toast.LENGTH_SHORT).show();
    }

    // Método para manejar el clic en el botón de Luz
    public void onLuzClick(View view) {
        String topic = "luz";
        String message = "1";
        mqtt.publicar(topic, message);
        Toast.makeText(this, "Enviando mensaje a " + topic, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Desconecta el cliente MQTT al destruir la actividad
        mqtt.desconectar();
    }
}
