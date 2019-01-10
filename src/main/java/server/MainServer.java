package server;

import server.api.v2.medic.ApiMedic;
import server.api.v2.ApiServiceV2;
import server.mqtt.MQTTSubscriber;
import server.weight_control.WeightController;

public class MainServer {

    public static WeightController weightController;

    public static void main(String[] args) {
        weightController = new WeightController();

        MQTTSubscriber subscriber = new MQTTSubscriber();
        subscriber.start();

        //new ApiService();
        new ApiServiceV2();
        new ApiMedic();

        /*System.out.println("start");
        weightController.startcheck(1, LocalDate.parse("2018-01-02"), 0d);
        weightController.startcheck(2, LocalDate.parse("2018-03-01"), 1.5d);
        weightController.startcheck(3, LocalDate.parse("2018-03-01"), 3d);
        weightController.startcheck(4, LocalDate.parse("2018-03-01"), 0d);
        System.out.println("end");*/

    }
}