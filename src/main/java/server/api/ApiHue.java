package server.api;

import com.google.gson.Gson;
import server.database.HueDB;
import java.util.Map;
import static spark.Spark.get;
import static spark.Spark.post;

class ApiHue {

    private Gson gson = new Gson();
    private String baseURL = "/api";

    ApiHue() {
        sparkGetHue();
        sparkGetHueWithDate();
        sparkGetHueWithInterval();
        sparkPostHue();
        sparkGetHueTotalWithDate();
        sparkGetHueTotalWithInterval();
    }

    private void sparkGetHue() {
        get(baseURL + "/hue/:homestation_id", "application/json", (request, response) -> {

            String json = HueDB.getHue(Integer.valueOf(request.params(":homestation_id")));
            response.type("application/json");

            if (json == null || json.equals("}")) {
                response.status(404);
                return "{ \"Errore 404\": \"risorsa non trovata\" }";
            }

            response.status(200);
            return json;
        });
    }

    private void sparkGetHueWithDate() {
        get(baseURL + "/hue/:homestation_id/:date", "application/json", (request, response) -> {

            String json = HueDB.getHueWithDate(Integer.valueOf(request.params(":homestation_id")), request.params(":date"));
            response.type("application/json");

            if (json == null || json.equals("}")) {
                response.status(404);
                return "{ \"Errore 404\": \"risorsa non trovata\" }";
            }

            response.status(200);
            return json;
        });
    }

    private void sparkGetHueWithInterval() {
        get(baseURL + "/hue/:homestation_id/:startdate/:enddate", "application/json", (request, response) -> {

            String json = HueDB.getHueWithDateInterval(Integer.valueOf(request.params(":homestation_id")), request.params(":startdate"), request.params(":enddate"));
            response.type("application/json");

            if (json == null || json.equals("}")) {
                response.status(404);
                return "{ \"Errore 404\": \"risorsa non trovata\" }";
            }

            response.status(200);
            return json;
        });
    }

    private void sparkGetHueTotalWithDate() {
        get(baseURL + "/hue_total/:homestation_id/:date", "application/json", (request, response) -> {

            String json = HueDB.getHueWithDate(Integer.valueOf(request.params(":homestation_id")), request.params(":date"));
            response.type("application/json");

            if (json == null || json.equals("}")) {
                response.status(404);
                return "{ \"Errore 404\": \"risorsa non trovata\" }";
            }

            Map<String, Map> map = gson.fromJson(json, Map.class);
            StringBuilder builder = new StringBuilder();
            Integer totSoft = 0, totHard = 0;

            for (String s : map.keySet()) {
                totSoft += ((Double) map.get(s).get("cromosoft")).intValue();
                totHard += ((Double) map.get(s).get("cromohard")).intValue();
            }

            builder.append("{\"cromosoft\": ").append(totSoft).append(",\"cromohard\": ").append(totHard).append("}");

            response.status(200);
            return builder.toString();
        });
    }

    private void sparkGetHueTotalWithInterval() {
        get(baseURL + "/hue_total/:homestation_id/:startdate/:enddate", "application/json", (request, response) -> {

            String json = HueDB.getHueWithDateInterval(Integer.valueOf(request.params(":homestation_id")), request.params(":startdate"), request.params(":enddate"));
            response.type("application/json");

            if (json == null || json.equals("}")) {
                response.status(404);
                return "{ \"Errore 404\": \"risorsa non trovata\" }";
            }

            Map<String, Map> map = gson.fromJson(json, Map.class);
            StringBuilder builder = new StringBuilder();
            Integer totSoft = 0, totHard = 0;

            for(String s : map.keySet()) {
                totSoft += ((Double) map.get(s).get("cromosoft")).intValue();
                totHard += ((Double) map.get(s).get("cromohard")).intValue();
            }

            builder.append("{\"cromosoft\": ").append(totSoft).append(",\"cromohard\": ").append(totHard).append("}");

            response.status(200);
            return builder.toString();
        });
    }

    private void sparkPostHue() {
        post(baseURL + "/hue", (request, response) -> {

            Map addRequest = gson.fromJson(request.body(), Map.class);
            response.type("application/json");

            if (addRequest != null && addRequest.containsKey("homestation_id")
                    && addRequest.containsKey("timedate")
                    && addRequest.containsKey("cromosoft")
                    && addRequest.containsKey("cromohard"))
            {
                Integer homestation_id = ((Double) addRequest.get("homestation_id")).intValue();
                String timedate = String.valueOf(addRequest.get("timedate"));
                Integer cromosoft = ((Double) addRequest.get("cromosoft")).intValue();
                Integer cromohard = ((Double) addRequest.get("cromohard")).intValue();

                if(!HueDB.addHue(homestation_id, timedate, cromosoft, cromohard)) {
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
