package server.api;

import com.google.gson.Gson;
import server.database.PesoDB;
import server.database.TaskDB;
import server.peso.ControllerPeso;

import java.util.Map;

import static spark.Spark.post;

public class ApiPeso {

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

                if(PesoDB.addPeso(homestation_id, data, peso))
                    new ControllerPeso(homestation_id, data, peso);
                else
                {
                    response.status(400);
                    return "{\"Errore 400\": \"risorsa non aggiunta\" }";
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
