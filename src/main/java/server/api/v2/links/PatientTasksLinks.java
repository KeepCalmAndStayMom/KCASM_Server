package server.api.v2.links;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PatientTasksLinks {
    private static final String BASE_URL = "http://localhost:4567/api/v2/";

    /*public static String tasksMenu(int id, String userType) {
        StringBuilder json = new StringBuilder();

        json.append(Link.jsonLink(BASE_URL + userType + "s/" + id + "/tasks/general", userType + "/tasks/general", "GET")).append(", ");
        json.append(Link.jsonLink(BASE_URL + userType + "s/" + id + "/tasks/activities", userType + "/tasks/activities", "GET")).append(", ");
        json.append(Link.jsonLink(BASE_URL + userType + "s/" + id + "/tasks/diets", userType + "/tasks/diets", "GET"));

        return json.toString();
    }*/

    public static List<Map<String, String>> tasksMenu(int id, String userType) {
        List<Map<String, String>> links = new ArrayList<>();

        links.add(Link.linkMap(BASE_URL + userType + "s/" + id + "/tasks/general", userType + "/tasks/general", "GET"));
        links.add(Link.linkMap(BASE_URL + userType + "s/" + id + "/tasks/activities", userType + "/tasks/activities", "GET"));
        links.add(Link.linkMap(BASE_URL + userType + "s/" + id + "/tasks/diets", userType + "/tasks/diets", "GET"));

        return links;
    }

    public static String patientGeneralLinks(int patientId, String medic_id, String executed, String startingProgram, String ... date) {
        StringBuilder json = new StringBuilder();
        StringBuilder params = new StringBuilder("?");


        if(medic_id != null)
            params.append("medic_id=" + medic_id + "&");
        if(executed != null)
            params.append("executed=" + executed + "&");
        if(startingProgram != null)
            params.append("starting_program=" + startingProgram + "&");

        if(date.length==1)
            params.append("date=" + date[0] + "&");
        else if(date.length==2)
            params.append("startdate=" + date[0] + "enddate=" + date[1] + "&");

        params.deleteCharAt(params.length()-1);


        json.append("\"links\": [ ");
        json.append(Link.jsonLink(BASE_URL + "patients/" + patientId + "/tasks/activities" + params.toString(), "patient/tasks/activities", "GET")).append(", ");
        json.append(Link.jsonLink(BASE_URL + "patients/" + patientId + "/tasks/diets" + params.toString(), "patient/tasks/diets", "GET")).append(" ]");

        return json.toString();
    }

    public static String patientActivitiesLinks(int patientId, String medic_id, String executed, String startingProgram, String ... date) {
        StringBuilder json = new StringBuilder();
        StringBuilder params = new StringBuilder("?");

        if(medic_id != null)
            params.append("medic_id=" + medic_id + "&");
        if(executed != null)
            params.append("executed=" + executed + "&");
        if(startingProgram != null)
            params.append("starting_program=" + startingProgram + "&");

        if(date.length==1)
            params.append("date=" + date[0] + "&");
        else if(date.length==2)
            params.append("startdate=" + date[0] + "enddate=" + date[1] + "&");

        params.deleteCharAt(params.length()-1);


        json.append("\"links\": [ ");
        json.append(Link.jsonLink(BASE_URL + "patients/" + patientId + "/tasks/general" + params.toString(), "patient/tasks/general", "GET")).append(", ");
        json.append(Link.jsonLink(BASE_URL + "patients/" + patientId + "/tasks/diets" + params.toString(), "patient/tasks/diets", "GET")).append(" ]");

        return json.toString();
    }

    public static String patientDietsLinks(int patientId, String medic_id, String executed, String startingProgram, String ... date) {
        StringBuilder json = new StringBuilder();
        StringBuilder params = new StringBuilder("?");

        if(medic_id != null)
            params.append("medic_id=" + medic_id + "&");
        if(executed != null)
            params.append("executed=" + executed + "&");
        if(startingProgram != null)
            params.append("starting_program=" + startingProgram + "&");

        if(date.length==1)
            params.append("date=" + date[0] + "&");
        else if(date.length==2)
            params.append("startdate=" + date[0] + "enddate=" + date[1] + "&");

        params.deleteCharAt(params.length()-1);


        json.append("\"links\": [ ");
        json.append(Link.jsonLink(BASE_URL + "patients/" + patientId + "/tasks/activities" + params.toString(), "patient/tasks/activities", "GET")).append(", ");
        json.append(Link.jsonLink(BASE_URL + "patients/" + patientId + "/tasks/general" + params.toString(), "patient/tasks/general", "GET")).append(" ]");

        return json.toString();
    }

    public static String patientInnerListTaskLink(int patientId, int taskId, String taskCategory) {
        StringBuilder json = new StringBuilder();

        json.append("\"link\": ");
        json.append(Link.jsonLink(BASE_URL + "patients/" + patientId + "/tasks/" + taskCategory + "/" + taskId, "self", "GET"));

        return json.toString();
    }

    /*public static String patientSingleTaskLinks(int patientId, int taskId, int medicId, String taskCategory) {
        StringBuilder json = new StringBuilder();
        String href = BASE_URL + "patients/" + patientId + "/tasks/" + taskCategory + "/" + taskId;

        json.append("\"links\": [ ");
        json.append(Link.jsonLink(BASE_URL + "patients/" + patientId + "/tasks/" + taskCategory + "/" + taskId, "self", "PUT")).append(", ");
        json.append(Link.jsonLink(BASE_URL + "patients/" + patientId + "/tasks/" + taskCategory, "patient/tasks/" + taskCategory, "GET")).append(", ");
        json.append(Link.jsonLink(BASE_URL + "patients/" + patientId, "patient", "GET")).append(", ");
        json.append(Link.jsonLink(BASE_URL + "medics/" + medicId, "medic", "GET")).append(" ]");

        return json.toString();
    }*/

    public static List<Map<String, String>> patientSingleTaskLinks(int patientId, int taskId, int medicId, String taskCategory) {
        List<Map<String, String>> links = new ArrayList<>();
        String href = BASE_URL + "patients/" + patientId + "/tasks/" + taskCategory + "/" + taskId;

        links.add(Link.linkMap(BASE_URL + "patients/" + patientId + "/tasks/" + taskCategory + "/" + taskId, "self", "PUT"));
        links.add(Link.linkMap(BASE_URL + "patients/" + patientId + "/tasks/" + taskCategory, "patient/tasks/" + taskCategory, "GET"));
        links.add(Link.linkMap(BASE_URL + "patients/" + patientId, "patient", "GET"));
        links.add(Link.linkMap(BASE_URL + "medics/" + medicId, "medic", "GET"));

        return links;
    }
}
