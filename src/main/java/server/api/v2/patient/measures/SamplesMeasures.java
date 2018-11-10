package server.api.v2.patient.measures;

import server.api.v2.JsonBuilder;
import server.api.v2.Regex;
import server.api.v2.links.MeasuresLinks;
import server.database.v2.FitbitDB;
import server.database.v2.HueDB;
import server.database.v2.SensorDB;

import java.util.List;
import java.util.Map;

import static spark.Spark.get;

public class SamplesMeasures {

    public SamplesMeasures() {
        getSensorSamples();
        getFitbitSamples();
        getHueSamples();
    }

    private void getSensorSamples() {
        get("/sensor", (request, response) -> {
            //get sensor
            int patientId = Integer.parseInt(request.params("patient_id"));
            List<Map<String, Object>> query;
            String links;
            String date = request.queryParams("date");
            String startdate = request.queryParams("startdate");
            String enddate = request.queryParams("enddate");

            if(date != null && date.matches(Regex.DATE_REGEX)) {
                query = SensorDB.selectDate(patientId, date);
                links = MeasuresLinks.sensorLinks(patientId, "samples", date);
            }
            else if(startdate != null && enddate != null && (startdate.matches(Regex.DATE_REGEX) || startdate.matches(Regex.TIMEDATE_REGEX)) && (enddate.matches(Regex.DATE_REGEX) || enddate.matches(Regex.TIMEDATE_REGEX))) {
                query = SensorDB.selectDateInterval(patientId, startdate, enddate);
                links = MeasuresLinks.sensorLinks(patientId, "samples", startdate, enddate);
            }
            else {
                query = SensorDB.select(patientId);
                links = MeasuresLinks.sensorLinks(patientId, "samples");
            }

            if(query != null && query.size() > 0) {
                response.status(200);
                response.type("application/json");

                return "{ " + JsonBuilder.jsonList("sensor-samples", query, links, "test").toString() + " }";
            }

            response.status(404);
            return "";
        });
    }

    private void getHueSamples() {
        get("/hue", (request, response) -> {
            //get hue
            int patientId = Integer.parseInt(request.params("patient_id"));
            List<Map<String, Object>> query;
            String links;
            String date = request.queryParams("date");
            String startdate = request.queryParams("startdate");
            String enddate = request.queryParams("enddate");

            if(date != null && date.matches(Regex.DATE_REGEX)) {
                query = HueDB.selectDate(patientId, date);
                links = MeasuresLinks.hueLinks(patientId, "samples", date);
            }
            else if(startdate != null && enddate != null && (startdate.matches(Regex.DATE_REGEX) || startdate.matches(Regex.TIMEDATE_REGEX)) && (enddate.matches(Regex.DATE_REGEX) || enddate.matches(Regex.TIMEDATE_REGEX))) {
                query = HueDB.selectDateInterval(patientId, startdate, enddate);
                links = MeasuresLinks.hueLinks(patientId, "samples", startdate, enddate);
            }
            else {
                query = HueDB.select(patientId);
                links = MeasuresLinks.hueLinks(patientId, "samples");
            }

            if(query != null && query.size() > 0) {
                response.status(200);
                response.type("application/json");

                return "{ " + JsonBuilder.jsonList("hue-samples", query, links, "test").toString() + " }";
            }

            response.status(404);
            return "";
        });
    }

    private void getFitbitSamples() {
        get("/fitbit", (request, response) -> {
            //get fitbit
            int patientId = Integer.parseInt(request.params("patient_id"));

            List<Map<String, Object>> query;
            String links;
            String date = request.queryParams("date");
            String startdate = request.queryParams("startdate");
            String enddate = request.queryParams("enddate");

            if(date != null && date.matches(Regex.DATE_REGEX)) {
                query = FitbitDB.selectDate(patientId, date);
                links = MeasuresLinks.fitbitLinks(patientId, "samples", date);
            }
            else if(startdate != null && enddate != null && (startdate.matches(Regex.DATE_REGEX) || startdate.matches(Regex.TIMEDATE_REGEX)) && (enddate.matches(Regex.DATE_REGEX) || enddate.matches(Regex.TIMEDATE_REGEX))) {
                query = FitbitDB.selectDateInterval(patientId, startdate, enddate);
                links = MeasuresLinks.fitbitLinks(patientId, "samples", startdate, enddate);
            }
            else {
                query = FitbitDB.select(patientId);
                links = MeasuresLinks.fitbitLinks(patientId, "samples");
            }

            if(query != null && query.size() > 0) {
                response.status(200);
                response.type("application/json");

                return "{ " + JsonBuilder.jsonList("fitbit-samples", query, links, "test").toString() + " }";
            }

            response.status(404);
            return "";
        });
    }
}
