package server.api.v1;

import com.google.gson.Gson;
import server.database.v1.PesoDB;
import java.util.Map;

import static spark.Spark.post;

class ApiPeso {

    private Gson gson = new Gson();
    private String baseURL = "/api";

    ApiPeso(){
        sparkPostPeso();
    }

    private void sparkPostPeso() {
        post(baseURL + "/peso", (request, response) -> {

            Map addRequest = gson.fromJson(request.body(), Map.class);
            response.type("application/json");

            if (addRequest != null && addRequest.containsKey("homestation_id")
                    && addRequest.containsKey("data")
                    && addRequest.containsKey("peso"))
            {
                Integer homestation_id = ((Double) addRequest.get("homestation_id")).intValue();
                String data = String.valueOf(addRequest.get("data"));
                Float peso = ((Double) addRequest.get("peso")).floatValue();

                PesoDB.addPeso(homestation_id, data, peso);
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
