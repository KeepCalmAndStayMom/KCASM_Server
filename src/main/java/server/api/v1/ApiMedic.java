package server.api.v1;

import com.google.gson.Gson;
import server.database.v1.LoginDB;
import server.database.v1.MedicDB;
import java.util.Map;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

class ApiMedic {

    private Gson gson = new Gson();
    private String baseURL = "/api";

    ApiMedic(){
        sparkGetMedic();
        sparkGetUsersWithMedic();
        sparkPostMedic();
        sparkPutMedic();
        sparkPutMedicLogin();
    }

    private void sparkGetMedic() {
        get(baseURL + "/medics/:id", "application/json", (request, response) -> {

            String json = MedicDB.getMedic(Integer.valueOf(request.params(":id")));
            response.type("application/json");

            if (json == null || json.equals("}")) {
                response.status(404);
                return "{ \"Errore 404\": \"risorsa non trovata\" }";
            }

            response.status(200);
            return json;
        });
    }

    private void sparkGetUsersWithMedic() {
        get(baseURL + "/medics/:id/users", "application/json", (request, response) -> {

            String json = MedicDB.getUsers(Integer.valueOf(request.params(":id")));
            response.type("application/json");

            if (json == null || json.equals("}")) {
                response.status(404);
                return "{ \"Errore 404\": \"risorsa non trovata\" }";
            }

            response.status(200);
            return json;
        });
    }

    private void sparkPostMedic() {
        post(baseURL + "/medics", (request, response) -> {

            Map addRequest = gson.fromJson(request.body(), Map.class);
            response.type("application/json");

            if (addRequest != null && addRequest.containsKey("name")) {
                String name = String.valueOf(addRequest.get("name"));

                MedicDB.addMedic(name);
            }
            else {
                response.status(400);
                return "{\"Errore 400\": \"richiesta non valida\" }";
            }

            response.status(201);
            return "{ \"Success\":\"risorsa aggiunta\" }";
        });
    }

    private void sparkPutMedic() {
        put(baseURL + "/medics/:id", (request, response) -> {

            Map addRequest = gson.fromJson(request.body(), Map.class);
            response.type("application/json");

            if (addRequest != null && addRequest.containsKey("name")) {
                String name = String.valueOf(addRequest.get("name"));

                if(!MedicDB.updateMedic(Integer.valueOf(request.params(":id")), name)) {
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

    private void sparkPutMedicLogin() {
        put(baseURL + "/login_medic/:username/:id", (request, response) -> {

            Map addRequest = gson.fromJson(request.body(), Map.class);
            response.type("application/json");

            if (addRequest != null && addRequest.containsKey("username")
                    && addRequest.containsKey("email")
                    && addRequest.containsKey("name"))
            {

                if(!LoginDB.updateLogin(request.params(":username"),String.valueOf(addRequest.get("username")),
                        String.valueOf(addRequest.get("email"))))
                {
                    response.status(409);
                    return "{\"Errore 409\": \"conflitto generato\" }";
                }

                if(!MedicDB.updateMedic(Integer.valueOf(request.params(":id")),
                        String.valueOf(addRequest.get("name"))))
                {
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
}
