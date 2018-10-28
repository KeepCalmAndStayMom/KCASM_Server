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

    public static String medicListLink(int medicId) {
        StringBuilder json = new StringBuilder();

        json.append("\"link\": ");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/medics/" + medicId, "medic", "GET"));

        return json.toString();
    }

    public static String patientListLink(int patientId) {
        StringBuilder json = new StringBuilder();

        json.append("\"link\": ");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId, "patient", "GET"));

        return json.toString();
    }

    public static String fitbitLinks(int patientId, String type) {
        StringBuilder json = new StringBuilder();

        json.append("\"links\": [ ");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/measures/" + type + "/hue", "patient/measures/" + type + "/hue", "GET")).append(", ");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/measures/" + type + "/sensor", "patient/measures/" + type + "/sensor", "GET")).append(" ]");

        return json.toString();
    }

    public static String sensorLinks(int patientId, String type) {
        StringBuilder json = new StringBuilder();

        json.append("\"links\": [ ");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/measures/" + type + "/hue", "patient/measures/" + type + "/hue", "GET")).append(", ");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/measures/" + type + "/fitbit", "patient/measures/" + type + "/fitbit", "GET")).append(" ]");

        return json.toString();
    }

    public static String hueLinks(int patientId, String type) {
        StringBuilder json = new StringBuilder();

        json.append("\"links\": [ ");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/measures/" + type + "/fitbit", "patient/measures/" + type + "/fitbit", "GET")).append(", ");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/measures/" + type + "/sensor", "patient/measures/" + type + "/sensor", "GET")).append(" ]");

        return json.toString();
    }

    public static String measuresLinks(int patientId) {
        StringBuilder json = new StringBuilder();

        json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/measures/samples", "patient/measures/samples", "GET")).append(", ");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/measures/total", "patient/measures/total", "GET"));

        return json.toString();
    }

    public static String measuresSubLinks(int patientId, String type) {
        StringBuilder json = new StringBuilder();

        json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/measures/" + type + "/fitbit", "patient/measures/" + type + "/fitbit", "GET")).append(", ");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/measures/" + type + "/hue", "patient/measures/" + type + "/hue", "GET")).append(", ");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/measures/" + type + "/sensor", "patient/measures/" + type + "/sensor", "GET"));

        return json.toString();
    }

    public static String loginData(int id, String type) {
        StringBuilder json = new StringBuilder();

        json.append("\"links\": [");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/" + type + "/" + id, type, "GET")).append(", ");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/" + type + "/" + id + "/login_data", "self", "PUT")).append(" ]");

        return json.toString();
    }

    public static String initialData(int patientId) {
        StringBuilder json = new StringBuilder();

        json.append("\"links\": [");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId, "patient", "GET")).append(", ");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/initial_data", "self", "PUT")).append(" ]");

        return json.toString();
    }
}
