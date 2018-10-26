package server.api.v1;

import com.google.gson.Gson;
import server.database.FitbitDB;
import java.util.Map;
import static spark.Spark.get;
import static spark.Spark.post;

class ApiFitbit {

    private Gson gson = new Gson();
    private String baseURL = "/api";

    ApiFitbit(){
        sparkGetFitbit();
        sparkGetFitbitWithDate();
        sparkGetFitbitWithInterval();
        sparkPostFitbit();
        sparkGetFitbitTotalWithDate();
    }

    private void sparkGetFitbit() {
        get(baseURL + "/fitbit/:homestation_id", "application/json", (request, response) -> {

            String json = FitbitDB.getFitbit(Integer.valueOf(request.params(":homestation_id")));
            response.type("application/json");

            if (json == null || json.equals("}")) {
                response.status(404);
                return "{ \"Errore 404\": \"risorsa non trovata\" }";
            }

            response.status(200);
            return json;
        });
    }

    private void sparkGetFitbitWithDate() {
        get(baseURL + "/fitbit/:homestation_id/:date", "application/json", (request, response) -> {

            String json = FitbitDB.getFitbitWithDate(Integer.valueOf(request.params(":homestation_id")), request.params(":date"));
            response.type("application/json");

            if (json == null || json.equals("}")) {
                response.status(404);
                return "{ \"Errore 404\": \"risorsa non trovata\" }";
            }

            response.status(200);
            return json;
        });
    }

    private void sparkGetFitbitWithInterval() {
        get(baseURL + "/fitbit/:homestation_id/:startdate/:enddate", "application/json", (request, response) -> {

            String json = FitbitDB.getFitbitWithInterval(Integer.valueOf(request.params(":homestation_id")), request.params(":startdate"), request.params(":enddate"));
            response.type("application/json");

            if (json == null || json.equals("}")) {
                response.status(404);
                return "{ \"Errore 404\": \"risorsa non trovata\" }";
            }

            response.status(200);
            return json;
        });
    }

    private void sparkPostFitbit() {
        post(baseURL + "/fitbit", (request, response) -> {

            Map addRequest = gson.fromJson(request.body(), Map.class);
            response.type("application/json");

            if (addRequest!=null && addRequest.containsKey("homestation_id")
                    && addRequest.containsKey("timedate")
                    && addRequest.containsKey("calories")
                    && addRequest.containsKey("elevation")
                    && addRequest.containsKey("floors")
                    && addRequest.containsKey("steps")
                    && addRequest.containsKey("distance")
                    && addRequest.containsKey("minutesAsleep")
                    && addRequest.containsKey("minutesAwake"))
            {
                Integer homestation_id = ((Double) addRequest.get("homestation_id")).intValue();
                String timedate = String.valueOf(addRequest.get("timedate"));
                Integer calories = ((Double) addRequest.get("calories")).intValue();
                Float elevation = ((Double) addRequest.get("elevation")).floatValue();
                Integer floors = ((Double) addRequest.get("floors")).intValue();
                Integer steps = ((Double) addRequest.get("steps")).intValue();
                Double distance = (Double) addRequest.get("distance");
                Integer minutesAsleep = ((Double) addRequest.get("minutesAsleep")).intValue();
                Integer minutesAwake = ((Double) addRequest.get("minutesAwake")).intValue();

                Integer avg_heartbeats = null;
                if(addRequest.containsKey("avg_heartbeats"))
                    if (addRequest.get("avg_heartbeats") != null)
                        avg_heartbeats = ((Double) addRequest.get("avg_heartbeats")).intValue();

                if(!FitbitDB.addFitbit(homestation_id, timedate, avg_heartbeats, calories, elevation, floors, steps, distance, minutesAsleep, minutesAwake)) {
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

    private void sparkGetFitbitTotalWithDate() {
        get(baseURL + "/fitbit_total/:homestation_id/:date", "application/json", (request, response) -> {

            String json = FitbitDB.getFitbitWithDate(Integer.valueOf(request.params(":homestation_id")), request.params(":date"));
            response.type("application/json");

            if (json == null || json.equals("}")) {
                response.status(404);
                return "{ \"Errore 404\": \"risorsa non trovata\" }";
            }

            Map<String, Map> map = gson.fromJson(json, Map.class);
            Integer avgHeart = 0;
            StringBuilder builder = new StringBuilder();
            String[] keys = map.keySet().toArray(new String[0]);

            for (int i = 0; i < keys.length; i++) {
                if (map.get(keys[i]).get("avg_heartbeats") != null)
                    avgHeart += ((Double) map.get(keys[i]).get("avg_heartbeats")).intValue();

                if (i == keys.length - 1) {
                    if (avgHeart == 0)
                        avgHeart = null;
                    else
                        avgHeart = avgHeart/keys.length;
                    builder.append("{\"avg_heartbeats\": ").append(avgHeart).append(",");
                    builder.append("\"calories\": ").append(map.get(keys[i]).get("calories")).append(",");
                    builder.append("\"elevation\": ").append(map.get(keys[i]).get("elevation")).append(",");
                    builder.append("\"floors\": ").append(map.get(keys[i]).get("floors")).append(",");
                    builder.append("\"steps\": ").append(map.get(keys[i]).get("steps")).append(",");
                    builder.append("\"distance\": ").append(map.get(keys[i]).get("distance")).append(",");
                    builder.append("\"minutesAsleep\": ").append(map.get(keys[i]).get("minutesAsleep")).append(",");
                    builder.append("\"minutesAwake\": ").append(map.get(keys[i]).get("minutesAwake")).append("}");
                }
            }

            response.status(200);
            return builder.toString();
        });
    }
}
