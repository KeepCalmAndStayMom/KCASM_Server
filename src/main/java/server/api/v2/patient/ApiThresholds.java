package server.api.v2.patient;

import server.api.v2.JsonBuilder;
import server.database.v2.PatientInitialDB;
import server.retrieve_data.SogliePeso;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class ApiThresholds {


    public ApiThresholds() {
        getThreshold();
    }

    private void getThreshold() {
        get("/thresholds", (request, response) -> {
            int patientId = Integer.parseInt(request.params("patient_id"));
            Map initialData = PatientInitialDB.select(patientId);

            if(initialData != null) {
                Map thresholds = new HashMap();
                String bmi = (String) initialData.get("bmi");
                boolean twin = (boolean) initialData.get("twin");

                thresholds.put("min", SogliePeso.getListSogliaMin(bmi, twin));
                thresholds.put("max", SogliePeso.getListSogliaMax(bmi, twin));

                response.status(200);
                response.type("application/json");

                return JsonBuilder.jsonObject(thresholds, null);
            }

            response.status(404);
            return "ERRORE";
        });
    }

}
