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

        //cpt.startcheck(1, LocalDate.parse("2018-01-02"), 0d);
        //cpt.startcheck(2, LocalDate.parse("2018-03-01"), 1.5d);
        //cpt.startcheck(3, LocalDate.parse("2018-03-01"), 3d);
        //cpt.startcheck(4, LocalDate.parse("2018-03-01"), 0d);
    }
}