package com.example.comun;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class Mqtt {
    public static final String TAG = "MQTT";
    public static final String topicRoot = "gti/grupo2-3/"; // Reemplaza jtomas
    public static final int qos = 1;
    public static final String broker = "tcp://test.mosquitto.org:1883";
    public static final String clientId = "Test1313"; // Reemplaza

    private MqttClient client;
    private int texto = 0; // Nueva variable para almacenar el valor

    public Mqtt() {
        try {
            client = new MqttClient(broker, clientId, new MemoryPersistence());
            client.connect();
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    public String publicar(String topic, String mensageStr) {
        try {
            if (topic.equals("publica")) {
                // Si el topic es "publica", actualiza la variable texto
                texto = Integer.parseInt(mensageStr);
            }
            MqttMessage message = new MqttMessage(mensageStr.getBytes());
            message.setQos(qos);
            message.setRetained(false);
            client.publish(topicRoot + topic, message);
            return "Publicando mensaje: " + topic + "->" + mensageStr;
        } catch (MqttException e) {
            return "Error al publicar en MQTT." + e;
        }
    }

    public String desconectar() {
        try {
            client.disconnect();
            return "Desconectado";
        } catch (MqttException e) {
            return "Error al desconectar.";
        }
    }

    public String suscribir(String topic, MqttCallback listener) {
        try {
            client.subscribe(topicRoot + topic, qos);
            client.setCallback(listener);
            return "Suscrito a " + topicRoot + topic;
        } catch (MqttException e) {
            return "Error al suscribir." + e;
        }
    }

    public int getValorTexto() {
        return texto;
    }
}
