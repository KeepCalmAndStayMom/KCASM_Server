package server.api.v2.patient;

import com.google.gson.Gson;
import server.api.v2.JsonBuilder;
import server.database.v2.PatientInitialDB;
import server.retrieve_data.SogliePeso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class ApiThresholds {
    private Gson gson = new Gson();

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

                double startPeso = (double) initialData.get("weight");

                List min = new ArrayList();
                List max = new ArrayList();

                for(double d : SogliePeso.getListSogliaMin(bmi, twin)) {
                    min.add(startPeso + d);
                }

                for(double d : SogliePeso.getListSogliaMax(bmi, twin)) {
                    max.add(startPeso + d);
                }

                thresholds.put("min", min);
                thresholds.put("max", max);

                response.status(200);
                response.type("application/json");

                return thresholds;
            }

            response.status(404);
            return "";
        }, gson::toJson);
    }

}
