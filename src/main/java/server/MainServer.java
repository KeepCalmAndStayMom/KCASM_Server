package server;

import server.weight_control.ControllerPesoTest;

import java.time.LocalDate;

public class MainServer {

    public static ControllerPesoTest cpt;

    public static void main(String[] args) {

        cpt = new ControllerPesoTest();

        /*
        MQTTSubscriber subscriber = new MQTTSubscriber();
        subscriber.start();

        new ApiService();*/

        cpt.startcheck(1, LocalDate.parse("2018-01-02"), 0d);
        //cpt.startcheck(2, LocalDate.parse("2018-03-01"), 1.5d);
        //cpt.startcheck(3, LocalDate.parse("2018-03-01"), 3d);
        //cpt.startcheck(4, LocalDate.parse("2018-03-01"), 0d);
    }
}