package server.api.v2.medic;

import com.google.gson.Gson;
import server.api.v2.Checker;
import server.api.v2.JsonBuilder;
import server.api.v2.links.LinksBuilder;
import server.database.v2.MedicDB;
import server.database.v2.MedicHasPatientDB;

import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class ApiMedic {
    private final static String baseURL = "/api/v2";
    private Gson gson = new Gson();

    public ApiMedic() { apiMedic(); }

    private void apiMedic() {
        path(baseURL + "/medics", () -> {
            addMedic();

            path("/:medic_id", () -> {
                getMedicData();

                changeMedicData();

                deleteMedic();

                getPatientsOfMedic();

                addPatientToMedic();

                new ApiMedicTasks();

                new ApiMedicMessages();

                new ApiMedicLoginData();
            });
        });
    }

    private void addPatientToMedic() {
        post("/patients", (request, response) -> {
            if(request.contentType().contains("json")) {
                Map<String, Object> map = gson.fromJson(request.body(), Map.class);

                if(map != null && Checker.medicAddPatient(map)) {
                    map.put("medic_id", Double.parseDouble(request.params("medic_id")));
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

    private void getPatientsOfMedic() {
        get("/patients", (request, response) -> {
            //get dei pazienti del medico
            int medicId = Integer.parseInt(request.params("medic_id"));
            List<Map<String, Object>> query = MedicDB.selectPatientsOfMedic(medicId);

            if(query != null && query.size() > 0) {
                response.status(200);
                response.type("application/json");

                return JsonBuilder.jsonList(null, query, null, "patient").toString();
            }

            response.status(404);
            return "";
        });
    }

    private void deleteMedic() {
        delete("", (request, response) -> {
            if(MedicDB.delete(Integer.parseInt(request.params("medic_id")))) {
                response.status(200);
                return "OK";
            }

            response.status(404);
            return "ERRORE";
        });
    }

    private void changeMedicData() {
        put("", (request, response) -> {
            if(request.contentType().contains("json")) {
                Map map = gson.fromJson(request.body(), Map.class);

                if(map != null && Checker.medicPutMapValidation(map)) {
                    map.put("id", Integer.parseInt(request.params("medic_id")));

                    if(MedicDB.update(map)) {
                        response.status(200);
                        return "OK";
                    }
                }
            }

            response.status(400);
            return "ERRORE";
        });
    }

    private void getMedicData() {
        get("", (request, response) -> {
            int medicId = Integer.parseInt(request.params("medic_id"));
            Map<String, Object> query = MedicDB.select(medicId);

            if(query != null) {
                response.status(200);
                response.type("application/json");

                return JsonBuilder.jsonObject(query, LinksBuilder.medicLinks(medicId)).toString();
            }

            response.status(404);
            return "";
        });
    }

    private void addMedic() {
        post("", (request, response) -> {
            if(request.contentType().contains("json")) {
                Map<String, Object> map = gson.fromJson(request.body(), Map.class);

                if(map != null && Checker.medicPostMapValidation(map)) {
                    if(MedicDB.insert(map)) {
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
