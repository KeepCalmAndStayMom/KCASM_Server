package server.api.v2.medic;

import com.google.gson.Gson;
import server.api.v2.Checker;
import server.api.v2.JsonBuilder;
import server.api.v2.links.Link;
import server.api.v2.links.LinksBuilder;
import server.database.v2.LoginDB;

import java.util.Map;

import static spark.Spark.*;

public class ApiMedicLoginData {

    private Gson gson = new Gson();

    public ApiMedicLoginData() {
        medicLoginData();
    }

    private void medicLoginData() {
        path("/login_data", () -> {
            getMedicLoginData();

            addMedicLoginData();

            modifyMedicLoginData();
        });
    }

    private void modifyMedicLoginData() {
        put("", (request, response) -> {
            if(request.contentType().contains("json")) {
                Map map = gson.fromJson(request.body(), Map.class);

                if(map != null && Checker.loginDataMapValidation(map)) {
                    map.put("medic_id", Integer.parseInt(request.params("medic_id")));

                    if(LoginDB.updateMedic(map)) {
                        response.status(200);
                        return "OK";
                    }
                }
            }

            response.status(400);
            return "ERRORE";
        });
    }

    private void addMedicLoginData() {
        post("", (request, response) -> {
            if(request.contentType().contains("json")) {
                Map<String, Object> map = gson.fromJson(request.body(), Map.class);

                if(map != null && Checker.loginDataMapValidation(map)) {
                    map.put("medic_id", Integer.parseInt(request.params("medic_id")));
                    if(LoginDB.insert(map)) {
                        response.status(201);
                        return "OK";
                    }
                }
            }

            response.status(400);
            return "ERRORE";
        });
    }

    private void getMedicLoginData() {
        get("", (request, response) -> {
            int medicId = Integer.parseInt(request.params("medic_id"));
            Map<String, Object> query = LoginDB.selectMedic(medicId);

            if(query != null) {
                query.put("links", LinksBuilder.loginData(medicId, "medic"));
                response.status(200);
                response.type("application/json");

                return query;
            }

            response.status(404);
            return "";
        }, gson::toJson);
    }
}
