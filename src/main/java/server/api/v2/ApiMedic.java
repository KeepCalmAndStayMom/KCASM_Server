package server.api.v2;

import server.api.v2.links.Link;
import server.api.v2.links.LinksBuilder;
import server.database2.LoginDB;
import server.database2.MedicDB;
import server.database2.MessageMedicPatientDB;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class ApiMedic {
    private String baseURL = "/api/v2";
    private final static String DATE_REGEX = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$";
    private final static String TIMEDATE_REGEX = "^(19|20)\\d\\d-(0[1-9]|1[012])-([012]\\d|3[01])T([01]\\d|2[0-3]):([0-5]\\d):([0-5]\\d)$";

    public ApiMedic() { apiMedic(); }

    private void apiMedic() {
        path(baseURL + "/medics", () -> {
            post("", (request, response) -> {
                //aggiunta di un nuovo medico solo admin
                return "";
            });

            path("/:medic_id", () -> {
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
                put("", (request, response) -> {
                   //modifica dati medico
                    return "";
                });
                delete("", (request, response) -> {
                   //rimozione di un medico solo admin
                   return "";
                });
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

                medicTasks();

                medicMessages();

                medicLoginData();
            });
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
                return "";
            });
            put("", (request, response) -> {
                //modifica dati login paziente
                return "";
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
                return "";
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

                        return JsonBuilder.jsonObject(message, LinksBuilder.singleMessageLink(medicId, "medic", "sent", Integer.parseInt(patientId), timedate)).toString();
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

                        return JsonBuilder.jsonObject(message, LinksBuilder.singleMessageLink(medicId, "medic", "received", Integer.parseInt(patientId), timedate)).toString();
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
                    System.out.println(query);

                    return "{ " + JsonBuilder.jsonList("messages-received", query, links, "message", "medic", "received").toString() + " }";
                }

                response.status(404);
                return "";
            });
        });
    }

    private void medicTasks() {
        path("/tasks", () -> {
            path("/general", () -> {
                post("", (request, response) -> {
                    //aggiunta task generale
                    return "";
                });
                get("", (request, response) -> {
                    //get task generali medico
                    return "";
                });

                medicTask();
            });
            path("/activities", () -> {
                post("", (request, response) -> {
                    //aggiunta attività
                    return "";
                });
                get("", (request, response) -> {
                    //get attività medico
                    return "";
                });

                medicTask();
            });
            path("/diets", () -> {
                post("", (request, response) -> {
                    //aggiunta dieta
                    return "";
                });
                get("", (request, response) -> {
                    //get diete medico
                    return "";
                });

                medicTask();
            });
        });
    }

    private void medicTask() {
        get("/:task_id", (request, response) -> {
            //get singolo task
            return "";
        });
        put("/:task_id", (request, response) -> {
            //modifica task
            return "";
        });
        delete("/:task_id", (request, response) -> {
           //rimozione task
           return "";
        });
    }
}
