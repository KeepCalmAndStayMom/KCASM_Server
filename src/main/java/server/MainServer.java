package server;

import server.api.ApiService;
import server.peso.ControllerPeso;

public class MainServer {

    public static void main(String[] args) {

        MQTTSubscriber subscriber = new MQTTSubscriber();
        subscriber.start();

        new ApiService();

        new ControllerPeso(1, "2018-01-15", 61);
        new ControllerPeso(2, "2018-05-10", 70);
        new ControllerPeso(3, "2018-06-07", 72);
    }
}