package server.api.v2;

public class LinksBuilder {

    public static String patientLinks(int patientId) {
        StringBuilder json = new StringBuilder();

        json.append("\"links\": [ ");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/tasks", "patient/tasks", "GET")).append(", ");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/weight", "patient/weight", "GET")).append(", ");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/initial_data", "patient/initial_data", "GET")).append(", ");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/medics", "patient/medics", "GET")).append(", ");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/messages", "patient/messages", "GET")).append(", ");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/measures", "patient/measures", "GET")).append(", ");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/login_data", "patient/login_data", "GET")).append(", ");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId, "patient", "PUT")).append(" ]");

        return json.toString();
    }
}
