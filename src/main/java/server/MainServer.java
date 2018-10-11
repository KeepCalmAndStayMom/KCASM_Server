package server;

import server.api.ApiService;
import server.peso.ControllerPeso;

public class MainServer {

    public static void main(String[] args) {

        MQTTSubscriber subscriber = new MQTTSubscriber();
        subscriber.start();

        new ApiService();

        new ControllerPeso(1, "2018-10-10", 63);
    }
}