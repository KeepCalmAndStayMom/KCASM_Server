package server.api.v2;

import com.google.gson.Gson;
import server.api.v2.links.LinksBuilder;
import server.api.v2.links.MeasuresLinks;
import server.api.v2.links.PatientTasksLinks;
import server.database2.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class ApiPatient {

    private final static String baseURL = "/api/v2";
    private Gson gson = new Gson();
    private final static String DATE_REGEX = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$";
    private final static String TIMEDATE_REGEX = "^(19|20)\\d\\d-(0[1-9]|1[012])-([012]\\d|3[01])T([01]\\d|2[0-3]):([0-5]\\d):([0-5]\\d)$";

    public ApiPatient() {
        apiPatient();
    }

    private void apiPatient() {
        path(baseURL + "/patients", () -> {
            post("", (request, response) -> {
                //aggiunta paziente
                if(request.contentType().contains("json")) {
                    Map<String, Object> map = gson.fromJson(request.body(), Map.class);

                    if(Checker.patientMapValidation(map)) {
                        if(PatientDB.insert(map)) {
                            response.status(201);
                            return "OK";
                        }
                    }
                }

                response.status(400);
                return "ERRORE";
            });

            path("/:patient_id", () -> {
                get("", (request, response) -> {
                    //get dati paziente
                    int patientId = Integer.parseInt(request.params("patient_id"));
                    Map<String, Object> query = PatientDB.select(patientId);

                    if(query != null) {
                        response.status(200);
                        response.type("application/json");

                        return JsonBuilder.jsonObject(query, LinksBuilder.patientLinks(patientId)).toString();
                    }

                    response.status(404);
                    return "";
                });
                put("", (request, response) -> {
                    if(request.contentType().contains("json")) {
                        Map map = gson.fromJson(request.body(), Map.class);

                        if(map != null && Checker.patientMapValidation(map)) {
                             map.put("id", Integer.parseInt(request.params("patient_id")));

                             if(PatientDB.update(map)) {
                                 response.status(200);
                                 return "OK";
                             }
                        }
                    }

                    response.status(400);
                    return "ERRORE";
                });
                delete("", (request, response) -> {
                    //cancellazione utente solo admin
                    return "";
                });
                path("/medics", () -> {
                    get("", (request, response) -> {
                        int patientId = Integer.parseInt(request.params("patient_id"));
                        List<Map<String, Object>> query = PatientDB.selectMedicsOfPatient(patientId);

                        if(query != null && query.size() != 0) {
                            response.status(200);
                            response.type("application/json");
                            return JsonBuilder.jsonList(null, query,null, "medic", null).toString();
                        }

                        response.status(404);
                        return "";
                    });
                    post("", (request, response) -> {
                        if(request.contentType().contains("json")) {
                            Map<String, Object> map = gson.fromJson(request.body(), Map.class);

                            if(map != null && Checker.patientAddMedic(map)) {
                                map.put("patient_id", Double.parseDouble(request.params("patient_id")));
                                if(MedicHasPatientDB.insert(map)) {
                                    response.status(201);
                                    return "OK";
                                }
                            }
                        }

                        response.status(400);
                        return "ERRORE";
                    });
                });

                //API DISPOSITIVI
                patientMeasures();

                //API MESSAGGI
                patientMessages();

                //API LOGIN_DATA
                patientLoginData();

                //API INITIAL_DATA
                patientInitialData();

                //API TASK
                patientTasks();

                //API PESO
                patientWeight();

            });
        });
    }

    private void patientMeasures() {
        path("/measures", () -> {
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
            path("/samples", () -> {
                get("", (request, response) -> {
                    int patientId = Integer.parseInt(request.params("patient_id"));
                    String r = JsonBuilder.jsonList(null, null, MeasuresLinks.measuresSubLinks(patientId, "samples"), null, null).toString();

                    if(r != null) {
                        response.status(200);
                        response.type("application/json");

                        return r;
                    }

                    response.status(404);
                    return "";
                });
                get("/fitbit", (request, response) -> {
                    //get fitbit
                    int patientId = Integer.parseInt(request.params("patient_id"));

                    List<Map<String, Object>> query;
                    String links;
                    String date = request.queryParams("date");
                    String startdate = request.queryParams("startdate");
                    String enddate = request.queryParams("enddate");

                    if(date != null && date.matches(DATE_REGEX)) {
                        query = FitbitDB.selectDate(patientId, date);
                        links = MeasuresLinks.fitbitLinks(patientId, "samples", date);
                    }
                    else if(startdate != null && enddate != null && (startdate.matches(DATE_REGEX) || startdate.matches(TIMEDATE_REGEX)) && (enddate.matches(DATE_REGEX) || enddate.matches(TIMEDATE_REGEX))) {
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
                get("/hue", (request, response) -> {
                    //get hue
                    int patientId = Integer.parseInt(request.params("patient_id"));
                    List<Map<String, Object>> query;
                    String links;
                    String date = request.queryParams("date");
                    String startdate = request.queryParams("startdate");
                    String enddate = request.queryParams("enddate");

                    if(date != null && date.matches(DATE_REGEX)) {
                        query = HueDB.selectDate(patientId, date);
                        links = MeasuresLinks.hueLinks(patientId, "samples", date);
                    }
                    else if(startdate != null && enddate != null && (startdate.matches(DATE_REGEX) || startdate.matches(TIMEDATE_REGEX)) && (enddate.matches(DATE_REGEX) || enddate.matches(TIMEDATE_REGEX))) {
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
                get("/sensor", (request, response) -> {
                    //get sensor
                    int patientId = Integer.parseInt(request.params("patient_id"));
                    List<Map<String, Object>> query;
                    String links;
                    String date = request.queryParams("date");
                    String startdate = request.queryParams("startdate");
                    String enddate = request.queryParams("enddate");

                    if(date != null && date.matches(DATE_REGEX)) {
                        query = SensorDB.selectDate(patientId, date);
                        links = MeasuresLinks.sensorLinks(patientId, "samples", date);
                    }
                    else if(startdate != null && enddate != null && (startdate.matches(DATE_REGEX) || startdate.matches(TIMEDATE_REGEX)) && (enddate.matches(DATE_REGEX) || enddate.matches(TIMEDATE_REGEX))) {
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
            });
            path("/total", () -> {
                get("", (request, response) -> {
                    int patientId = Integer.parseInt(request.params("patient_id"));
                    String r = JsonBuilder.jsonList(null, null, MeasuresLinks.measuresSubLinks(patientId, "total"), null, null).toString();

                    if(r != null) {
                        response.status(200);
                        response.type("application/json");

                        return r;
                    }

                    response.status(404);
                    return "";
                });
                get("/fitbit", (request, response) -> {
                    //get fitbit

                    return "";
                });
                get("/hue", (request, response) -> {
                    //get hue
                    return "";
                });
                get("/sensor", (request, response) -> {
                    //get sensor
                    return "";
                });
            });
        });
    }

    private void patientMessages() {
        path("/messages", () -> {
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
        });
    }

    private void patientLoginData() {
        path("/login_data", () -> {
            get("", (request, response) -> {
                int patientId = Integer.parseInt(request.params("patient_id"));
                Map<String, Object> query = LoginDB.selectPatient(patientId);

                if(query != null) {
                    response.status(200);
                    response.type("application/json");

                    return JsonBuilder.jsonObject(query, LinksBuilder.loginData(patientId, "patient")).toString();
                }

                response.status(404);
                return "";
            });
            post("", (request, response) -> {
                if(request.contentType().contains("json")) {
                    Map<String, Object> map = gson.fromJson(request.body(), Map.class);

                    if(map != null && Checker.loginDataMapValidation(map)) {
                        map.put("patient_id", Integer.parseInt(request.params("patient_id")));
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
                       map.put("patient_id", Integer.parseInt(request.params("patient_id")));

                       if(LoginDB.updatePatient(map)) {
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

    private void patientInitialData() {
        path("/initial_data", () -> {
            get("", (request, response) -> {
                //get dei dati iniziali della paziente
                int patientId = Integer.parseInt(request.params("patient_id"));
                Map<String, Object> query = PatientInitialDB.select(patientId);

                if(query != null) {
                    response.status(200);
                    response.type("application/json");

                    return JsonBuilder.jsonObject(query, LinksBuilder.initialData(patientId)).toString();
                }

                response.status(404);
                return "";
            });
            post("", (request, response) -> {
                if(request.contentType().contains("json")) {
                    Map<String, Object> map = gson.fromJson(request.body(), Map.class);

                    if(map != null && Checker.postPatientInitialDataMapValidation(map)) {
                        map.put("patient_id", Integer.parseInt(request.params("patient_id")));
                        if(PatientInitialDB.insert(map)) {
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

                    if(map != null && Checker.putPatientInitialDataMapValidation(map)) {
                        map.put("patient_id", Integer.parseInt(request.params("patient_id")));

                        if(PatientInitialDB.update(map)) {
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

    private void patientTasks() {
        path("/tasks", () -> {
            get("", (request, response) -> {
               int patientId = Integer.parseInt(request.params("patient_id"));

               response.status(200);
               response.type("application/json");

               return JsonBuilder.jsonList(null, null, PatientTasksLinks.tasksMenu(patientId, "patient"), null);
            });

            path("/general", () -> {
                get("", (request, response) -> {
                    //get task generali della paziente
                    int patientId = Integer.parseInt(request.params("patient_id"));
                    String medicId = request.queryParams("medic_id");
                    String executed = request.queryParams("executed");
                    String date = request.queryParams("date");
                    String startdate = request.queryParams("startdate");
                    String enddate = request.queryParams("enddate");
                    String starting_program = request.queryParams("starting_program");
                    List<Map<String, Object>> query;
                    String links;

                    if(starting_program != null && starting_program.equals("1")) {
                        query = TaskGeneralDB.selectProgram(patientId, "patient");
                        links = PatientTasksLinks.patientGeneralLinks(patientId, medicId, executed, starting_program);
                    }
                    else if(date!=null && date.matches(DATE_REGEX)) {
                        query = TaskGeneralDB.selectDate(patientId, medicId, date, executed, "patient");
                        links = PatientTasksLinks.patientGeneralLinks(patientId, medicId, executed, starting_program, date);
                    }
                    else if(startdate != null && enddate != null && startdate.matches(DATE_REGEX) && enddate.matches(DATE_REGEX)) {
                        query = TaskGeneralDB.selectDateInterval(patientId, medicId, startdate, enddate, executed, "patient");
                        links = PatientTasksLinks.patientGeneralLinks(patientId, medicId, executed, starting_program, startdate, enddate);
                    }
                    else {
                        query = TaskGeneralDB.select(patientId, medicId, executed, "patient");
                        links = PatientTasksLinks.patientGeneralLinks(patientId, medicId, executed, starting_program);
                    }

                    if(query != null && query.size() > 0) {
                        response.status(200);
                        response.type("application/json");

                        return "{ " + JsonBuilder.jsonList("general", query, links, "task", "general", "patient") + " }";
                    }

                    response.status(404);
                    return "";
                });

                patientTask("general");
            });
            path("/activities", () -> {
                get("", (request, response) -> {
                    //get attività della paziente
                    int patientId = Integer.parseInt(request.params("patient_id"));
                    String medicId = request.queryParams("medic_id");
                    String executed = request.queryParams("executed");
                    String date = request.queryParams("date");
                    String startdate = request.queryParams("startdate");
                    String enddate = request.queryParams("enddate");
                    String starting_program = request.queryParams("starting_program");
                    List<Map<String, Object>> query;
                    String links;

                    if(starting_program != null && starting_program.equals("1")) {
                        query = TaskActivityDB.selectProgram(patientId, "patient");
                        links = PatientTasksLinks.patientActivitiesLinks(patientId, medicId, executed, starting_program);
                    }
                    else if(date!=null && date.matches(DATE_REGEX)) {
                        query = TaskActivityDB.selectDate(patientId, medicId, date, executed, "patient");
                        links = PatientTasksLinks.patientActivitiesLinks(patientId, medicId, executed, starting_program, date);
                    }
                    else if(startdate != null && enddate != null && startdate.matches(DATE_REGEX) && enddate.matches(DATE_REGEX)) {
                        query = TaskActivityDB.selectDateInterval(patientId, medicId, startdate, enddate, executed, "patient");
                        links = PatientTasksLinks.patientActivitiesLinks(patientId, medicId, executed, starting_program, startdate, enddate);
                    }
                    else {
                        query = TaskActivityDB.select(patientId, medicId, executed, "patient");
                        links = PatientTasksLinks.patientActivitiesLinks(patientId, medicId, executed, starting_program);
                    }

                    if(query != null && query.size() > 0) {
                        response.status(200);
                        response.type("application/json");

                        return "{ " + JsonBuilder.jsonList("activities", query, links, "task", "activities", "patient") + " }";
                    }

                    response.status(404);
                    return "";
                });

                patientTask("activities");
            });
            path("/diets", () -> {
                get("", (request, response) -> {
                    //get task generali della paziente
                    int patientId = Integer.parseInt(request.params("patient_id"));
                    String medicId = request.queryParams("medic_id");
                    String executed = request.queryParams("executed");
                    String date = request.queryParams("date");
                    String startdate = request.queryParams("startdate");
                    String enddate = request.queryParams("enddate");
                    String starting_program = request.queryParams("starting_program");
                    List<Map<String, Object>> query;
                    String links;

                    if(starting_program != null && starting_program.equals("1")) {
                        query = TaskDietDB.selectProgram(patientId, "patient");
                        links = PatientTasksLinks.patientDietsLinks(patientId, medicId, executed, starting_program);
                    }
                    else if(date!=null && date.matches(DATE_REGEX)) {
                        query = TaskDietDB.selectDate(patientId, medicId, date, executed, "patient");
                        links = PatientTasksLinks.patientDietsLinks(patientId, medicId, executed, starting_program, date);
                    }
                    else if(startdate != null && enddate != null && startdate.matches(DATE_REGEX) && enddate.matches(DATE_REGEX)) {
                        query = TaskDietDB.selectDateInterval(patientId, medicId, startdate, enddate, executed, "patient");
                        links = PatientTasksLinks.patientDietsLinks(patientId, medicId, executed, starting_program, startdate, enddate);
                    }
                    else {
                        query = TaskDietDB.select(patientId, medicId, executed, "patient");
                        links = PatientTasksLinks.patientDietsLinks(patientId, medicId, executed, starting_program);
                    }

                    if(query != null && query.size() > 0) {
                        response.status(200);
                        response.type("application/json");

                        return "{ " + JsonBuilder.jsonList("diets", query, links, "task", "diets", "patient") + " }";
                    }

                    response.status(404);
                    return "";
                });

                patientTask("diets");
            });
        });
    }

    private void patientTask(String taskCategory) {
        get("/:task_id", (request, response) -> {
            //get singolo task
            int patientId = Integer.parseInt(request.params("patient_id"));
            int taskId = Integer.parseInt(request.params("task_id"));
            Map<String, Object> query = new LinkedHashMap<>();

            switch (taskCategory) {
                case "general":
                    query = SharedTaskFunctionDB.selectSingleTaskGeneral(patientId, taskId, "patient");
                    break;
                case "activities":
                    query = SharedTaskFunctionDB.selectSingleTaskActivities(patientId, taskId, "patient");
                    break;
                case "diets":
                    query = SharedTaskFunctionDB.selectSingleTaskDiets(patientId, taskId, "patient");
                    break;
            }

            if(query != null) {
                response.status(200);
                response.type("application/json");
                return JsonBuilder.jsonObject(query, PatientTasksLinks.patientSingleTaskLinks(patientId, taskId, (int) query.get("medic_id"), taskCategory));
            }

            response.status(404);
            return "";
        });
        put("/:task_id", (request, response) -> {
            if(request.contentType().contains("json")) {
                Map map = gson.fromJson(request.body(), Map.class);

                if(map != null && Checker.putPatientTaskMapValidation(map)) {
                    map.put("patient_id", Integer.parseInt(request.params("patient_id")));
                    map.put("id", Integer.parseInt(request.params("task_id")));
                    boolean result = false;

                    switch(taskCategory) {
                        case "general":
                            result = TaskGeneralDB.updatePatient(map);

                            break;
                        case "activities":
                            result = TaskActivityDB.updatePatient(map);

                            break;
                        case "diets":
                            result = TaskDietDB.updatePatient(map);

                            break;
                    }

                    if(result) {
                        response.status(200);
                        return "OK";
                    }
                }
            }

            response.status(400);
            return "ERRORE";
        });
    }

    private void patientWeight() {
        path("/weight", () -> {
            get("", (request, response) -> {
                //get pesi della paziente
                int patientId = Integer.parseInt(request.params("patient_id"));
                String date = request.queryParams("date");

                if(date == null) {
                    List<Map<String, Object>> query = WeightDB.selectList(patientId);

                    if(query != null && query.size() > 0) {
                        response.status(200);
                        response.type("application/json");

                        return "{ "+ JsonBuilder.jsonList("weights", query, LinksBuilder.weightsLinks(patientId), "weight").toString() + " }";
                    }
                }
                else {
                    Map<String, Object> query = WeightDB.selectSingleWeight(patientId, date);
                    if(query != null) {
                        response.status(200);
                        response.type("application/json");

                        return JsonBuilder.jsonObject(query, null).toString();
                    }
                }

                response.status(404);
                return "";
            });
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
        });
    }
}
