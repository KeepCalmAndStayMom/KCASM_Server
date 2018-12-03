package server.api.v2.links;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MedicTasksLinks {
    private static final String BASE_URL = "http://localhost:4567/api/v2/";

    public static List<Map<String, String>> tasksMenu(int id, String userType) {
        List<Map<String, String>> links = new ArrayList<>();

        links.add(Link.linkMap(BASE_URL + userType + "s/" + id + "/tasks/general", userType + "/tasks/general", "GET"));
        links.add(Link.linkMap(BASE_URL + userType + "s/" + id + "/tasks/activities", userType + "/tasks/activities", "GET"));
        links.add(Link.linkMap(BASE_URL + userType + "s/" + id + "/tasks/diets", userType + "/tasks/diets", "GET"));

        return links;
    }

    public static String medicGeneralLinks(int medicId, String patientId, String executed, String startingProgram, String ... date) {
        StringBuilder json = new StringBuilder();
        StringBuilder params = new StringBuilder("?");


        if(patientId != null)
            params.append("patient_id=" + patientId + "&");
        if(executed != null)
            params.append("executed=" + executed + "&");
        if(startingProgram != null)
            params.append("starting_program=" + startingProgram + "&");

        if(date.length==1)
            params.append("date=" + date[0] + "&");
        else if(date.length==2)
            params.append("startdate=" + date[0] + "&enddate=" + date[1] + "&");

        params.deleteCharAt(params.length()-1);


        json.append("\"links\": [ ");
        json.append(Link.jsonLink(BASE_URL + "medics/" + medicId + "/tasks/activities" + params.toString(), "medic/tasks/activities", "GET")).append(", ");
        json.append(Link.jsonLink(BASE_URL + "medics/" + medicId + "/tasks/general", "medic/tasks/general", "POST")).append(", ");
        json.append(Link.jsonLink(BASE_URL + "medics/" + medicId + "/tasks/diets" + params.toString(), "medic/tasks/diets", "GET")).append(" ]");

        return json.toString();
    }

    public static String medicActivitiesLinks(int medicId, String patientId, String executed, String startingProgram, String ... date) {
        StringBuilder json = new StringBuilder();
        StringBuilder params = new StringBuilder("?");

        if(patientId != null)
            params.append("patient_id=" + patientId + "&");
        if(executed != null)
            params.append("executed=" + executed + "&");
        if(startingProgram != null)
            params.append("starting_program=" + startingProgram + "&");

        if(date.length==1)
            params.append("date=" + date[0] + "&");
        else if(date.length==2)
            params.append("startdate=" + date[0] + "&enddate=" + date[1] + "&");

        params.deleteCharAt(params.length()-1);


        json.append("\"links\": [ ");
        json.append(Link.jsonLink(BASE_URL + "medics/" + medicId + "/tasks/general" + params.toString(), "medic/tasks/general", "GET")).append(", ");
        json.append(Link.jsonLink(BASE_URL + "medics/" + medicId + "/tasks/activities", "medic/tasks/general", "POST")).append(", ");
        json.append(Link.jsonLink(BASE_URL + "medics/" + medicId + "/tasks/diets" + params.toString(), "medic/tasks/diets", "GET")).append(" ]");

        return json.toString();
    }

    public static String medicDietsLinks(int medicId, String patientId, String executed, String startingProgram, String ... date) {
        StringBuilder json = new StringBuilder();
        StringBuilder params = new StringBuilder("?");

        if(patientId != null)
            params.append("patient_id=" + patientId + "&");
        if(executed != null)
            params.append("executed=" + executed + "&");
        if(startingProgram != null)
            params.append("starting_program=" + startingProgram + "&");

        if(date.length==1)
            params.append("date=" + date[0] + "&");
        else if(date.length==2)
            params.append("startdate=" + date[0] + "&enddate=" + date[1] + "&");

        params.deleteCharAt(params.length()-1);


        json.append("\"links\": [ ");
        json.append(Link.jsonLink(BASE_URL + "medics/" + medicId + "/tasks/activities" + params.toString(), "medic/tasks/activities", "GET")).append(", ");
        json.append(Link.jsonLink(BASE_URL + "medics/" + medicId + "/tasks/diets", "medic/tasks/general", "POST")).append(", ");
        json.append(Link.jsonLink(BASE_URL + "medics/" + medicId + "/tasks/general" + params.toString(), "medic/tasks/general", "GET")).append(" ]");

        return json.toString();
    }

    public static String medicInnerListTaskLink(int medicId, int taskId, String taskCategory) {
        StringBuilder json = new StringBuilder();

        json.append("\"link\": ");
        json.append(Link.jsonLink(BASE_URL + "medics/" + medicId + "/tasks/" + taskCategory + "/" + taskId, "self", "GET"));

        return json.toString();
    }

    public static List<Map<String, String>> medicSingleTaskLinks(int medicId, int taskId, int patientId, String taskCategory) {
        List<Map<String, String>> links = new ArrayList<>();
        String href = BASE_URL + "medics/" + medicId + "/tasks/" + taskCategory + "/" + taskId;

        links.add(Link.linkMap(BASE_URL + "medics/" + medicId + "/tasks/" + taskCategory + "/" + taskId, "self", "PUT"));
        links.add(Link.linkMap(BASE_URL + "medics/" + medicId + "/tasks/" + taskCategory + "/" + taskId, "self", "DELETE"));
        links.add(Link.linkMap(BASE_URL + "medics/" + medicId + "/tasks/" + taskCategory, "medic/tasks/" + taskCategory, "GET"));
        links.add(Link.linkMap(BASE_URL + "medics/" + medicId, "medic", "GET"));
        links.add(Link.linkMap(BASE_URL + "patients/" + patientId, "patient", "GET"));

        return links;
    }
}
