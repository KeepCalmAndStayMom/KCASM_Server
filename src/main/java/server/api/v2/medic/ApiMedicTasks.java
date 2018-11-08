package server.api.v2.medic;

import com.google.gson.Gson;
import server.api.v2.Checker;
import server.api.v2.JsonBuilder;
import server.api.v2.Regex;
import server.api.v2.links.MedicTasksLinks;
import server.database2.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class ApiMedicTasks {
    private Gson gson = new Gson();

    public ApiMedicTasks() {
        medicTasks();
    }

    private void medicTasks() {
        path("/tasks", () -> {
            get("", (request, response) -> {
                int medicId = Integer.parseInt(request.params("medic_id"));

                response.status(200);
                response.type("application/json");

                return JsonBuilder.jsonList(null, null, MedicTasksLinks.tasksMenu(medicId, "medic"), null);
            });

            path("/general", () -> {
                get("", (request, response) -> {
                    //get task generali medico
                    int medicId = Integer.parseInt(request.params("medic_id"));
                    String patientId = request.queryParams("patient_id");
                    String executed = request.queryParams("executed");
                    String date = request.queryParams("date");
                    String startdate = request.queryParams("startdate");
                    String enddate = request.queryParams("enddate");
                    String starting_program = request.queryParams("starting_program");
                    List<Map<String, Object>> query;
                    String links;

                    if(starting_program != null && starting_program.equals("1")) {
                        query = TaskGeneralDB.selectProgram(medicId, "medic");
                        links = MedicTasksLinks.medicGeneralLinks(medicId, patientId, executed, starting_program);
                    }
                    else if(date!=null && date.matches(Regex.DATE_REGEX)) {
                        if(patientId != null)
                            query = TaskGeneralDB.selectDate(Integer.parseInt(patientId), String.valueOf(medicId), date, executed, "medic");
                        else
                            query = TaskGeneralDB.selectDate(null, String.valueOf(medicId), date, executed, "medic");

                        links = MedicTasksLinks.medicGeneralLinks(medicId, patientId, executed, starting_program, date);
                    }
                    else if(startdate != null && enddate != null && startdate.matches(Regex.DATE_REGEX) && enddate.matches(Regex.DATE_REGEX)) {
                        if(patientId != null)
                            query = TaskGeneralDB.selectDateInterval(Integer.parseInt(patientId), String.valueOf(medicId), startdate, enddate, executed, "medic");
                        else
                            query = TaskGeneralDB.selectDateInterval(null, String.valueOf(medicId), startdate, enddate, executed, "medic");

                        links = MedicTasksLinks.medicGeneralLinks(medicId, patientId, executed, starting_program, startdate, enddate);
                    }
                    else {
                        if(patientId != null)
                            query = TaskGeneralDB.select(Integer.parseInt(patientId), String.valueOf(medicId), executed, "medic");
                        else
                            query = TaskGeneralDB.select(null, String.valueOf(medicId), executed, "medic");

                        links = MedicTasksLinks.medicGeneralLinks(medicId, patientId, executed, starting_program);
                    }

                    if(query != null && query.size() > 0) {
                        response.status(200);
                        response.type("application/json");

                        return "{ " + JsonBuilder.jsonList("general", query, links, "task", "general", "medic") + " }";
                    }

                    response.status(404);
                    return "";
                });

                medicTask("general");
            });
            path("/activities", () -> {
                get("", (request, response) -> {
                    //get attività medico
                    int medicId = Integer.parseInt(request.params("medic_id"));
                    String patientId = request.queryParams("patient_id");
                    String executed = request.queryParams("executed");
                    String date = request.queryParams("date");
                    String startdate = request.queryParams("startdate");
                    String enddate = request.queryParams("enddate");
                    String starting_program = request.queryParams("starting_program");
                    List<Map<String, Object>> query;
                    String links;

                    if(starting_program != null && starting_program.equals("1")) {
                        query = TaskActivityDB.selectProgram(medicId, "medic");
                        links = MedicTasksLinks.medicActivitiesLinks(medicId, patientId, executed, starting_program);
                    }
                    else if(date!=null && date.matches(Regex.DATE_REGEX)) {
                        if(patientId !=null)
                            query = TaskActivityDB.selectDate(Integer.parseInt(patientId), String.valueOf(medicId), date, executed,"medic");
                        else
                            query = TaskActivityDB.selectDate(null, String.valueOf(medicId), date, executed,"medic");
                        links = MedicTasksLinks.medicActivitiesLinks(medicId, patientId, executed, starting_program, date);
                    }
                    else if(startdate != null && enddate != null && startdate.matches(Regex.DATE_REGEX) && enddate.matches(Regex.DATE_REGEX)) {
                        if(patientId != null)
                            query = TaskActivityDB.selectDateInterval(Integer.parseInt(patientId), String.valueOf(medicId), startdate, enddate, executed, "patient");
                        else
                            query = TaskActivityDB.selectDateInterval(null, String.valueOf(medicId), startdate, enddate, executed,"medic");
                        links = MedicTasksLinks.medicActivitiesLinks(medicId, patientId, executed, starting_program, startdate, enddate);
                    }
                    else {
                        if(patientId != null)
                            query = TaskActivityDB.select(Integer.parseInt(patientId), String.valueOf(medicId), executed,"medic");
                        else
                            query = TaskActivityDB.select(null, String.valueOf(medicId), executed,"medic");
                        links = MedicTasksLinks.medicActivitiesLinks(medicId, patientId, executed, starting_program);
                    }

                    if(query.size() > 0) {
                        response.status(200);
                        response.type("application/json");

                        return "{ " + JsonBuilder.jsonList("actvities", query, links, "task", "activities", "medic") + " }";
                    }

                    response.status(404);
                    return "";
                });

                medicTask("activities");
            });
            path("/diets", () -> {
                get("", (request, response) -> {
                    //get diete medico
                    int medicId = Integer.parseInt(request.params("medic_id"));
                    String patientId = request.queryParams("patient_id");
                    String executed = request.queryParams("executed");
                    String date = request.queryParams("date");
                    String startdate = request.queryParams("startdate");
                    String enddate = request.queryParams("enddate");
                    String starting_program = request.queryParams("starting_program");
                    List<Map<String, Object>> query;
                    String links;

                    if(starting_program != null && starting_program.equals("1")) {
                        query = TaskDietDB.selectProgram(medicId, "medic");
                        links = MedicTasksLinks.medicDietsLinks(medicId, patientId, executed, starting_program);
                    }
                    else if(date!=null && date.matches(Regex.DATE_REGEX)) {
                        if(patientId != null)
                            query = TaskDietDB.selectDate(Integer.parseInt(patientId), String.valueOf(medicId), date, executed,"medic");
                        else
                            query = TaskDietDB.selectDate(null, String.valueOf(medicId), date, executed,"medic");

                        links = MedicTasksLinks.medicDietsLinks(medicId, patientId, executed, starting_program, date);
                    }
                    else if(startdate != null && enddate != null && startdate.matches(Regex.DATE_REGEX) && enddate.matches(Regex.DATE_REGEX)) {
                        if(patientId!=null)
                            query = TaskDietDB.selectDateInterval(Integer.parseInt(patientId), String.valueOf(medicId), startdate, enddate, executed,"medic");
                        else
                            query = TaskDietDB.selectDateInterval(null, String.valueOf(medicId), startdate, enddate, executed,"medic");

                        links = MedicTasksLinks.medicDietsLinks(medicId, patientId, executed, starting_program, startdate, enddate);
                    }
                    else {
                        if(patientId != null)
                            query = TaskDietDB.select(Integer.parseInt(patientId), String.valueOf(medicId), executed,"medic");
                        else
                            query = TaskDietDB.select(null, String.valueOf(medicId), executed,"medic");

                        links = MedicTasksLinks.medicDietsLinks(medicId, patientId, executed, starting_program);
                    }

                    if(query != null && query.size() > 0) {
                        response.status(200);
                        response.type("application/json");

                        return "{ " + JsonBuilder.jsonList("diets", query, links, "task", "diets", "medic") + " }";
                    }

                    response.status(404);
                    return "";
                });

                medicTask("diets");
            });
        });
    }

    private void medicTask(String taskCategory) {
        get("/:task_id", (request, response) -> {
            //get singolo task
            int medicId = Integer.parseInt(request.params("medic_id"));
            int taskId = Integer.parseInt(request.params("task_id"));
            Map<String, Object> query = new LinkedHashMap<>();

            switch (taskCategory) {
                case "general":
                    query = SharedTaskFunctionDB.selectSingleTaskGeneral(medicId, taskId, "medic");

                    break;
                case "activities":
                    query = SharedTaskFunctionDB.selectSingleTaskActivities(medicId, taskId, "medic");

                    break;
                case "diets":
                    query = SharedTaskFunctionDB.selectSingleTaskDiets(medicId, taskId, "medic");

                    break;
            }

            if(query != null) {
                response.status(200);
                response.type("application/json");
                return JsonBuilder.jsonObject(query, MedicTasksLinks.medicSingleTaskLinks(medicId, taskId, (int) query.get("patient_id"), taskCategory));
            }

            response.status(404);
            return "";
        });
        put("/:task_id", (request, response) -> {
            if(request.contentType().contains("json")) {
                Map map = gson.fromJson(request.body(), Map.class);

                if(map != null && Checker.putMedicTaskMapValidation(map)) {
                    map.put("medic_id", Integer.parseInt(request.params("medic_id")));
                    map.put("id", Integer.parseInt(request.params("task_id")));
                    boolean result = false;

                    switch(taskCategory) {
                        case "general":
                            result = TaskGeneralDB.updateMedic(map);

                            break;
                        case "activities":
                            result = TaskActivityDB.updateMedic(map);

                            break;
                        case "diets":
                            result = TaskDietDB.updateMedic(map);

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
        delete("/:task_id", (request, response) -> {
            boolean result = false;

            switch (taskCategory) {
                case "general":
                    result = TaskGeneralDB.delete(Integer.parseInt(request.params("task_id")));

                    break;
                case "activities":
                    result = TaskActivityDB.delete(Integer.parseInt(request.params("task_id")));

                    break;
                case "diets":
                    result = TaskDietDB.delete(Integer.parseInt(request.params("task_id")));

                    break;
            }

            if(result) {
                response.status(200);
                return "OK";
            }

            response.status(404);
            return "ERRORE";
        });
        post("", (request, response) -> {
            if(request.contentType().contains("json")) {
                Map<String, Object> map = gson.fromJson(request.body(), Map.class);

                if(map != null && Checker.taskMapValidation(map)) {
                    map.put("medic_id", Integer.parseInt(request.params("medic_id")));

                    if(MedicHasPatientDB.checkMedicPatientAssociation(((Double) map.get("patient_id")).intValue(), (int) map.get("medic_id"))) {
                        boolean result = false;

                        switch (taskCategory) {
                            case "general":
                                result = TaskGeneralDB.insert(map);

                                break;
                            case "activities":
                                result = TaskActivityDB.insert(map);

                                break;
                            case "diets":
                                result = TaskDietDB.insert(map);
                        }

                        if (result) {
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
}
