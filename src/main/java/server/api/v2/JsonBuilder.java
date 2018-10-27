package server.api.v2;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class JsonBuilder {

    public static StringBuilder jsonBuilder(Map<String, Object> query, String links) {

        if(query != null) {
            StringBuilder json = new StringBuilder();
            json.append("{");
            mapToJson(json, query);
            //json.deleteCharAt(json.length()-1);

            if(links != null)
                json.append(", " + links);
            json.append(" }");

            return json;
        }

        return null;
    }

    public static StringBuilder jsonList(LinkedList<LinkedHashMap<String, Object>> query, String type) {
        if(query != null) {
            StringBuilder json = new StringBuilder();
            json.append("[ ");
            for(Map m : query) {
                if(type.equals("medic"))
                    json.append(jsonBuilder(m, LinksBuilder.medicListLink((String) m.get("id"))).toString());
                else if(type.equals("patient"))
                    json.append(jsonBuilder(m, LinksBuilder.patientListLink((String) m.get("id"))).toString());
                json.append(", ");
            }
            json.replace(json.length()-2, json.length()-1, " ]");

            System.out.println(json.toString());
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
