package server.api.v2.links;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LinksBuilder {

    /*@Deprecated
    public static String patientLinks(int patientId) {
        StringBuilder json = new StringBuilder();

        json.append("\"links\": [ ");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/tasks", "patient/tasks", "GET")).append(", ");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/weights", "patient/weights", "GET")).append(", ");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/thresholds", "patient/thresholds", "GET")).append(", ");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/initial_data", "patient/initial_data", "GET")).append(", ");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/medics", "patient/medics", "GET")).append(", ");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/messages", "patient/messages", "GET")).append(", ");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/measures", "patient/measures", "GET")).append(", ");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/login_data", "patient/login_data", "GET")).append(", ");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId, "self", "PUT")).append(" ]");

        return json.toString();
    }*/

    public static List<Map<String, String>> patientLinks(int patientId) {
        List<Map<String, String>> links = new ArrayList<>();

        links.add(Link.linkMap("http://localhost:4567/api/v2/patients/" + patientId + "/tasks", "patient/tasks", "GET"));
        links.add(Link.linkMap("http://localhost:4567/api/v2/patients/" + patientId + "/weights", "patient/weights", "GET"));
        links.add(Link.linkMap("http://localhost:4567/api/v2/patients/" + patientId + "/thresholds", "patient/thresholds", "GET"));
        links.add(Link.linkMap("http://localhost:4567/api/v2/patients/" + patientId + "/initial_data", "patient/initial_data", "GET"));
        links.add(Link.linkMap("http://localhost:4567/api/v2/patients/" + patientId + "/medics", "patient/medics", "GET"));
        links.add(Link.linkMap("http://localhost:4567/api/v2/patients/" + patientId + "/messages", "patient/messages", "GET"));
        links.add(Link.linkMap("http://localhost:4567/api/v2/patients/" + patientId + "/measures", "patient/measures", "GET"));
        links.add(Link.linkMap("http://localhost:4567/api/v2/patients/" + patientId + "/login_data", "patient/login_data", "GET"));
        links.add(Link.linkMap("http://localhost:4567/api/v2/patients/" + patientId, "self", "PUT"));

        return links;
    }

    /*public static String medicLinks(int medicId) {
        StringBuilder json = new StringBuilder();

        json.append("\"links\": [ ");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/medics/" + medicId + "/patients", "medic/patients", "GET")).append(", ");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/medics/" + medicId + "/tasks", "medic/tasks", "GET")).append(", ");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/medics/" + medicId + "/messages", "medic/messages", "GET")).append(", ");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/medics/" + medicId + "/login_data", "medic/login_data", "GET")).append(", ");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/medics/" + medicId, "self", "PUT")).append(" ]");

        return json.toString();
    }*/

    public static List<Map<String, String>> medicLinks(int medicId) {
        List<Map<String, String>> links = new ArrayList<>();

        links.add(Link.linkMap("http://localhost:4567/api/v2/medics/" + medicId + "/patients", "medic/patients", "GET"));
        links.add(Link.linkMap("http://localhost:4567/api/v2/medics/" + medicId + "/tasks", "medic/tasks", "GET"));
        links.add(Link.linkMap("http://localhost:4567/api/v2/medics/" + medicId + "/messages", "medic/messages", "GET"));
        links.add(Link.linkMap("http://localhost:4567/api/v2/medics/" + medicId + "/login_data", "medic/login_data", "GET"));
        links.add(Link.linkMap("http://localhost:4567/api/v2/medics/" + medicId, "self", "PUT"));

        return links;
    }


    public static String medicListLink(int medicId) {
        StringBuilder json = new StringBuilder();

        json.append("\"link\": ");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/medics/" + medicId, "self", "GET"));

        return json.toString();
    }

    public static String patientListLink(int patientId) {
        StringBuilder json = new StringBuilder();

        json.append("\"link\": ");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId, "self", "GET"));

        return json.toString();
    }

    public static String loginData(int id, String type) {
        StringBuilder json = new StringBuilder();

        json.append("\"links\": [");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/" + type + "s/" + id, type, "GET")).append(", ");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/" + type + "s/" + id + "/login_data", "self", "PUT")).append(" ]");

        return json.toString();
    }

    public static String initialData(int patientId) {
        StringBuilder json = new StringBuilder();

        json.append("\"links\": [");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId, "patient", "GET")).append(", ");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/initial_data", "self", "PUT")).append(" ]");

        return json.toString();
    }

    public static String singleWeightLink(int patientId, String date) {
        StringBuilder json = new StringBuilder();

        json.append("\"link\": ");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/weights?date=" + date, "self", "GET"));

        return json.toString();
    }

    public static String weightsLinks(int patientId) {
        StringBuilder json = new StringBuilder();

        json.append("\"links\": [");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId, "patient", "GET")).append(", ");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/weights", "self", "PUT")).append(", ");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/patients/" + patientId + "/weights", "self", "POST")).append(" ]");

        return json.toString();
    }

    public static String messagesCategoryLinks(int id, String type) {
        StringBuilder json = new StringBuilder();

        json.append(Link.jsonLink("http://localhost:4567/api/v2/" + type + "s/" + id + "/messages/received", type + "/messages/received", "GET")).append(", ");
        json.append(Link.jsonLink("http://localhost:4567/api/v2/" + type + "s/" + id + "/messages/sent", type + "/messages/sent", "GET"));

        return json.toString();
    }

    public static String messagesLinks(int id, String type, String category, String id2, String startdate, String enddate) {
        StringBuilder json = new StringBuilder();
        StringBuilder href = new StringBuilder();


        href.append("http://localhost:4567/api/v2/" + type + "s/" + id + "/messages");

        if(category.equals("sent"))
            href.append("/received");
        else
            href.append("/sent");

        if(id2 != null) {
            if(type.equals("patient"))
                href.append("?medic_id=" + id2);
            else
                href.append("?patient_id=" + id2);
            if(startdate != null && enddate == null)
                href.append("&date=" + startdate);
            else if(startdate != null && enddate != null)
                href.append("&startdate=" + startdate + "&enddate=" + enddate);
        }
        else {
            if(startdate != null && enddate == null)
                href.append("?date=" + startdate);
            else if(startdate != null && enddate != null)
                href.append("?startdate=" + startdate + "&enddate=" + enddate);
        }

        json.append("\"links:\": [");
        if(category.equals("received"))
            json.append(Link.jsonLink(href.toString(), type + "/messages/sent", "GET")).append(", ");
        else
            json.append(Link.jsonLink(href.toString(), type + "/messages/received", "GET")).append(", ");


        json.append(Link.jsonLink("http://localhost:4567/api/v2/" + type + "s/" + id + "/messages", type + "/messages", "POST")).append(" ]");

        return json.toString();
    }

    public static String innerListMessage(int id, String type, String category, int id2 , String timedate) {
        StringBuilder json = new StringBuilder();

        json.append("\"link\": ");
        if(type.equals("patient"))
            json.append(Link.jsonLink("http://localhost:4567/api/v2/" + type + "s/" + id + "/messages/" + category + "?medic_id=" + id2 + "&timedate=" + timedate.replace(" ", "T"), "self", "GET"));
        else
            json.append(Link.jsonLink("http://localhost:4567/api/v2/" + type + "s/" + id + "/messages/" + category + "?patient_id=" + id2 + "&timedate=" + timedate.replace(" ", "T"), "self", "GET"));

        return json.toString();
    }

    public static String singleMessage(int id, String type, String category, int id2 , String timedate) {
        StringBuilder json = new StringBuilder();

        json.append("\"link\": ");
        if(type.equals("patient"))
            json.append(Link.jsonLink("http://localhost:4567/api/v2/" + type + "s/" + id + "/messages/" + category + "?medic_id=" + id2 + "&timedate=" + timedate.replace(" ", "T"), "self", "PUT"));
        else
            json.append(Link.jsonLink("http://localhost:4567/api/v2/" + type + "s/" + id + "/messages/" + category + "?patient_id=" + id2 + "&timedate=" + timedate.replace(" ", "T"), "self", "PUT"));

        return json.toString();
    }
}
