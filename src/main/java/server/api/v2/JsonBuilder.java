package server.api.v2;

import java.util.List;
import java.util.Map;

public class JsonBuilder {

    public static StringBuilder jsonObject(Map<String, Object> query, String links) {
        StringBuilder json = null;

        if(query != null) {
            json = new StringBuilder();
            json.append("{ ");
            mapToJson(json, query);
            //json.deleteCharAt(json.length()-1);

            if(links != null)
                json.append(", " + links);

            json.append(" }");

            return json;
        }

        if(links != null) {
            json = new StringBuilder();
            json.append(links);
        }

        return json;
    }

    public static StringBuilder jsonList(String listName, List<Map<String, Object>> query, String links, String type, String[] args) {

        if(query != null) {
            StringBuilder json = new StringBuilder();

            if(listName != null)
                json.append("\"" + listName + "\": ");

            json.append("[ ");
            for(Map m : query) {
                innerListJsonObject(type, args, json, m);
                json.append(", ");
            }
            if(json.charAt(json.length()-2) == ',')
                json.replace(json.length()-2, json.length(), " ]");
            else
                json.append(" ]");

            if(links != null)
                json.append(", " + links);

            return json;
        }

        if(links != null) {
            StringBuilder json = new StringBuilder();
            return json.append("[ ").append(jsonObject(null, links).toString()).append(" ]");
        }

        return null;
    }

    private static void innerListJsonObject(String type, String[] args, StringBuilder json, Map m) {
        if(type.equals("medic"))
            json.append(jsonObject(m, LinksBuilder.medicListLink((int) m.get("id"))).toString());
        else if(type.equals("patient"))
            json.append(jsonObject(m, LinksBuilder.patientListLink((int) m.get("id"))).toString());
        else if(type.equals("weight"))
            json.append(jsonObject(m, LinksBuilder.singleWeightLink((int) m.get("patient_id"), (String) m.get("date"))).toString());
        else if(type.equals("message")) {
            if (args[0].equals("patient"))
                json.append(jsonObject(m, LinksBuilder.singleMessageLink((int) m.get("patient_id"), args[0], args[1], (int) m.get("medic_id"), (String) m.get("timedate"))).toString());
            else
                json.append(jsonObject(m, LinksBuilder.singleMessageLink((int) m.get("medic_id"), args[0], args[1], (int) m.get("patient_id"), (String) m.get("timedate"))).toString());
        }
        else
            json.append(jsonObject(m, null).toString());
    }

    private static StringBuilder mapToJson(StringBuilder json, Map m) {
        m.forEach((k, v) -> {
            json.append(" \"" + k + "\"" + ": ");
            if (v instanceof String)
                json.append("\"" + v + "\",");
            else
                json.append(v + ",");
        });

        json.deleteCharAt(json.length()-1);

        return json;
    }
}
