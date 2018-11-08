package server.api.v2.patient;

import com.auth0.jwt.internal.org.bouncycastle.util.encoders.Base64;
import com.google.gson.Gson;
import server.api.v2.Checker;
import server.api.v2.JsonBuilder;
import server.api.v2.links.LinksBuilder;
import server.api.v2.patient.measures.ApiMeasures;
import server.database2.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class ApiPatient {

    private final static String baseURL = "/api/v2";
    private Gson gson = new Gson();

    public ApiPatient() {
        apiPatient();
    }

    private void apiPatient() {
        post(baseURL + "/login_data", (request, response) -> {
            String base64 = request.headers("Authorization").replace("Basic ", "");
            String[] auth = new String(Base64.decode(base64.getBytes()), "UTF-8").split(":");

            Map query = LoginDB.select(auth[0], auth[1]);

            if(query != null) {
                response.status(200);
                response.type("application/json");
                return JsonBuilder.jsonObject(query, null);
            }

            response.status(401);
            return "ERRORE";
        });

        path(baseURL + "/patients", () -> {
            addPatient();

            path("/:patient_id", () -> {
                getPatientData();

                changePatientData();

                deletePatient();

                path("/medics", () -> {
                    getMedicsOfPatient();

                    addMedicToPatient();
                });

                new ApiMeasures();

                new ApiPatientMessages();

                new ApiPatientLoginData();

                new ApiPatientInitialData();

                new ApiPatientTasks();

                new ApiWeights();

            });
        });
    }

    private void addMedicToPatient() {
        post("", (request, response) -> {
            if(request.contentType().contains("json")) {
                Map<String, Object> map = gson.fromJson(request.body(), Map.class);

                if(map != null && Checker.patientAddMedic(map)) {
                    map.put("patient_id", Double.parseDouble(request.params("patient_id")));
                    if(MedicHasPatientDB.insert(map)) {
                        response.status(201);
                        return "OK";
                    }
                }
            }

            response.status(400);
            return "ERRORE";
        });
    }

    private void getMedicsOfPatient() {
        get("", (request, response) -> {
            int patientId = Integer.parseInt(request.params("patient_id"));
            List<Map<String, Object>> query = PatientDB.selectMedicsOfPatient(patientId);

            if(query != null && query.size() != 0) {
                response.status(200);
                response.type("application/json");
                return JsonBuilder.jsonList(null, query,null, "medic", null).toString();
            }

            response.status(404);
            return "";
        });
    }

    private void deletePatient() {
        delete("", (request, response) -> {
            if(PatientDB.delete(Integer.parseInt(request.params("patient_id")))) {
                response.status(200);
                return "OK";
            }

            response.status(404);
            return "ERRORE";
        });
    }

    private void changePatientData() {
        put("", (request, response) -> {
            if(request.contentType().contains("json")) {
                Map map = gson.fromJson(request.body(), Map.class);

                if(map != null && Checker.patientMapValidation(map)) {
                     map.put("id", Integer.parseInt(request.params("patient_id")));

                     if(PatientDB.update(map)) {
                         response.status(200);
                         return "OK";
                     }
                }
            }

            response.status(400);
            return "ERRORE";
        });
    }

    private void getPatientData() {
        get("", (request, response) -> {
            //get dati paziente
            int patientId = Integer.parseInt(request.params("patient_id"));
            Map<String, Object> query = PatientDB.select(patientId);

            if(query != null) {
                response.status(200);
                response.type("application/json");

                return JsonBuilder.jsonObject(query, LinksBuilder.patientLinks(patientId)).toString();
            }

            response.status(404);
            return "";
        });
    }

    private void addPatient() {
        post("", (request, response) -> {
            //aggiunta paziente
            if(request.contentType().contains("json")) {
                Map<String, Object> map = gson.fromJson(request.body(), Map.class);

                if(Checker.patientMapValidation(map)) {
                    if(PatientDB.insert(map)) {
                        response.status(201);
                        return "OK";
                    }
                }
            }

            response.status(400);
            return "ERRORE";
        });
    }
}
