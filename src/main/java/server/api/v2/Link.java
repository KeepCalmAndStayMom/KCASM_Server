package server.api.v2;

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
}
