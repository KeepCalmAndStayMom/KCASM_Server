package server.api.v2;

import static spark.Spark.*;

public class ApiMedic {
    private String baseURL = "/api/v2";

    private void apiMedic() {
        path(baseURL + "/medics", () -> {
            post("", (request, response) -> {
                //aggiunta di un nuovo medico solo admin
                return "";
            });

            path("/:medic_id", () -> {
                get("", (request, response) -> {
                   //get dati medico
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
