package server.api.v2.medic;

import com.google.gson.Gson;
import server.api.v2.Checker;
import server.api.v2.JsonBuilder;
import server.api.v2.Regex;
import server.api.v2.links.LinksBuilder;
import server.database.v2.MedicHasPatientDB;
import server.database.v2.MessageMedicPatientDB;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class ApiMedicMessages {

    private Gson gson = new Gson();

    public ApiMedicMessages() {
        medicMessages();
    }

    private void medicMessages() {
        path("/messages", () -> {
            showCategoriesMessages();
            addMessage();
            getSentMessages();
            getReceivedMessages();
            setMessageAsRead();
        });
    }

    private void setMessageAsRead() {
        put("/received", (request, response) -> {
            Map<String, Object> map = new LinkedHashMap<>();
            String timedate = request.queryParams("timedate");

            if(timedate.matches(Regex.URL_TIMEDATE_REGEX)) {
                map.put("patient_id", Integer.parseInt(request.queryParams("patient_id")));
                map.put("medic_id", Integer.parseInt(request.params("medic_id")));
                map.put("timedate", timedate.replace("T", " "));

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
            //get dei messaggi ricevuti medico
            int medicId = Integer.parseInt(request.params("medic_id"));
            String patientId = request.queryParams("patient_id");
            String date = request.queryParams("date");
            String startDate = request.queryParams("startdate");
            String endDate = request.queryParams("enddate");
            String timedate = request.queryParams("timedate");
            List<Map<String, Object>> query = null;
            String links = null;

            if(timedate != null && patientId != null && timedate.matches(Regex.URL_TIMEDATE_REGEX)) {
                Map<String, Object> message = MessageMedicPatientDB.selectSingleMessage(Integer.parseInt(patientId), medicId, timedate.replace("T", " "));

                if(message != null) {
                    message.put("link", LinksBuilder.singleMessage(medicId, "medic", "received", Integer.parseInt(patientId), timedate));
                    response.status(200);
                    response.type("application/json");

                    /*return JsonBuilder.jsonObject(message, LinksBuilder.singleMessage(medicId, "medic", "received", Integer.parseInt(patientId), timedate)).toString();*/
                    return gson.toJson(message);
                }
            }
            else if(date != null && date.matches(Regex.DATE_REGEX)) {
                query = MessageMedicPatientDB.selectMedicReceived(medicId, patientId, date);
                links = LinksBuilder.messagesLinks(medicId, "medic", "received", patientId, date, null);
            }
            else if(startDate != null && endDate != null && startDate.matches(Regex.DATE_REGEX) && endDate.matches(Regex.DATE_REGEX)) {
                query = MessageMedicPatientDB.selectMedicReceived(medicId, patientId, startDate, endDate);
                links = LinksBuilder.messagesLinks(medicId, "medic", "received", patientId, startDate, endDate);
            }
            else {
                query = MessageMedicPatientDB.selectMedicReceived(medicId, patientId);
                links = LinksBuilder.messagesLinks(medicId, "medic", "received", patientId, null, null);
            }

            if(query != null && query.size() > 0) {
                response.status(200);
                response.type("application/json");

                return "{ " + JsonBuilder.jsonList("messages_received", query, links, "message", "medic", "received").toString() + " }";
            }

            response.status(404);
            return "";
        });
    }

    private void getSentMessages() {
        get("/sent", (request, response) -> {
            //get dei messaggi inviati medico
            int medicId = Integer.parseInt(request.params("medic_id"));
            String patientId = request.queryParams("patient_id");
            String date = request.queryParams("date");
            String startDate = request.queryParams("startdate");
            String endDate = request.queryParams("enddate");
            String timedate = request.queryParams("timedate");
            List<Map<String, Object>> query = null;
            String links = null;

            if(timedate != null && patientId != null && timedate.matches(Regex.URL_TIMEDATE_REGEX)) {
                Map<String, Object> message = MessageMedicPatientDB.selectSingleMessage(Integer.parseInt(patientId), medicId, timedate.replace("T", " "));

                if(message != null) {
                    message.put("link", LinksBuilder.singleMessage(medicId, "medic", "sent", Integer.parseInt(patientId), timedate));
                    response.status(200);
                    response.type("application/json");

                    /*return JsonBuilder.jsonObject(message, LinksBuilder.singleMessage(medicId, "medic", "sent", Integer.parseInt(patientId), timedate)).toString();*/
                    return gson.toJson(message);
                }
            }
            else if(date != null && date.matches(Regex.DATE_REGEX)) {
                query = MessageMedicPatientDB.selectMedicSent(medicId, patientId, date);
                links = LinksBuilder.messagesLinks(medicId, "medic", "sent", patientId, date, null);
            }
            else if(startDate != null && endDate != null && startDate.matches(Regex.DATE_REGEX) && endDate.matches(Regex.DATE_REGEX)) {
                query = MessageMedicPatientDB.selectMedicSent(medicId, patientId, startDate, endDate);
                links = LinksBuilder.messagesLinks(medicId, "medic", "sent", patientId, startDate, endDate);
            }
            else {
                query = MessageMedicPatientDB.selectMedicSent(medicId, patientId);
                links = LinksBuilder.messagesLinks(medicId, "medic", "sent", patientId, startDate, endDate);
            }

            if(query != null && query.size() > 0) {
                response.status(200);
                response.type("application/json");

                return "{ " + JsonBuilder.jsonList("messages_sent", query, links, "message", "medic", "sent").toString() + " }";
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

                if(Checker.medicMessageMapValidation(map)) {
                    map.replace("patient_id", ((Double)map.get("patient_id")).intValue());
                    map.put("medic_id", Integer.parseInt(request.params("medic_id")));
                    map.put("medic_sender", true);
                    if(MedicHasPatientDB.checkMedicPatientAssociation((int) map.get("patient_id"), (int) map.get("medic_id"))) {
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
            int medicId = Integer.parseInt(request.params("medic_id"));
            List<Map<String, String>> links = LinksBuilder.messagesCategoryLinks(medicId,"medic");

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
