package server.api.v2.patient;

import com.google.gson.Gson;
import server.api.v2.Checker;
import server.api.v2.JsonBuilder;
import server.api.v2.links.LinksBuilder;
import server.database.v2.LoginDB;

import java.util.Map;

import static spark.Spark.*;

public class ApiPatientLoginData {

    private Gson gson = new Gson();

    public ApiPatientLoginData() {
        patientLoginData();
    }

    private void patientLoginData() {
        path("/login_data", () -> {
            getPatientLoginData();
            addPatientLoginData();
            modifyPatientLoginData();
        });
    }

    private void modifyPatientLoginData() {
        put("", (request, response) -> {
            if(request.contentType().contains("json")) {
                Map map = gson.fromJson(request.body(), Map.class);

                if(map != null && Checker.loginDataMapValidation(map)) {
                    map.put("patient_id", Integer.parseInt(request.params("patient_id")));

                    if(LoginDB.updatePatient(map)) {
                        response.status(200);
                        return "OK";
                    }
                }
            }

            response.status(400);
            return "ERRORE";
        });
    }

    private void addPatientLoginData() {
        post("", (request, response) -> {
            if(request.contentType().contains("json")) {
                Map<String, Object> map = gson.fromJson(request.body(), Map.class);

                if(map != null && Checker.loginDataMapValidation(map)) {
                    map.put("patient_id", Integer.parseInt(request.params("patient_id")));
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

    private void getPatientLoginData() {
        get("", (request, response) -> {
            int patientId = Integer.parseInt(request.params("patient_id"));
            Map<String, Object> query = LoginDB.selectPatient(patientId);

            if(query != null) {
                query.put("links", LinksBuilder.loginData(patientId, "patient"));
                response.status(200);
                response.type("application/json");

                return query;
            }

            response.status(404);
            return "";
        }, gson::toJson);
    }
}
