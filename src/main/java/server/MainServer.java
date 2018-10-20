package server;

import server.api.ApiService;
import server.mqtt.MQTTSubscriber;
import server.peso.ControllerPeso;
import java.time.LocalDate;

public class MainServer {

    public static void main(String[] args) {

        MQTTSubscriber subscriber = new MQTTSubscriber();
        subscriber.start();

        new ApiService();

    }
}