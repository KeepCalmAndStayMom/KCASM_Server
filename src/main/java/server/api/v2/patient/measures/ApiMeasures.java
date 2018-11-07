package server.api.v2.patient.measures;

import server.api.v2.JsonBuilder;
import server.api.v2.links.MeasuresLinks;

import static spark.Spark.get;
import static spark.Spark.path;

public class ApiMeasures {

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
            String r = JsonBuilder.jsonList(null, null, MeasuresLinks.measuresSubLinks(patientId, measuresType), null, null).toString();

            if(r != null) {
                response.status(200);
                response.type("application/json");

                return r;
            }

            response.status(404);
            return "";
        });
    }

    private void showTypeOfMeasures() {
        get("", (request, response) -> {
            int patientId = Integer.parseInt(request.params("patient_id"));
            String r = JsonBuilder.jsonList(null, null, MeasuresLinks.measuresLinks(patientId), null, null).toString();;

            if(r != null) {
                response.status(200);
                response.type("application/json");

                return r;
            }

            response.status(404);
            return "";
        });
    }
}
