package server.api.v2.patient;

import com.google.gson.Gson;
import server.api.v2.Checker;
import server.api.v2.JsonBuilder;
import server.api.v2.Regex;
import server.api.v2.links.LinksBuilder;
import server.database2.MedicHasPatientDB;
import server.database2.MessageMedicPatientDB;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class ApiPatientMessages {

    private Gson gson = new Gson();

    public ApiPatientMessages() {
        patientMessages();
    }

    private void patientMessages() {
        path("/messages", () -> {
            showCategoriesMessages();
            addMessage();
            getSentMessages();
            getReceivedMessages();
            setReceivedMessageAsRead();
        });
    }

    private void setReceivedMessageAsRead() {
        put("/received", (request, response) -> {
            Map<String, Object> map = new LinkedHashMap<>();
            String timedate = request.queryParams("timedate").replace("T", " ");

            if(timedate.matches(Regex.TIMEDATE_REGEX)) {
                map.put("patient_id", Integer.parseInt(request.params("patient_id")));
                map.put("medic_id", Integer.parseInt(request.queryParams("medic_id")));
                map.put("timedate", timedate);

                if(MessageMedicPatientDB.setMessageAsRead(map)) {
                    response.status(200);
                    return "OK";
                }
            }

            response.status(400);
            return "ERRORE";
        });
    }

    private void getReceivedMessages() {
        get("/received", (request, response) -> {
            //get dei messaggi ricevuti paziente
            int patientId = Integer.parseInt(request.params("patient_id"));
            String medic_id = request.queryParams("medic_id");
            String date = request.queryParams("date");
            String startdate = request.queryParams("startdate");
            String enddate = request.queryParams("enddate");
            String timedate = request.queryParams("timedate");

            if(timedate != null) {
                Map<String, Object> map = MessageMedicPatientDB.selectSingleMessage(patientId, Integer.parseInt(medic_id), timedate.replace("T", " "));

                if(map != null) {
                    response.status(200);
                    response.type("application/json");
                    return JsonBuilder.jsonObject(map, LinksBuilder.singleMessage(patientId, "patient", "received", Integer.parseInt(medic_id), timedate)).toString();
                }
            }
            else if(date != null) {
                List<Map<String, Object>> list = MessageMedicPatientDB.selectPatientReceived(patientId, medic_id, date, null);

                if(list != null && list.size() > 0) {
                    response.status(200);
                    response.type("application/json");

                    return "{ " + JsonBuilder.jsonList("messages-received", list, LinksBuilder.messagesLinks(patientId, "patient", "received", medic_id, date, null), "message","patient", "received").toString() + " }";
                }
            }
            else {
                List<Map<String, Object>> list = MessageMedicPatientDB.selectPatientReceived(patientId, medic_id, startdate, enddate);

                if(list != null && list.size() > 0) {
                    response.status(200);
                    response.type("application/json");

                    return "{ " + JsonBuilder.jsonList("messages-received", list, LinksBuilder.messagesLinks(patientId, "patient", "received", medic_id, startdate, enddate), "message","patient", "received").toString() + " }";
                }
            }

            response.status(404);
            return "";
        });
    }

    private void getSentMessages() {
        get("/sent", (request, response) -> {
            //get dei messaggi inviati paziente
            int patientId = Integer.parseInt(request.params("patient_id"));
            String medic_id = request.queryParams("medic_id");
            String date = request.queryParams("date");
            String startdate = request.queryParams("startdate");
            String enddate = request.queryParams("enddate");
            String timedate = request.queryParams("timedate");

            if(timedate != null) {
                Map<String, Object> map = MessageMedicPatientDB.selectSingleMessage(patientId, Integer.parseInt(medic_id), timedate.replace("T", " "));

                if(map != null) {
                    response.status(200);
                    response.type("application/json");
                    return JsonBuilder.jsonObject(map, LinksBuilder.singleMessage(patientId, "patient", "sent", Integer.parseInt(medic_id), timedate)).toString();
                }
            }
            else if(date != null) {
                List<Map<String, Object>> list = MessageMedicPatientDB.selectPatientSent(patientId, medic_id, date, null);

                if(list != null && list.size() > 0) {
                    response.status(200);
                    response.type("application/json");

                    return "{ " + JsonBuilder.jsonList("messages-sent", list, LinksBuilder.messagesLinks(patientId, "patient", "sent", medic_id, date, null), "message","patient", "sent").toString() + " }";
                }
            }
            else {
                List<Map<String, Object>> list = MessageMedicPatientDB.selectPatientSent(patientId, medic_id, startdate, enddate);

                if(list != null && list.size() > 0) {
                    response.status(200);
                    response.type("application/json");

                    return "{ " + JsonBuilder.jsonList("messages-sent", list, LinksBuilder.messagesLinks(patientId, "patient", "sent", medic_id, startdate, enddate), "message","patient", "sent").toString() + " }";
                }
            }

            response.status(404);
            return "";
        });
    }

    private void addMessage() {
        post("", (request, response) -> {
            //aggiunta un nuovo messaggio
            if(request.contentType().contains("json")) {
                Map<String, Object> map = gson.fromJson(request.body(), Map.class);

                if(map != null && Checker.patientMessageMapValidation(map)) {
                    map.put("patient_id", Double.parseDouble(request.params("patient_id")));
                    map.put("medic_sender", false);

                    if(MedicHasPatientDB.checkMedicPatientAssociation(((Double) map.get("patient_id")).intValue(), ((Double) map.get("medic_id")).intValue())) {
                        if(MessageMedicPatientDB.insert(map)) {
                            response.status(201);
                            return "OK";
                        }
                    }
                    else {
                        response.status(403);
                        return "FORBIDDEN";
                    }
                }
            }

            response.status(400);
            return "ERRORE";
        });
    }

    private void showCategoriesMessages() {
        get("", (request, response) -> {
            int patientId = Integer.parseInt(request.params("patient_id"));
            String r = JsonBuilder.jsonList(null, null, LinksBuilder.messagesCategoryLinks(patientId, "patient"), null, null).toString();

            if(r != "") {
                response.status(200);
                response.type("application/json");

                return r;
            }

            response.status(404);
            return "";
        });
    }
}
