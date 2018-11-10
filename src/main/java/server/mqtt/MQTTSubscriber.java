package server.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import server.database.v1.HomestationDB;

public class MQTTSubscriber {

    private static final String BROKER_URL = "tcp://localhost:1883";
    private static MqttClient mqttClient;

    public MQTTSubscriber() {
        try {
            mqttClient = new MqttClient(BROKER_URL, MqttClient.generateClientId());

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        try {
            mqttClient.setCallback(new SubscribeCallBack());
            mqttClient.connect();

            updateSubscriber();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private static void updateSubscriber() throws MqttException {
        for (String s : HomestationDB.getAllHomestations())
            mqttClient.subscribe("homestation/" + s + "/#");
    }
}