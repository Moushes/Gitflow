package com.example.proyectoiot.presentation;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;


import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.comun.Mqtt;
import com.example.proyectoiot.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class VistaParkingActivity extends AppCompatActivity
        implements OnMapReadyCallback {
    private TextView Nombre,localizacion,plazaslibres,plazasocupadas;
    private GoogleMap mapa;

    private Mqtt mqtt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_parking);
        // Asociar TextViews con sus respectivos elementos en el layout
        Nombre = findViewById(R.id.parkingNombre); // Asegúrate de que coincida con el ID en tu layout
        localizacion = findViewById(R.id.parkingCalle); // Asegúrate de que coincida con el ID en tu layout
        plazaslibres = findViewById(R.id.libre);
        plazasocupadas = findViewById(R.id.ocupada);
        String nombre = getIntent().getStringExtra("Nombre");
        String lugar= getIntent().getStringExtra("Lugar");
        String plazas = getIntent().getStringExtra("Plazas");

        Nombre.setText(nombre);
        localizacion.setText(lugar);
        plazaslibres.setText(plazas);

        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);



        //mqtt

        mqtt = new Mqtt();
        mqtt.suscribir("publica", new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                //Log.d(TAG, "Mensaje recibido: " + message.toString());
                onSuccess(topic, message);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // You can also use Intent to navigate back without finishing the MainActivity
    }

    @Override public void onMapReady(GoogleMap googleMap) {
        String nombre = getIntent().getStringExtra("Nombre");
        mapa = googleMap;
        mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (ActivityCompat.checkSelfPermission(this,
                ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mapa.setMyLocationEnabled(true);
            mapa.getUiSettings().setZoomControlsEnabled(true);
            mapa.getUiSettings().setCompassEnabled(true);
            mapa.addMarker(new MarkerOptions().position(new LatLng(123, 123))
                    .title(nombre));
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mqtt.desconectar();
    }


    private void onSuccess(String topic, MqttMessage message) {
        if (message.toString().equals("1")) {
            int currentValue = Integer.parseInt(plazasocupadas.getText().toString());
            runOnUiThread(() -> plazasocupadas.setText(String.valueOf(currentValue + 1)));
            int currentValue1 = Integer.parseInt(plazaslibres.getText().toString());
            runOnUiThread(() -> plazaslibres.setText(String.valueOf(currentValue1 - 1)));
        }

        if (message.toString().equals("0")) {
            int currentValue = Integer.parseInt(plazasocupadas.getText().toString());
            runOnUiThread(() -> plazasocupadas.setText(String.valueOf(currentValue - 1)));
            int currentValue1 = Integer.parseInt(plazaslibres.getText().toString());
            runOnUiThread(() -> plazaslibres.setText(String.valueOf(currentValue1 + 1)));
        }
    }
}


