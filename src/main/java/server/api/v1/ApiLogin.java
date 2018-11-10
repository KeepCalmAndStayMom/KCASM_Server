package server.api.v1;

import com.google.gson.Gson;
import server.database.v1.LoginDB;
import java.util.Map;
import static spark.Spark.get;
import static spark.Spark.put;

class ApiLogin {

    private Gson gson = new Gson();
    private String baseURL = "/api";

    ApiLogin(){
        sparkGetLogin();
        sparkGetPassword();
        sparkPutPassword();
    }

    private void sparkGetLogin() {
        get(baseURL + "/login", "application/json", (request, response) -> {

            String json = LoginDB.getLogin(request.queryParams("name"), request.queryParams("password"));
            response.type("application/json");

            if (json == null || json.equals("{}")) {
                response.status(404);
                return "{ \"Errore 404\": \"risorsa non trovata\" }";
            }

            response.status(200);
            return json;
        });
    }

    private void sparkPutPassword() {
        put(baseURL + "/password", (request, response) -> {

            Map addRequest = gson.fromJson(request.body(), Map.class);
            response.type("application/json");

            if (addRequest != null && addRequest.containsKey("username") && addRequest.containsKey("password"))
            {

                if(!LoginDB.updatePassword(String.valueOf(addRequest.get("username")), String.valueOf(addRequest.get("password")))) {
                    response.status(400);
                    return "{\"Errore 400\": \"richiesta non valida\" }";
                }
            }
            else {
                response.status(400);
                return "{\"Errore 400\": \"richiesta non valida\" }";
            }

            response.status(200);
            return "{ \"Success\":\"risorsa modificata\" }";
        });
    }

    private void sparkGetPassword() {
        get(baseURL + "/login_recovery", (request, response) -> {
            String json = LoginDB.getPassword(request.queryParams("name"));
            response.type("application/json");

            if (json == null) {
                response.status(404);
                return "{ \"Errore 404\": \"risorsa non trovata\" }";
            }

            response.status(200);
            return json;
        });
    }
}
