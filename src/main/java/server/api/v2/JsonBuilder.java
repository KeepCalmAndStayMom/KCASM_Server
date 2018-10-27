package server.api.v2;

import java.util.Map;

public class JsonBuilder {

    public static String jsonBuilder(Map<String, Object> query, String links) {

        if(query != null) {
            StringBuilder json = new StringBuilder();
            json.append("{");
            query.forEach((k, v) -> {
                json.append(" \"" + k + "\"" + ": ");
                if(v instanceof String)
                    json.append("\"" + v + "\",");
                else
                    json.append(v + ",");
            });
            //json.deleteCharAt(json.length()-1);
            json.append(links);
            json.append(" }");

            return json.toString();
        }

        return null;
    }
}
