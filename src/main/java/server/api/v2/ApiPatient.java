package server.api.v2;

import com.google.gson.Gson;
import server.api.v2.links.LinksBuilder;
import server.api.v2.links.MeasuresLinks;
import server.api.v2.links.TaskLinks;
import server.database2.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class ApiPatient {

    private String baseURL = "/api/v2";
    Gson gson = new Gson();
    private final static String DATE_REGEX = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$";
    private final static String TIMEDATE_REGEX = "^(19|20)\\d\\d-(0[1-9]|1[012])-([012]\\d|3[01])T([01]\\d|2[0-3]):([0-5]\\d):([0-5]\\d)$";

    public ApiPatient() {
        apiPatient();
    }

    private void apiPatient() {
        path(baseURL + "/patients", () -> {
            post("", (request, response) -> {
                //aggiunta paziente
                return "";
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
                    //modifica dati paziente
                    return "";
                });
                delete("", (request, response) -> {
                    //cancellazione utente solo admin
                    return "";
                });
                get("/medics", (request, response) -> {
                    int patientId = Integer.parseInt(request.params("patient_id"));
                    List<Map<String, Object>> query = PatientDB.selectMedicsOfPatient(patientId);

                    if(query.size() != 0) {
                        response.status(200);
                        response.type("application/json");
                        return JsonBuilder.jsonList(null, query,null, "medic", null).toString();
                    }

                    response.status(404);
                    return "";
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

                    if(query.size() > 0) {
                        response.status(200);
                        response.type("application/json");

                        return "{ " + JsonBuilder.jsonList("fitbit-samples", query, links, "test", null).toString() + " }";
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

                    if(query.size() > 0) {
                        response.status(200);
                        response.type("application/json");

                        return "{ " + JsonBuilder.jsonList("hue-samples", query, links, "test", null).toString() + " }";
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

                    if(query.size() > 0) {
                        response.status(200);
                        response.type("application/json");

                        return "{ " + JsonBuilder.jsonList("sensor-samples", query, links, "test", null).toString() + " }";
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
                        return JsonBuilder.jsonObject(map, null).toString();
                    }
                }
                else if(date != null) {
                    List<Map<String, Object>> list = MessageMedicPatientDB.selectPatientSent(patientId, medic_id, date, null);

                    if(list.size() != 0) {
                        response.status(200);
                        response.type("application/json");

                        return "{ " + JsonBuilder.jsonList("messages-sent", list, LinksBuilder.messagesLinks(patientId, "patient", "sent", medic_id, date, null), "message", new String[]{"patient", "sent"}).toString() + " }";
                    }
                }
                else {
                    List<Map<String, Object>> list = MessageMedicPatientDB.selectPatientSent(patientId, medic_id, startdate, enddate);

                    if(list.size() != 0) {
                        response.status(200);
                        response.type("application/json");

                        return "{ " + JsonBuilder.jsonList("messages-sent", list, LinksBuilder.messagesLinks(patientId, "patient", "sent", medic_id, startdate, enddate), "message", new String[]{"patient", "sent"}).toString() + " }";
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
                        return JsonBuilder.jsonObject(map, null).toString();
                    }
                }
                else if(date != null) {
                    List<Map<String, Object>> list = MessageMedicPatientDB.selectPatientReceived(patientId, medic_id, date, null);

                    if(list.size() != 0) {
                        response.status(200);
                        response.type("application/json");

                        return "{ " + JsonBuilder.jsonList("messages-received", list, LinksBuilder.messagesLinks(patientId, "patient", "received", medic_id, date, null), "message", new String[]{"patient", "received"}).toString() + " }";
                    }
                }
                else {
                    List<Map<String, Object>> list = MessageMedicPatientDB.selectPatientReceived(patientId, medic_id, startdate, enddate);

                    if(list.size() != 0) {
                        response.status(200);
                        response.type("application/json");

                        return "{ " + JsonBuilder.jsonList("messages-received", list, LinksBuilder.messagesLinks(patientId, "patient", "received", medic_id, startdate, enddate), "message", new String[]{"patient", "received"}).toString() + " }";
                    }
                }

                response.status(404);
                return "";
            });
        });
    }

    private void patientLoginData() {
        path("/login_data", () -> {
            get("", (request, response) -> {
                //get dati login paziente
                int patientId = Integer.parseInt(request.params("patient_id"));
                Map<String, Object> query = LoginDB.selectPatient(patientId);

                if(query != null) {
                    response.status(200);
                    response.type("application/json");

                    return JsonBuilder.jsonObject(query, LinksBuilder.loginData(patientId, "patients")).toString();
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
               //aggiunta del dato iniziale solo admin
               return "";
            });
            put("", (request, response) -> {
                //modifica twin solo medico
                return "";
            });
        });
    }

    private void patientTasks() {
        path("/tasks", () -> {
            get("", (request, response) -> {
               int patientId = Integer.parseInt(request.params("patient_id"));

               response.status(200);
               response.type("application/json");

               return JsonBuilder.jsonList(null, null, TaskLinks.tasksMenu(patientId, "patient"), null, null);
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
                        query = TaskGeneralDB.selectProgram(patientId);
                        links = TaskLinks.patientGeneralLinks(patientId, medicId, executed, starting_program);
                    }
                    else if(date!=null && date.matches(DATE_REGEX)) {
                        query = TaskGeneralDB.selectDate(patientId, medicId, date, executed);
                        links = TaskLinks.patientGeneralLinks(patientId, medicId, executed, starting_program, date);
                    }
                    else if(startdate != null && enddate != null && startdate.matches(DATE_REGEX) && enddate.matches(DATE_REGEX)) {
                        query = TaskGeneralDB.selectDateInterval(patientId, medicId, startdate, enddate, executed);
                        links = TaskLinks.patientGeneralLinks(patientId, medicId, executed, starting_program, startdate, enddate);
                    }
                    else {
                        query = TaskGeneralDB.select(patientId, medicId, executed);
                        links = TaskLinks.patientGeneralLinks(patientId, medicId, executed, starting_program);
                    }

                    if(query.size() > 0) {
                        response.status(200);
                        response.type("application/json");

                        return "{ " + JsonBuilder.jsonList("general", query, links, "task", "general") + " }";
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
                        query = TaskActivityDB.selectProgram(patientId);
                        links = TaskLinks.patientActivitiesLinks(patientId, medicId, executed, starting_program);
                    }
                    else if(date!=null && date.matches(DATE_REGEX)) {
                        query = TaskActivityDB.selectDate(patientId, medicId, date, executed);
                        links = TaskLinks.patientActivitiesLinks(patientId, medicId, executed, starting_program, date);
                    }
                    else if(startdate != null && enddate != null && startdate.matches(DATE_REGEX) && enddate.matches(DATE_REGEX)) {
                        query = TaskActivityDB.selectDateInterval(patientId, medicId, startdate, enddate, executed);
                        links = TaskLinks.patientActivitiesLinks(patientId, medicId, executed, starting_program, startdate, enddate);
                    }
                    else {
                        query = TaskActivityDB.select(patientId, medicId, executed);
                        links = TaskLinks.patientActivitiesLinks(patientId, medicId, executed, starting_program);
                    }

                    if(query.size() > 0) {
                        response.status(200);
                        response.type("application/json");

                        return "{ " + JsonBuilder.jsonList("activities", query, links, "task", "activities") + " }";
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
                        query = TaskDietDB.selectProgram(patientId);
                        links = TaskLinks.patientDietsLinks(patientId, medicId, executed, starting_program);
                    }
                    else if(date!=null && date.matches(DATE_REGEX)) {
                        query = TaskDietDB.selectDate(patientId, medicId, date, executed);
                        links = TaskLinks.patientDietsLinks(patientId, medicId, executed, starting_program, date);
                    }
                    else if(startdate != null && enddate != null && startdate.matches(DATE_REGEX) && enddate.matches(DATE_REGEX)) {
                        query = TaskDietDB.selectDateInterval(patientId, medicId, startdate, enddate, executed);
                        links = TaskLinks.patientDietsLinks(patientId, medicId, executed, starting_program, startdate, enddate);
                    }
                    else {
                        query = TaskDietDB.select(patientId, medicId, executed);
                        links = TaskLinks.patientDietsLinks(patientId, medicId, executed, starting_program);
                    }

                    if(query.size() > 0) {
                        response.status(200);
                        response.type("application/json");

                        return "{ " + JsonBuilder.jsonList("diets", query, links, "task", "diets") + " }";
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

            if(taskCategory.equals("general"))
                query = SharedTaskFunctionDB.selectSingleTaskGeneral(patientId, taskId);
            else if(taskCategory.equals("activities"))
                query = SharedTaskFunctionDB.selectSingleTaskActivities(patientId, taskId);
            else if(taskCategory.equals("diets"))
                query = SharedTaskFunctionDB.selectSingleTaskDiets(patientId, taskId);

            if(query != null) {
                response.status(200);
                response.type("application/json");
                return JsonBuilder.jsonObject(query, TaskLinks.patientSingleTaskLinks(patientId, taskId, (int) query.get("medic_id"), taskCategory));
            }

            response.status(404);
            return "";
        });
        put("/:task_id", (request, response) -> {
            //modifica task solo executed
            return "";
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

                    if(query.size() != 0) {
                        response.status(200);
                        response.type("application/json");

                        return "{ "+ JsonBuilder.jsonList("weights", query, LinksBuilder.weightsLinks(patientId), "weight", null).toString() + " }";
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
                //aggiunta di un nuovo peso
                return "";
            });
            put("", (request, response) ->{
                //modifica ultimo peso inserito
                return "";
            });
        });
    }
}
