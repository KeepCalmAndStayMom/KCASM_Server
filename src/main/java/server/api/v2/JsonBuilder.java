package server.api.v2;

import server.api.v2.links.LinksBuilder;
import server.api.v2.links.MedicTasksLinks;
import server.api.v2.links.PatientTasksLinks;

import java.util.List;
import java.util.Map;

public class JsonBuilder {

    public static StringBuilder jsonMap(Map<String, Object> inputMap, String links) {
        StringBuilder json = new StringBuilder();

        if(inputMap != null) {
            json.append("{ ");
            mapToJson(json, inputMap);
            //json.deleteCharAt(json.length()-1);

            if(links != null)
                json.append(", " + links);

            json.append(" }");

            return json;
        }
        else
            json.append("null");

        if(links != null) {
            json.append(links);
        }

        return json;
    }

    public static StringBuilder jsonList(String listName, List<Map<String, Object>> inputList, String links, String innerObjectType, String ... args) {

        if(inputList != null) {
            StringBuilder json = new StringBuilder();

            if(listName != null)
                json.append("\"" + listName + "\": ");

            json.append("[ ");
            for(Map m : inputList) {
                innerListJsonObject(innerObjectType, args, json, m);
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
            return json.append("[ ").append(jsonMap(null, links).toString()).append(" ]");
        }

        return null;
    }

    private static void innerListJsonObject(String typeObject, String[] args, StringBuilder json, Map inputMap) {
        if(typeObject.equals("medic"))
            json.append(jsonMap(inputMap, LinksBuilder.medicListLink((int) inputMap.get("id"))).toString());
        else if(typeObject.equals("patient"))
            json.append(jsonMap(inputMap, LinksBuilder.patientListLink((int) inputMap.get("id"))).toString());
        else if(typeObject.equals("weight"))
            json.append(jsonMap(inputMap, LinksBuilder.singleWeightLink((int) inputMap.get("patient_id"), (String) inputMap.get("date"))).toString());
        else if(typeObject.equals("message")) {
            if (args[0].equals("patient"))
                json.append(jsonMap(inputMap, LinksBuilder.innerListMessage((int) inputMap.get("patient_id"), args[0], args[1], (int) inputMap.get("medic_id"), (String) inputMap.get("timedate"))).toString());
            else
                json.append(jsonMap(inputMap, LinksBuilder.innerListMessage((int) inputMap.get("medic_id"), args[0], args[1], (int) inputMap.get("patient_id"), (String) inputMap.get("timedate"))).toString());
        }
        else if(typeObject.equals("task")) {
            if (args[1].equals("patient"))
                json.append(jsonMap(inputMap, PatientTasksLinks.patientInnerListTaskLink((int) inputMap.get("patient_id"), (int) inputMap.get("id"), args[0])).toString());
            else
                json.append(jsonMap(inputMap, MedicTasksLinks.medicInnerListTaskLink((int) inputMap.get("medic_id"), (int) inputMap.get("id"), args[0])).toString());
        }
        else
            json.append(jsonMap(inputMap, null).toString());
    }

    private static StringBuilder mapToJson(StringBuilder json, Map inputMap) {
        inputMap.forEach((k, v) -> {
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
