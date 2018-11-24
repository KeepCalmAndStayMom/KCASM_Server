package server.api.v2.links;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MeasuresLinks {

    public static String fitbitLinks(int patientId, String type, String... date) {
        StringBuilder json = new StringBuilder();

        json.append("\"links\": [ ");
        if (date.length == 0) {
            json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/measures/" + type + "/hue", "patient/measures/" + type + "/hue", "GET")).append(", ");
            json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/measures/" + type + "/sensor", "patient/measures/" + type + "/sensor", "GET")).append(" ]");

        } else if (date.length == 1) {
            json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/measures/" + type + "/hue?date=" + date[0], "patient/measures/" + type + "/hue", "GET")).append(", ");
            json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/measures/" + type + "/sensor?date=" + date[0], "patient/measures/" + type + "/sensor", "GET")).append(" ]");

        } else {
            json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/measures/" + type + "/hue?startdate=" + date[0] + "&enddate=" + date[1], "patient/measures/" + type + "/hue", "GET")).append(", ");
            json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/measures/" + type + "/sensor?startdate=" + date[0] + "&enddate=" + date[1], "patient/measures/" + type + "/sensor", "GET")).append(" ]");

        }
        return json.toString();
    }

    public static String sensorLinks(int patientId, String type, String... date) {
        StringBuilder json = new StringBuilder();

        json.append("\"links\": [ ");
        if (date.length == 0) {
            json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/measures/" + type + "/fitbit", "patient/measures/" + type + "/fitbit", "GET")).append(", ");
            json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/measures/" + type + "/hue", "patient/measures/" + type + "/hue", "GET")).append(" ]");

        } else if (date.length == 1) {
            json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/measures/" + type + "/fitbit?date=" + date[0], "patient/measures/" + type + "/fitbit", "GET")).append(", ");
            json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/measures/" + type + "/hue?date=" + date[0], "patient/measures/" + type + "/hue", "GET")).append(" ]");

        } else {
            json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/measures/" + type + "/fitbit?startdate=" + date[0] + "&enddate=" + date[1], "patient/measures/" + type + "/fitbit", "GET")).append(", ");
            json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/measures/" + type + "/hue?startdate=" + date[0] + "&enddate=" + date[1], "patient/measures/" + type + "/hue", "GET")).append(" ]");

        }
        return json.toString();
    }

    public static String hueLinks(int patientId, String type, String... date) {
        StringBuilder json = new StringBuilder();

        json.append("\"links\": [ ");
        if (date.length == 0) {
            json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/measures/" + type + "/fitbit", "patient/measures/" + type + "/fitbit", "GET")).append(", ");
            json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/measures/" + type + "/sensor", "patient/measures/" + type + "/sensor", "GET")).append(" ]");

        } else if (date.length == 1) {
            json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/measures/" + type + "/fitbit?date=" + date[0], "patient/measures/" + type + "/fitbit", "GET")).append(", ");
            json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/measures/" + type + "/sensor?date=" + date[0], "patient/measures/" + type + "/sensor", "GET")).append(" ]");

        } else {
            json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/measures/" + type + "/fitbit?startdate=" + date[0] + "&enddate=" + date[1], "patient/measures/" + type + "/fitbit", "GET")).append(", ");
            json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/measures/" + type + "/sensor?startdate=" + date[0] + "&enddate=" + date[1], "patient/measures/" + type + "/sensor", "GET")).append(" ]");

        }
        return json.toString();
    }

    /*public static String measuresLinks(int patientId) {
        StringBuilder json = new StringBuilder();

        json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/measures/samples", "patient/measures/samples", "GET")).append(", ");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/measures/total", "patient/measures/total", "GET"));

        return json.toString();
    }*/

    public static List<Map<String, String>> measuresLinks(int patientId) {
        List<Map<String, String>> links = new ArrayList<>();

        links.add(Link.linkMap("http://localhost:4567/api/v2/patients/" + patientId + "/measures/samples", "patient/measures/samples", "GET"));
        links.add(Link.linkMap("http://localhost:4567/api/v2/patients/" + patientId + "/measures/total", "patient/measures/total", "GET"));

        return links;
    }

    /*public static String measuresSubLinks(int patientId, String type) {
        StringBuilder json = new StringBuilder();

        json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/measures/" + type + "/fitbit", "patient/measures/" + type + "/fitbit", "GET")).append(", ");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/measures/" + type + "/hue", "patient/measures/" + type + "/hue", "GET")).append(", ");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/measures/" + type + "/sensor", "patient/measures/" + type + "/sensor", "GET"));

        return json.toString();
    }*/

    public static List<Map<String, String>> measuresSubLinks(int patientId, String type) {
        List<Map<String, String>> links = new ArrayList<>();

        links.add(Link.linkMap("http://localhost:4567/api/v2/patients/" + patientId + "/measures/" + type + "/fitbit", "patient/measures/" + type + "/fitbit", "GET"));
        links.add(Link.linkMap("http://localhost:4567/api/v2/patients/" + patientId + "/measures/" + type + "/hue", "patient/measures/" + type + "/hue", "GET"));
        links.add(Link.linkMap("http://localhost:4567/api/v2/patients/" + patientId + "/measures/" + type + "/sensor", "patient/measures/" + type + "/sensor", "GET"));

        return links;
    }

}
