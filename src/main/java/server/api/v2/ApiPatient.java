package server.api.v2;

import com.google.gson.Gson;
import server.database2.*;

import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class ApiPatient {

    private String baseURL = "/api/v2";
    Gson gson = new Gson();

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
                    List<Map<String, Object>> query = MedicDB.selectMedicsOfPatient(patientId);

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
                String r = JsonBuilder.jsonList(null, null, LinksBuilder.measuresLinks(patientId), null, null).toString();;

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
                    String r = JsonBuilder.jsonList(null, null, LinksBuilder.messagesCategoryLinks(patientId, "samples"), null, null).toString();

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
                    List<Map<String, Object>> query = FitbitDB.select(patientId);

                    if(query.size() != 0) {
                        response.status(200);
                        response.type("application/json");

                        return "{ " + JsonBuilder.jsonList("fitbit-samples", query, LinksBuilder.fitbitLinks(patientId, "samples"), "test", null).toString() + " }";
                    }

                    response.status(404);
                    return "";
                });
                get("/hue", (request, response) -> {
                    //get hue
                    int patientId = Integer.parseInt(request.params("patient_id"));
                    List<Map<String, Object>> query = HueDB.select(patientId);

                    if(query.size() != 0) {
                        response.status(200);
                        response.type("application/json");

                        return "{ " + JsonBuilder.jsonList("hue-samples", query, LinksBuilder.hueLinks(patientId, "samples"), "test", null).toString() + " }";
                    }

                    response.status(404);
                    return "";
                });
                get("/sensor", (request, response) -> {
                    //get sensor
                    int patientId = Integer.parseInt(request.params("patient_id"));
                    List<Map<String, Object>> query = SensorDB.select(patientId);

                    if(query.size() != 0) {
                        response.status(200);
                        response.type("application/json");

                        return "{ " + JsonBuilder.jsonList("sensor-samples", query, LinksBuilder.sensorLinks(patientId, "samples"), "test", null).toString() + " }";
                    }

                    response.status(404);
                    return "";
                });
            });
            path("/total", () -> {
                get("", (request, response) -> {
                    int patientId = Integer.parseInt(request.params("patient_id"));
                    String r = JsonBuilder.jsonList(null, null, LinksBuilder.measuresSubLinks(patientId, "total"), null, null).toString();

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
            path("/general", () -> {
                get("", (request, response) -> {
                    //get task generali della paziente
                    return "";
                });

                patientTask();
            });
            path("/activities", () -> {
                get("", (request, response) -> {
                    //get attività della paziente
                    return "";
                });

                patientTask();
            });
            path("/diets", () -> {
                get("", (request, response) -> {
                    //get task generali della paziente
                    return "";
                });

                patientTask();
            });
        });
    }

    private void patientTask() {
        get("/:task_id", (request, response) -> {
            //get singolo task
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
                String links = new String();

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
