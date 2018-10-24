package server;

import server.api.ApiService;
import server.mqtt.MQTTSubscriber;
import server.netsmiledecorator.ControllerPesoTest;

import java.time.LocalDate;

public class MainServer {

    public static void main(String[] args) {

        /*
        MQTTSubscriber subscriber = new MQTTSubscriber();
        subscriber.start();

        new ApiService();*/

        ControllerPesoTest cpt = new ControllerPesoTest();

        cpt.startcheck(2, LocalDate.parse("2018-05-10"), 70d);
    }
}