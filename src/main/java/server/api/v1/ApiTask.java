package server.api.v1;

import com.google.gson.Gson;
import server.database.v1.TaskDB;
import java.util.Map;
import static spark.Spark.*;

class ApiTask {

    private Gson gson = new Gson();
    private String baseURL = "/api";

    ApiTask(){
        sparkGetTask();
        sparkGetAllTasks();
        sparkGetTasksWithDate();
        sparkGetTasksToExecute();
        sparkGetExecutedTasks();
        sparkPostTask();
        sparkPutTask();
        sparkDeleteTask();
    }

    private void sparkPostTask() {
        post(baseURL + "/tasks", (request, response) -> {

            Map addRequest = gson.fromJson(request.body(), Map.class);
            response.type("application/json");

            if (addRequest != null && addRequest.containsKey("homestation_id")
                    && addRequest.containsKey("title")
                    && addRequest.containsKey("description")
                    && addRequest.containsKey("programmed_date"))
            {
                Integer homestation_id = ((Double) addRequest.get("homestation_id")).intValue();
                String title = String.valueOf(addRequest.get("title"));
                String description = String.valueOf(addRequest.get("description"));
                String programmed_date = String.valueOf(addRequest.get("programmed_date"));

                TaskDB.addTask(homestation_id, title, description, programmed_date);
            }
            else {
                response.status(400);
                return "{\"Errore 400\": \"richiesta non valida\" }";
            }

            response.status(201);
            return "{ \"Success\":\"risorsa aggiunta\" }";
        });
    }

    private void sparkGetTask() {
        get(baseURL + "/task/:id", "application/json", (request, response) -> {

            String json = TaskDB.getTask(Integer.valueOf(request.params(":id")));
            response.type("application/json");

            if (json == null || json.equals("}")) {
                response.status(404);
                return "{ \"Errore 404\": \"risorsa non trovata\" }";
            }

            response.status(200);
            return json;
        });
    }

    private void sparkGetAllTasks() {
        get(baseURL + "/tasks/:homestation_id", "application/json", (request, response) -> {

            String json = TaskDB.getAllTasks(Integer.valueOf(request.params(":homestation_id")));
            response.type("application/json");

            if (json == null || json.equals("}")) {
                response.status(404);
                return "{ \"Errore 404\": \"risorsa non trovata\" }";
            }

            response.status(200);
            return json;
        });
    }

    private void sparkGetTasksToExecute() {
        get(baseURL + "/tasks_to_execute/:homestation_id", "application/json", (request, response) -> {

            String json = TaskDB.getTasksToExecute(Integer.valueOf(request.params(":homestation_id")));
            response.type("application/json");

            if (json == null || json.equals("}")) {
                response.status(404);
                return "{ \"Errore 404\": \"risorsa non trovata\" }";
            }

            response.status(200);
            return json;
        });

    }

    private void sparkGetExecutedTasks() {
        get(baseURL + "/tasks_executed/:homestation_id", "application/json", (request, response) -> {

            String json = TaskDB.getExecutedTasks(Integer.valueOf(request.params(":homestation_id")));
            response.type("application/json");

            if (json == null || json.equals("}")) {
                response.status(404);
                return "{ \"Errore 404\": \"risorsa non trovata\" }";
            }

            response.status(200);
            return json;
        });

    }

    private void sparkGetTasksWithDate() {
        get(baseURL + "/tasks/:homestation_id/:date", "application/json", (request, response) -> {

            String json = TaskDB.getTasksWithDate(Integer.valueOf(request.params(":homestation_id")), request.params(":date"));
            response.type("application/json");

            if (json == null || json.equals("}")) {
                response.status(404);
                return "{ \"Errore 404\": \"risorsa non trovata\" }";
            }

            response.status(200);
            return json;
        });
    }

    private void sparkPutTask() {
        put(baseURL + "/task/:id", (request, response) -> {

            Map addRequest = gson.fromJson(request.body(), Map.class);
            response.type("application/json");

            if (addRequest != null && addRequest.containsKey("executed"))
            {
                String executed = String.valueOf(addRequest.get("executed"));
                if(!TaskDB.updateTask(Integer.valueOf(request.params(":id")), executed)) {
                    response.status(400);
                    return "{\"Errore 400\": \"richiesta non valida\" }";
                }
            }
            else {
                response.status(400);
                return "{\"Errore 400\": \"richiesta non valida\" }";
            }

            response.status(200);
            return "{ \"Success\":\"risorsa modificata\" }";
        });
    }

    private void sparkDeleteTask() {
        delete(baseURL + "/task/:id", (request, response) -> {
            if(!TaskDB.deleteTask(Integer.valueOf(request.params(":id")))) {
                response.status(404);
                return "{\"Errore 404\": \"risorsa non trovata\" }";
            }

            response.status(200);
            return "{ \"Success\":\"risorsa eliminata\" }";
        });
    }

}
