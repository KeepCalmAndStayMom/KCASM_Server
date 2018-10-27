package server;

import server.api.v1.ApiService;
import server.api.v2.ApiPatient;
import server.mqtt.MQTTSubscriber;
import server.weight_control.WeightController;

import java.time.LocalDate;

public class MainServer {

    public static WeightController cpt;

    public static void main(String[] args) {
        cpt = new WeightController();

        MQTTSubscriber subscriber = new MQTTSubscriber();
        subscriber.start();

        //new ApiService();
        new ApiPatient();

        //cpt.startcheck(1, LocalDate.parse("2018-01-02"), 0d);
        //cpt.startcheck(2, LocalDate.parse("2018-03-01"), 1.5d);
        //cpt.startcheck(3, LocalDate.parse("2018-03-01"), 3d);
        //cpt.startcheck(4, LocalDate.parse("2018-03-01"), 0d);
    }
}