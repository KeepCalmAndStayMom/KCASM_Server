package server;

import server.api.ApiService;
import server.mqtt.MQTTSubscriber;
import server.net_influence.NetSmile;
import server.net_influence.NetSmileRetePeso;
import server.peso.ControllerPeso;

import java.time.LocalDate;

public class MainServer {

    public static NetSmile net;

    public static void main(String[] args) {

        MQTTSubscriber subscriber = new MQTTSubscriber();
        subscriber.start();

        new ApiService();

        net = new NetSmileRetePeso();

        ControllerPeso.startcheck(1, LocalDate.parse("2018-01-15"), 61);
        ControllerPeso.startcheck(2, LocalDate.parse("2018-05-10"), 70);
        ControllerPeso.startcheck(3, LocalDate.parse("2018-06-07"), 72);
    }
}