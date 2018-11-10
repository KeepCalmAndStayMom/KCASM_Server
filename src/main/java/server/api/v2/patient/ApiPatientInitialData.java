package server.api.v2.patient;

import com.google.gson.Gson;
import server.api.v2.Checker;
import server.api.v2.JsonBuilder;
import server.api.v2.links.LinksBuilder;
import server.database.v2.PatientInitialDB;

import java.util.Map;

import static spark.Spark.*;

public class ApiPatientInitialData {

    private Gson gson = new Gson();

    public ApiPatientInitialData() {
        patientInitialData();
    }

    private void patientInitialData() {
        path("/initial_data", () -> {
            getPatientInitialData();
            addPatientInitialData();
            modifyTwinParameter();
        });
    }

    private void modifyTwinParameter() {
        put("", (request, response) -> {
            if(request.contentType().contains("json")) {
                Map map = gson.fromJson(request.body(), Map.class);

                if(map != null && Checker.putPatientInitialDataMapValidation(map)) {
                    map.put("patient_id", Integer.parseInt(request.params("patient_id")));

                    if(PatientInitialDB.update(map)) {
                        response.status(200);
                        return "OK";
                    }
                }
            }

            response.status(400);
            return "ERRORE";
        });
    }

    private void addPatientInitialData() {
        post("", (request, response) -> {
            if(request.contentType().contains("json")) {
                Map<String, Object> map = gson.fromJson(request.body(), Map.class);

                if(map != null && Checker.postPatientInitialDataMapValidation(map)) {
                    map.put("patient_id", Integer.parseInt(request.params("patient_id")));
                    if(PatientInitialDB.insert(map)) {
                        response.status(201);
                        return "OK";
                    }
                }
            }

            response.status(400);
            return "ERRORE";
        });
    }

    private void getPatientInitialData() {
        get("", (request, response) -> {
            //get dei dati iniziali della paziente
            int patientId = Integer.parseInt(request.params("patient_id"));
            Map<String, Object> query = PatientInitialDB.select(patientId);

            if(query != null) {
                response.status(200);
                response.type("application/json");

                return JsonBuilder.jsonObject(query, LinksBuilder.initialData(patientId)).toString();
            }

            response.status(404);
            return "";
        });
    }
}
