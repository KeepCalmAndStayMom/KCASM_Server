package server;

import server.api.ApiService;
import server.net_influence.NetSmile;
import server.peso.ControllerPeso;
import server.peso.SogliePeso;

public class MainServer {

    public static void main(String[] args) {

        MQTTSubscriber subscriber = new MQTTSubscriber();
        subscriber.start();

        new ApiService();

        new ControllerPeso(1, "2018-04-08", 63);
    }
}