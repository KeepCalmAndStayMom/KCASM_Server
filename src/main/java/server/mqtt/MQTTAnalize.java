package server.mqtt;

import com.google.gson.Gson;
import server.database.FitbitDB;
import server.database.HueDB;
import server.database.ZWayDB;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

class MQTTAnalize {

    private Gson gson = new Gson();

    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    MQTTAnalize() { }

    void analizeTopic(String topic, String messagge) {

        String homestation_id = topic.split("/")[1];
        String top = topic.split("/")[2];
        String timedate = dtf.format(LocalDateTime.now());

        switch(top) {
            case "hue":
                Map hue = gson.fromJson(messagge, Map.class);

                HueDB.addHue(Integer.valueOf(homestation_id),
                                timedate,
                                ((Double)hue.get("cromoSoft")).intValue(),
                                ((Double)hue.get("cromoHard")).intValue());
                break;
            case "zway":
                Map zway = gson.fromJson(messagge, Map.class);

                ZWayDB.addZWay(Integer.valueOf(homestation_id),
                                timedate,
                                String.valueOf(zway.get("movement")),
                                ((Double)zway.get("temperature")).intValue(),
                                ((Double)zway.get("luminescence")).intValue(),
                                ((Double)zway.get("humidity")).intValue());
                break;
            case "fitbit":
                Map<String, Map> type = gson.fromJson(messagge, Map.class);
                Map activities = type.get("Activities");
                Map sleep = type.get("Sleep");
                Map heartrate = type.get("HeartRate");

                Integer heartbeats;

                if (heartrate.get("heartbeats") == null)
                    heartbeats = null;
                else
                   heartbeats = ((Double)heartrate.get("heartbeats")).intValue();

                FitbitDB.addFitbit(Integer.valueOf(homestation_id),
                                    timedate,
                                    heartbeats,
                                    ((Double)activities.get("activitiesCalories")).intValue(),
                                    ((Double)activities.get("elevation")).floatValue(),
                                    ((Double)activities.get("floors")).intValue(),
                                    ((Double)activities.get("steps")).intValue(),
                                    (Double) activities.get("distance"),
                                    ((Double)sleep.get("minutesAsleep")).intValue(),
                                    ((Double)sleep.get("minutesAwake")).intValue());
                break;
        }
    }
}