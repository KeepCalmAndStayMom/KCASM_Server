package server.api.v2.medic;

import com.google.gson.Gson;
import server.api.v2.Checker;
import server.api.v2.JsonBuilder;
import server.api.v2.Regex;
import server.api.v2.links.LinksBuilder;
import server.api.v2.links.MedicTasksLinks;
import server.database2.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class ApiMedic {
    private final static String baseURL = "/api/v2";
    private final static String DATE_REGEX = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$";
    private final static String TIMEDATE_REGEX = "^(19|20)\\d\\d-(0[1-9]|1[012])-([012]\\d|3[01])T([01]\\d|2[0-3]):([0-5]\\d):([0-5]\\d)$";
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

                medicMessages();

                medicLoginData();
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

                if(map != null && Checker.medicMapValidation(map)) {
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

                if(map != null && Checker.medicMapValidation(map)) {
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

    private void medicLoginData() {
        path("/login_data", () -> {
            get("", (request, response) -> {
                //get dati login paziente
                int medicId = Integer.parseInt(request.params("medic_id"));
                Map<String, Object> query = LoginDB.selectMedic(medicId);

                if(query != null) {
                    response.status(200);
                    response.type("application/json");

                    return JsonBuilder.jsonObject(query, LinksBuilder.loginData(medicId, "medic")).toString();
                }

                response.status(404);
                return "";
            });
            post("", (request, response) -> {
                //aggiunta dato di login solo admin
                if(request.contentType().contains("json")) {
                    Map<String, Object> map = gson.fromJson(request.body(), Map.class);

                    if(map != null && Checker.loginDataMapValidation(map)) {
                        map.put("medic_id", Integer.parseInt(request.params("medic_id")));
                        if(LoginDB.insert(map)) {
                            response.status(201);
                            return "OK";
                        }
                    }
                }

                response.status(400);
                return "ERRORE";
            });
            put("", (request, response) -> {
                if(request.contentType().contains("json")) {
                    Map map = gson.fromJson(request.body(), Map.class);

                    if(map != null && Checker.loginDataMapValidation(map)) {
                        map.put("medic_id", Integer.parseInt(request.params("medic_id")));

                        if(LoginDB.updateMedic(map)) {
                            response.status(200);
                            return "OK";
                        }
                    }
                }

                response.status(400);
                return "ERRORE";
            });
        });
    }

    private void medicMessages() {
        path("/messages", () -> {
            get("", (request, response) -> {
                int medicId = Integer.parseInt(request.params("medic_id"));
                String r = JsonBuilder.jsonList(null, null, LinksBuilder.messagesCategoryLinks(medicId, "medic"), null).toString();

                if(r != null) {
                    response.status(200);
                    response.type("application/json");

                    return r;
                }

                response.status(404);
                return "";
            });
            post("", (request, response) -> {
                //aggiunta un nuovo messaggio
                if(request.contentType().contains("json")) {
                    Map<String, Object> map = gson.fromJson(request.body(), Map.class);

                    if(Checker.medicMessageMapValidation(map)) {
                        map.put("medic_id", Double.parseDouble(request.params("medic_id")));
                        map.put("medic_sender", true);
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

                if(timedate != null && patientId != null && timedate.matches(TIMEDATE_REGEX)) {
                    Map<String, Object> message = MessageMedicPatientDB.selectSingleMessage(Integer.parseInt(patientId), medicId, timedate.replace("T", " "));

                    if(message != null) {
                        response.status(200);
                        response.type("application/json");

                        return JsonBuilder.jsonObject(message, LinksBuilder.singleMessage(medicId, "medic", "sent", Integer.parseInt(patientId), timedate)).toString();
                    }
                }
                else if(date != null && date.matches(DATE_REGEX)) {
                    query = MessageMedicPatientDB.selectMedicSent(medicId, patientId, date);
                    links = LinksBuilder.messagesLinks(medicId, "medic", "sent", patientId, date, null);
                }
                else if(startDate != null && endDate != null && startDate.matches(DATE_REGEX) && endDate.matches(DATE_REGEX)) {
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

                    return "{ " + JsonBuilder.jsonList("messages-sent", query, links, "message", "medic", "sent").toString() + " }";
                }

                response.status(404);
                return "";
            });
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

                if(timedate != null && patientId != null && timedate.matches(TIMEDATE_REGEX)) {
                    Map<String, Object> message = MessageMedicPatientDB.selectSingleMessage(Integer.parseInt(patientId), medicId, timedate.replace("T", " "));

                    if(message != null) {
                        response.status(200);
                        response.type("application/json");

                        return JsonBuilder.jsonObject(message, LinksBuilder.singleMessage(medicId, "medic", "received", Integer.parseInt(patientId), timedate)).toString();
                    }
                }
                else if(date != null && date.matches(DATE_REGEX)) {
                    query = MessageMedicPatientDB.selectMedicReceived(medicId, patientId, date);
                    links = LinksBuilder.messagesLinks(medicId, "medic", "received", patientId, date, null);
                }
                else if(startDate != null && endDate != null && startDate.matches(DATE_REGEX) && endDate.matches(DATE_REGEX)) {
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

                    return "{ " + JsonBuilder.jsonList("messages-received", query, links, "message", "medic", "received").toString() + " }";
                }

                response.status(404);
                return "";
            });
            put("/received", (request, response) -> {
                Map<String, Object> map = new LinkedHashMap<>();
                String timedate = request.queryParams("timedate").replace("T", " ");

                if(timedate.matches(Regex.TIMEDATE_REGEX)) {
                    map.put("patient_id", Integer.parseInt(request.queryParams("patient_id")));
                    map.put("medic_id", Integer.parseInt(request.params("medic_id")));
                    map.put("timedate", timedate);

                    if(MessageMedicPatientDB.setMessageAsRead(map)) {
                        response.status(200);
                        return "OK";
                    }
                }

                response.status(400);
                return "ERRORE";
            });
        });
    }
}
