package server.api.v1;

import com.google.gson.Gson;
import server.database.AttivitaDB;

import java.util.List;
import java.util.Map;

import static spark.Spark.get;

class ApiAttivita {
    private Gson gson = new Gson();
    private String baseURL = "/api";

    ApiAttivita() {
        sparkGetActivitiesOfTheDay();
    }

    private void sparkGetActivitiesOfTheDay() {
        get(baseURL + "/activities/:id/:date", "application/json", (request, response) -> {

            List<Map<String, Object>> result = AttivitaDB.getActivitiesOfTheDay(Integer.valueOf(request.params(":id")), request.params(":date"));
            response.type("application/json");

            if (result == null || result.size() == 0) {
                response.status(404);
                return "{\"Errore 404\": \"risorsa non trovata\"}";
            }

            String json = gson.toJson(result);

            response.status(200);
            return "{\"activities\": " + json + "}";
        });
    }
}