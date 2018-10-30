package server.api.v2.links;

public class TaskLinks {
    private static final String BASE_URL = "http://localhost:4567/api/v2/";

    public static String tasksMenu(int id, String userType) {
        StringBuilder json = new StringBuilder();

        json.append(Link.jsonLink(BASE_URL + userType + "s/" + id + "/tasks/general", userType + "/tasks/general", "GET")).append(", ");
        json.append(Link.jsonLink(BASE_URL + userType + "s/" + id + "/tasks/activities", userType + "/tasks/activities", "GET")).append(", ");
        json.append(Link.jsonLink(BASE_URL + userType + "s/" + id + "/tasks/diets", userType + "/tasks/diets", "GET"));

        return json.toString();
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

    public static String patientActivitiesLinks(int patientId, String medic_id, String executed, String ... date) {
        StringBuilder json = new StringBuilder();
        StringBuilder params = new StringBuilder("?");

        if(medic_id != null)
            params.append("medic_id=" + medic_id + "&");
        if(executed != null)
            params.append("executed=" + executed + "&");

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

    public static String patientDietsLinks(int patientId, String medic_id, String executed, String ... date) {
        StringBuilder json = new StringBuilder();
        StringBuilder params = new StringBuilder("?");

        if(medic_id != null)
            params.append("medic_id=" + medic_id + "&");
        if(executed != null)
            params.append("executed=" + executed + "&");

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
        json.append(Link.jsonLink(BASE_URL + "patients/" + patientId + "/tasks/" + taskCategory + "/" + taskId, "patient/tasks/" + taskCategory, "GET"));

        return json.toString();
    }

    public static String patientSingleTask(int patientId, int taskId) {
        StringBuilder json = new StringBuilder();

        return json.toString();
    }
}
