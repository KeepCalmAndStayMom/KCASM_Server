package server.api;

import com.google.gson.Gson;
import server.database.ZWayDB;

import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;

class ApiZway {

    private Gson gson = new Gson();
    private String baseURL = "/api";

    ApiZway(){
        sparkGetZWay();
        sparkGetZWayWithDate();
        sparkGetZWayWithInterval();
        sparkPostZWay();
        sparkGetZWayAvgWithDate();
        sparkGetZWayAvgWithInterval();
    }

    private void sparkGetZWay() {
        get(baseURL + "/zway/:homestation_id", "application/json", (request, response) -> {

            String json = ZWayDB.getZWay(Integer.valueOf(request.params(":homestation_id")));
            response.type("application/json");

            if (json == null || json.equals("}")) {
                response.status(404);
                return "{ \"Errore 404\": \"risorsa non trovata\" }";
            }

            response.status(200);
            return json;
        });

    }

    private void sparkGetZWayWithDate() {
        get(baseURL + "/zway/:homestation_id/:date", "application/json", (request, response) -> {

            String json = ZWayDB.getZWayWithDate(Integer.valueOf(request.params(":homestation_id")), request.params(":date"));
            response.type("application/json");

            if (json == null || json.equals("}")) {
                response.status(404);
                return "{ \"Errore 404\": \"risorsa non trovata\" }";
            }

            response.status(200);
            return json;
        });
    }

    private void sparkGetZWayWithInterval() {
        get(baseURL + "/zway/:homestation_id/:startdate/:enddate", "application/json", (request, response) -> {

            String json = ZWayDB.getZWayWithDateInterval(Integer.valueOf(request.params(":homestation_id")), request.params(":startdate"), request.params(":enddate"));
            response.type("application/json");

            if (json == null || json.equals("}")) {
                response.status(404);
                return "{ \"Errore 404\": \"risorsa non trovata\" }";
            }

            response.status(200);
            return json;
        });
    }

    private void sparkGetZWayAvgWithDate() {
        get(baseURL + "/zway_avg/:homestation_id/:date", "application/json", (request, response) -> {

            String json = ZWayDB.getZWayWithDate(Integer.valueOf(request.params(":homestation_id")), request.params(":date"));
            response.type("application/json");

            if (json == null || json.equals("}")) {
                response.status(404);
                return "{ \"Errore 404\": \"risorsa non trovata\" }";
            }

            Map<String, Map> map = gson.fromJson(json, Map.class);
            StringBuilder builder = new StringBuilder();
            Integer totTemp = 0, totHum = 0, totLum = 0;

            for(String s : map.keySet()) {
                totTemp += ((Double) map.get(s).get("temperature")).intValue();
                totHum += ((Double) map.get(s).get("humidity")).intValue();
                totLum += ((Double) map.get(s).get("luminescence")).intValue();
            }

            builder.append("{\"temperature\": ").append(totTemp/map.size()).append(",\"humidity\": ").append(totHum/map.size()).append(",\"luminescence\": ").append(totLum/map.size()).append("}");

            response.status(200);
            return builder.toString();
        });
    }

    private void sparkGetZWayAvgWithInterval() {
        get(baseURL + "/zway_avg/:homestation_id/:startdate/:enddate", "application/json", (request, response) -> {

            String json = ZWayDB.getZWayWithDateInterval(Integer.valueOf(request.params(":homestation_id")), request.params(":startdate"), request.params(":enddate"));
            response.type("application/json");

            if (json == null || json.equals("}")) {
                response.status(404);
                return "{ \"Errore 404\": \"risorsa non trovata\" }";
            }

            Map<String, Map> map = gson.fromJson(json, Map.class);
            StringBuilder builder = new StringBuilder();
            Integer totTemp = 0, totHum = 0, totLum = 0;

            for(String s : map.keySet()) {
                totTemp += ((Double) map.get(s).get("temperature")).intValue();
                totHum += ((Double) map.get(s).get("humidity")).intValue();
                totLum += ((Double) map.get(s).get("luminescence")).intValue();
            }

            builder.append("{\"temperature\": ").append(totTemp/map.size()).append(",\"humidity\": ").append(totHum/map.size()).append(",\"luminescence\": ").append(totLum/map.size()).append("}");

            response.status(200);
            return builder.toString();
        });
    }

    private void sparkPostZWay() {
        post(baseURL + "/zway", (request, response) -> {

            Map addRequest = gson.fromJson(request.body(), Map.class);
            response.type("application/json");

            if (addRequest != null && addRequest.containsKey("homestation_id")
                    && addRequest.containsKey("timedate")
                    && addRequest.containsKey("movement")
                    && addRequest.containsKey("temperature")
                    && addRequest.containsKey("luminescence")
                    && addRequest.containsKey("humidity"))
            {
                Integer homestation_id = ((Double) addRequest.get("homestation_id")).intValue();
                String timedate = String.valueOf(addRequest.get("timedate"));
                String movement = String.valueOf(addRequest.get("movement"));
                Integer temperature = ((Double) addRequest.get("temperature")).intValue();
                Integer luminescence = ((Double) addRequest.get("luminescence")).intValue();
                Integer humidity = ((Double) addRequest.get("humidity")).intValue();

                if(!ZWayDB.addZWay(homestation_id, timedate, movement, temperature, luminescence, humidity)) {
                    response.status(400);
                    return "{\"Errore 400\": \"richiesta non valida\" }";
                }
            }
            else {
                response.status(400);
                return "{\"Errore 400\": \"richiesta non valida\" }";
            }

            response.status(201);
            return "{ \"Success\":\"risorsa aggiunta\" }";
        });
    }
}
