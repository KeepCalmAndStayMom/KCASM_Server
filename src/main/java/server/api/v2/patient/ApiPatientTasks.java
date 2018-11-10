package server.api.v2.patient;

import com.google.gson.Gson;
import server.api.v2.Checker;
import server.api.v2.JsonBuilder;
import server.api.v2.Regex;
import server.api.v2.links.PatientTasksLinks;
import server.database.v2.SharedTaskFunctionDB;
import server.database.v2.TaskActivityDB;
import server.database.v2.TaskDietDB;
import server.database.v2.TaskGeneralDB;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.put;

public class ApiPatientTasks {

    private Gson gson = new Gson();

    public ApiPatientTasks() {
        patientTasks();
    }

    private void patientTasks() {
        path("/tasks", () -> {
            showTasksCategory();

            path("/general", () -> {
                getGeneralTasks();

                singleTaskOperations("general");
            });
            path("/activities", () -> {
                getActivities();

                singleTaskOperations("activities");
            });
            path("/diets", () -> {
                getDiets();

                singleTaskOperations("diets");
            });
        });
    }

    private void getDiets() {
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
            else if(date!=null && date.matches(Regex.DATE_REGEX)) {
                query = TaskDietDB.selectDate(patientId, medicId, date, executed, "patient");
                links = PatientTasksLinks.patientDietsLinks(patientId, medicId, executed, starting_program, date);
            }
            else if(startdate != null && enddate != null && startdate.matches(Regex.DATE_REGEX) && enddate.matches(Regex.DATE_REGEX)) {
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
    }

    private void getActivities() {
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
            else if(date!=null && date.matches(Regex.DATE_REGEX)) {
                query = TaskActivityDB.selectDate(patientId, medicId, date, executed, "patient");
                links = PatientTasksLinks.patientActivitiesLinks(patientId, medicId, executed, starting_program, date);
            }
            else if(startdate != null && enddate != null && startdate.matches(Regex.DATE_REGEX) && enddate.matches(Regex.DATE_REGEX)) {
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
    }

    private void getGeneralTasks() {
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
            else if(date!=null && date.matches(Regex.DATE_REGEX)) {
                query = TaskGeneralDB.selectDate(patientId, medicId, date, executed, "patient");
                links = PatientTasksLinks.patientGeneralLinks(patientId, medicId, executed, starting_program, date);
            }
            else if(startdate != null && enddate != null && startdate.matches(Regex.DATE_REGEX) && enddate.matches(Regex.DATE_REGEX)) {
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
    }

    private void showTasksCategory() {
        get("", (request, response) -> {
            int patientId = Integer.parseInt(request.params("patient_id"));

            response.status(200);
            response.type("application/json");

            return JsonBuilder.jsonList(null, null, PatientTasksLinks.tasksMenu(patientId, "patient"), null);
        });
    }

    private void singleTaskOperations(String taskCategory) {
        getSingleTask(taskCategory);

        setTaskAsCompleted(taskCategory);
    }

    private void setTaskAsCompleted(String taskCategory) {
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

    private void getSingleTask(String taskCategory) {
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
    }
}
