package server.api.v2.patient;

import com.google.gson.Gson;
import server.api.v2.Checker;
import server.api.v2.JsonBuilder;
import server.api.v2.Regex;
import server.api.v2.links.LinksBuilder;
import server.database.v2.WeightDB;

import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class ApiWeights {

    private Gson gson = new Gson();

    public ApiWeights() {
        patientWeights();
    }

    private void patientWeights() {
        path("/weights", () -> {
            getWeights();
            addWeight();
            modifyLastWeight();
        });
    }

    private void modifyLastWeight() {
        put("", (request, response) ->{
            if(request.contentType().contains("json")) {
                Map map = gson.fromJson(request.body(), Map.class);
                String date = request.queryParams("date");

                if(map != null && Checker.putWeightMapValidation(map) && date.matches(Regex.DATE_REGEX)) {
                    map.put("patient_id", Integer.parseInt(request.params("patient_id")));
                    map.put("date", date);

                    if(WeightDB.update(map)) {
                        response.status(200);
                        return "OK";
                    }
                }
            }

            response.status(400);
            return "ERRORE";
        });
    }

    private void addWeight() {
        post("", (request, response) -> {
            if(request.contentType().contains("json")) {
                Map<String, Object> map = gson.fromJson(request.body(), Map.class);

                if(map != null && Checker.postWeightMapValidation(map)) {
                    map.put("patient_id", Integer.parseInt(request.params("patient_id")));

                    if(WeightDB.insert(map)) {
                        response.status(201);
                        return "OK";
                    }
                }

                response.status(400);
                return "ERRORE";
            }


            return "";
        });
    }

    private void getWeights() {
        get("", (request, response) -> {
            //get pesi della paziente
            int patientId = Integer.parseInt(request.params("patient_id"));
            String date = request.queryParams("date");
            String startdate = request.queryParams("startdate");
            String enddate = request.queryParams("enddate");

            if(date != null && date.matches(Regex.DATE_REGEX)) {
                Map<String, Object> query = WeightDB.selectSingleWeight(patientId, date);
                if(query != null) {
                    response.status(200);
                    response.type("application/json");

                    return JsonBuilder.jsonObject(query, null).toString();
                }
            }
            else if(startdate != null && enddate != null && startdate.matches(Regex.DATE_REGEX) && enddate.matches(Regex.DATE_REGEX)) {
                List<Map<String, Object>> query = WeightDB.selectListWithInterval(patientId, startdate, enddate);

                if(query != null && query.size() > 0) {
                    response.status(200);
                    response.type("application/json");

                    return "{ "+ JsonBuilder.jsonList("weights", query, LinksBuilder.weightsLinks(patientId), "weight").toString() + " }";
                }
            }
            else {
                List<Map<String, Object>> query = WeightDB.selectList(patientId);

                if(query != null && query.size() > 0) {
                    response.status(200);
                    response.type("application/json");

                    return "{ "+ JsonBuilder.jsonList("weights", query, LinksBuilder.weightsLinks(patientId), "weight").toString() + " }";
                }
            }

            response.status(404);
            return "";
        });
    }
}
