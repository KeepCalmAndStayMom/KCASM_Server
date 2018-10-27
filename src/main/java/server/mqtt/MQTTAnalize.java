package server.mqtt;

import com.google.gson.Gson;
import server.database2.FitbitDB;
import server.database2.HueDB;
import server.database2.SensorDB;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

class MQTTAnalize {

    private Gson gson = new Gson();

    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    MQTTAnalize() { }

    void analizeTopic(String topic, String messagge) {

        String homestation_id = topic.split("/")[1];
        String top = topic.split("/")[2];
        String timedate = dtf.format(LocalDateTime.now());
        Map<String, Object> map;

        switch(top) {
            case "hue":
                Map hue = gson.fromJson(messagge, Map.class);

                map = new HashMap<>();
                map.put("Patient_id", Integer.valueOf(homestation_id));
                map.put("timedate", timedate);
                map.put("cromoterapia", String.valueOf(hue.get("cromoterapia")));
                HueDB.Insert(map);

                break;
            case "zway":
                Map zway = gson.fromJson(messagge, Map.class);

                map = new HashMap<>();
                map.put("Patient_id", Integer.valueOf(homestation_id));
                map.put("timedate", timedate);
                map.put("temperature", zway.get("temperature"));
                map.put("luminescence", zway.get("luminescence"));
                map.put("humidity", zway.get("humidity"));
                SensorDB.Insert(map);

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

                map = new HashMap<>();
                map.put("Patient_id", Integer.valueOf(homestation_id));
                map.put("timedate", timedate);
                map.put("avg_heartbeats", heartbeats);
                map.put("calories", activities.get("activitiesCalories"));
                map.put("elevation", activities.get("elevation"));
                map.put("floors", activities.get("floors"));
                map.put("steps", activities.get("steps"));
                map.put("distance", activities.get("distance"));
                map.put("minutes_sleep", sleep.get("minutesAsleep"));
                map.put("minutes_awake", sleep.get("minutesAwake"));
                FitbitDB.insert(map);

                break;
        }
    }
}