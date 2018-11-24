package server.api.v2.patient.measures;

import com.google.gson.Gson;
import server.api.v2.JsonBuilder;
import server.api.v2.links.MeasuresLinks;

import java.util.List;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.path;

public class ApiMeasures {
    private Gson gson = new Gson();

    public ApiMeasures() {
        apiMeasures();
    }

    private void apiMeasures() {
        path("/measures", () -> {
            showTypeOfMeasures();

            path("/samples", () -> {
                showDevices("samples");

                new SamplesMeasures();
            });
            path("/total", () -> {
                showDevices("total");

                new TotalMeasures();
            });
        });
    }

    private void showDevices(String measuresType) {
        get("", (request, response) -> {
            int patientId = Integer.parseInt(request.params("patient_id"));
            List<Map<String, String>> links = MeasuresLinks.measuresSubLinks(patientId, measuresType);

            if(links != null) {
                response.status(200);
                response.type("application/json");

                return links;
            }

            response.status(404);
            return "";
        }, gson::toJson);
    }

    private void showTypeOfMeasures() {
        get("", (request, response) -> {
            int patientId = Integer.parseInt(request.params("patient_id"));
            List<Map<String, String>> links = MeasuresLinks.measuresLinks(patientId);

            if(links != null) {
                response.status(200);
                response.type("application/json");

                return links;
            }

            response.status(404);
            return "";
        }, gson::toJson);
    }
}
