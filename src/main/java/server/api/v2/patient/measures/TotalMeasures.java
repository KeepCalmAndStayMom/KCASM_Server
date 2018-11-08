package server.api.v2.patient.measures;

import server.api.v2.JsonBuilder;
import server.api.v2.Regex;
import server.api.v2.links.MeasuresLinks;
import server.database2.FitbitDB;
import server.database2.HueDB;
import server.database2.SensorDB;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;

public class TotalMeasures {

    public TotalMeasures() {
        getFitbitTotal();
        getHueTotal();
        getSensorTotal();
    }

    private void getSensorTotal() {
        get("/sensor", (request, response) -> {
            int patientId = Integer.parseInt(request.params("patient_id"));
            String date = request.queryParams("date");

            if(date != null && date.matches(Regex.DATE_REGEX)) {
                response.status(200);
                response.type("application/json");
                return JsonBuilder.jsonObject(SensorDB.selectTotal(patientId, date), MeasuresLinks.sensorLinks(patientId, "total", date));
            }

            response.status(404);
            return "ERRORE";
        });
    }

    private void getHueTotal() {
        get("/hue", (request, response) -> {
            int patientId = Integer.parseInt(request.params("patient_id"));
            String date = request.queryParams("date");

            if(date != null && date.matches(Regex.DATE_REGEX)) {
                response.status(200);
                response.type("application/json");
                return JsonBuilder.jsonObject(HueDB.selectTotal(patientId, date), MeasuresLinks.hueLinks(patientId, "total", date));
            }

            response.status(404);
            return "ERRORE";
        });
    }

    private void getFitbitTotal() {
        get("/fitbit", (request, response) -> {
            int patientId = Integer.parseInt(request.params("patient_id"));
            String date = request.queryParams("date");

            if(date != null && date.matches(Regex.DATE_REGEX)) {
                Integer avgHeartbeat = 0;
                List<Map<String, Object>> list = FitbitDB.selectDate(patientId, date);

                if(list != null && list.size() > 0) {
                    for(Map m : list) {
                        if(m.get("avg_heartbeats") != null)
                            avgHeartbeat += (Integer) m.get("avg_heartbeats");
                    }
                    avgHeartbeat /= list.size();

                    Map<String, Object> map = new LinkedHashMap<>();
                    map.put("avg_heartbeats", avgHeartbeat);
                    map.put("calories", list.get(list.size()-1).get("calories"));
                    map.put("elevation", list.get(list.size()-1).get("elevation"));
                    map.put("floors", list.get(list.size()-1).get("floors"));
                    map.put("steps", list.get(list.size()-1).get("steps"));
                    map.put("distance", list.get(list.size()-1).get("distance"));
                    map.put("minutes_asleep", list.get(list.size()-1).get("minutes_asleep"));
                    map.put("minutes_awake", list.get(list.size()-1).get("minutes_awake"));

                    response.status(200);
                    response.type("application/json");
                    return JsonBuilder.jsonObject(map, MeasuresLinks.fitbitLinks(patientId, "total", date));
                }
            }

            response.status(404);
            return "ERRORE";
        });
    }
}
