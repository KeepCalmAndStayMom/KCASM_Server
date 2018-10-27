package server.api.v2;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class JsonBuilder {

    public static StringBuilder jsonObject(Map<String, Object> query, String links) {
        StringBuilder json = null;

        if(query != null) {
            json = new StringBuilder();
            json.append("{");
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

    public static StringBuilder jsonList(String listName, LinkedList<LinkedHashMap<String, Object>> query, String links, String type) {
        if(query != null) {
            StringBuilder json = new StringBuilder();

            if(listName != null)
                json.append("\"" + listName + "\": ");

            json.append("[ ");
            for(Map m : query) {
                if(type.equals("medic"))
                    json.append(jsonObject(m, LinksBuilder.medicListLink((String) m.get("id"))).toString());
                else if(type.equals("patient"))
                    json.append(jsonObject(m, LinksBuilder.patientListLink((String) m.get("id"))).toString());
                else
                    json.append(jsonObject(m, null).toString());
                json.append(", ");
            }
            if(json.charAt(json.length()-2) == ',')
                json.replace(json.length()-2, json.length()-1, " ]");
            else
                json.append(" ]");

            if(links != null)
                json.append(", " + links);

            return json;
        }

        return null;
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
