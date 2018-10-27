package server.api.v2;

import com.google.gson.Gson;
import server.database2.*;

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

                    String r = JsonBuilder.jsonObject(PatientDB.select(patientId), LinksBuilder.patientLinks(patientId)).toString();

                    if(r != null) {
                        response.status(200);
                        response.type("application/json");

                        return r;
                    }

                    response.status(200);
                    return "404";
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
                    String patientId = request.params("patient_id");
                    String r = JsonBuilder.jsonList(null, MedicDB.selectMedicsOfPatient(patientId),null, "medic").toString();

                    if(r != null) {
                        response.status(200);
                        response.type("application/json");

                        return r;
                    }

                    response.status(404);
                    return "404";
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
                String patientId = request.params("patient_id");
                String r = "[ " + JsonBuilder.jsonObject(null, LinksBuilder.measuresLinks(patientId)).toString() + " ]";

                if(r != null) {
                    response.status(200);
                    response.type("application/json");

                    return r;
                }

                response.status(404);
                return "404";
            });
            path("/samples", () -> {
                get("", (request, response) -> {
                    String patientId = request.params("patient_id");
                    String r = "[ " + JsonBuilder.jsonObject(null, LinksBuilder.measuresSubLinks(patientId, "samples")).toString() + " ]";

                    if(r != null) {
                        response.status(200);
                        response.type("application/json");

                        return r;
                    }

                    response.status(404);
                    return "404";
                });
                get("/fitbit", (request, response) -> {
                    //get fitbit
                    String patientId = request.params("patient_id");
                    String r = "{ " + JsonBuilder.jsonList("fitbit-samples", FitbitDB.select(patientId), LinksBuilder.fitbitLinks(patientId, "samples"), "test").toString() + " }";

                    if(r != null) {
                        response.status(200);
                        response.type("application/json");

                        return r;
                    }

                    response.status(404);
                    return "404";
                });
                get("/hue", (request, response) -> {
                    //get hue
                    String patientId = request.params("patient_id");
                    String r = "{ " + JsonBuilder.jsonList("hue-samples", HueDB.select(patientId), LinksBuilder.hueLinks(patientId, "samples"), "test").toString() + " }";

                    if(r != null) {
                        response.status(200);
                        response.type("application/json");

                        return r;
                    }

                    response.status(404);
                    return "404";
                });
                get("/sensor", (request, response) -> {
                    //get sensor
                    String patientId = request.params("patient_id");
                    String r = "{ " + JsonBuilder.jsonList("sensor-samples", SensorDB.select(patientId), LinksBuilder.fitbitLinks(patientId, "samples"), "test").toString() + " }";

                    if(r != null) {
                        response.status(200);
                        response.type("application/json");

                        return r;
                    }

                    response.status(404);
                    return "404";
                });
            });
            path("/measures/total", () -> {
                get("", (request, response) -> {
                    String patientId = request.params("patient_id");
                    String r = "[ " + JsonBuilder.jsonObject(null, LinksBuilder.measuresSubLinks(patientId, "total")).toString() + " ]";

                    if(r != null) {
                        response.status(200);
                        response.type("application/json");

                        return r;
                    }

                    response.status(404);
                    return "404";
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
            post("", (request, response) -> {
                //aggiunta un nuovo messaggio
                return "";
            });
            get("/sent", (request, response) -> {
                //get dei messaggi inviati paziente
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
