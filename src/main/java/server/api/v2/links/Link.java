package server.api.v2.links;

import java.util.HashMap;
import java.util.Map;

public class Link {

    public static String jsonLink(String href, String rel, String type) {
        StringBuilder json = new StringBuilder();

        json.append("{ ");
        json.append("\"href\": ").append("\"" + href + "\", ");
        json.append("\"rel\": ").append("\"" + rel + "\", ");
        json.append("\"type\": ").append("\"" + type + "\"");
        json.append(" }");

        return json.toString();
    }

    public static HashMap<String, String> linkMap(String href, String rel, String type) {
        HashMap<String, String> link = new HashMap<>();

        link.put("href", href);
        link.put("rel", rel);
        link.put("type", type);

        return link;
    }
}
