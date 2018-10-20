package server;

import server.api.ApiService;
import server.mqtt.MQTTSubscriber;
import server.peso.ControllerPeso;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import server.database2.*;

public class MainServer {

    public static void main(String[] args) {

        /*MQTTSubscriber subscriber = new MQTTSubscriber();
        subscriber.start();

        new ApiService();

        ControllerPeso.startcheck(1, LocalDate.parse("2018-01-15"), 61);
        ControllerPeso.startcheck(2, LocalDate.parse("2018-05-10"), 70);
        ControllerPeso.startcheck(3, LocalDate.parse("2018-06-07"), 72);*/

        //test database2

        Map<String, Object> map = new HashMap<>();
        map.put("name", "test");
        map.put("surname", "test");
        map.put("age", 1);
        map.put("phone", "test");
        map.put("address_home", "test");
        map.put("address_hospital", "test");

        PatientDB.Insert(map);
    }
}