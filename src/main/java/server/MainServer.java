package server;

import server.api.ApiService;

public class MainServer {

    public static void main(String[] args) {

        MQTTSubscriber subscriber = new MQTTSubscriber();
        subscriber.start();

        new ApiService();

        //new ControllerPeso(1, "2018-04-08", 63);
    }
}