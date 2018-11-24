package server.api.v2.patient.measures;

import com.google.api.client.util.DateTime;
import server.api.v2.JsonBuilder;
import server.api.v2.Regex;
import server.api.v2.links.MeasuresLinks;
import server.database.v2.FitbitDB;
import server.database.v2.HueDB;
import server.database.v2.MessageMedicPatientDB;
import server.database.v2.SensorDB;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static spark.Spark.get;

public class TotalMeasures {

    public TotalMeasures() {
        getFitbitTotal();
        getHueTotal();
        getSensorTotal();
    }

    private void getSensorTotal() {
        get("/sensor", (request, response) -> {
            int patientId = Integer.parseInt(request.params("patient_id"));
            String date = request.queryParams("date");
            String startdate = request.queryParams("startdate");
            String enddate = request.queryParams("enddate");

            if(date != null && date.matches(Regex.DATE_REGEX)) {
                List<Map<String, Object>> l = new ArrayList<>();
                l.add(SensorDB.selectTotal(patientId, date));

                if(!listIsAllNull(l)) {
                    response.status(200);
                    response.type("application/json");
                    return "{" + JsonBuilder.jsonList("sensor_total", l, MeasuresLinks.sensorLinks(patientId, "total", date), "test").toString() + "}";
                }
            }
            else if(startdate != null && enddate != null && startdate.matches(Regex.DATE_REGEX) && enddate.matches(Regex.DATE_REGEX)) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                dtf.format(LocalDate.parse(startdate));
                LocalDate startDate = LocalDate.parse(startdate);
                LocalDate endDate = LocalDate.parse(enddate);
                List<Map<String, Object>> l = new ArrayList<>();

                while(startDate.compareTo(endDate) <= 0) {
                    l.add(SensorDB.selectTotal(patientId, dtf.format(startDate)));
                    startDate = startDate.plusDays(1);
                }

                response.status(200);
                response.type("application/json");

                return "{" + JsonBuilder.jsonList("sensor_total", l, MeasuresLinks.sensorLinks(patientId, "total", startdate, enddate), "test").toString() + "}";
            }

            response.status(404);
            return "ERRORE";
        });
    }

    private void getHueTotal() {
        get("/hue", (request, response) -> {
            int patientId = Integer.parseInt(request.params("patient_id"));
            String date = request.queryParams("date");
            String startdate = request.queryParams("startdate");
            String enddate = request.queryParams("enddate");

            if(date != null && date.matches(Regex.DATE_REGEX)) {
                List<Map<String, Object>> l = new ArrayList<>();
                l.add(HueDB.selectTotal(patientId, date));

                if(!listIsAllNull(l)) {
                    response.status(200);
                    response.type("application/json");
                    return "{" + JsonBuilder.jsonList("hue_total", l, MeasuresLinks.hueLinks(patientId, "total", date), "test").toString() + "}";
                }
            }
            else if(startdate != null && enddate != null && startdate.matches(Regex.DATE_REGEX) && enddate.matches(Regex.DATE_REGEX)) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                dtf.format(LocalDate.parse(startdate));
                LocalDate startDate = LocalDate.parse(startdate);
                LocalDate endDate = LocalDate.parse(enddate);
                List<Map<String, Object>> l = new ArrayList<>();

                while(startDate.compareTo(endDate) <= 0) {
                    l.add(HueDB.selectTotal(patientId, dtf.format(startDate)));
                    startDate = startDate.plusDays(1);
                }

                response.status(200);
                response.type("application/json");

                return "{" + JsonBuilder.jsonList("hue_total", l, MeasuresLinks.hueLinks(patientId, "total", startdate, enddate), "test").toString() + "}";
            }

            response.status(404);
            return "ERRORE";
        });
    }

    private void getFitbitTotal() {
        get("/fitbit", (request, response) -> {
            int patientId = Integer.parseInt(request.params("patient_id"));
            String date = request.queryParams("date");
            String startdate = request.queryParams("startdate");
            String enddate = request.queryParams("enddate");

            if(date != null && date.matches(Regex.DATE_REGEX)) {
                List<Map<String, Object>> l = new ArrayList<>();
                l.add(getFitbitTotal(patientId, date));

                if(!listIsAllNull(l)) {
                    response.status(200);
                    response.type("application/json");
                    return "{" + JsonBuilder.jsonList("fitbit_total", l, MeasuresLinks.fitbitLinks(patientId, "total", date), "test").toString() + "}";
                }
            }
            else if(startdate != null && enddate != null && startdate.matches(Regex.DATE_REGEX) && enddate.matches(Regex.DATE_REGEX)) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                dtf.format(LocalDate.parse(startdate));
                LocalDate startDate = LocalDate.parse(startdate);
                LocalDate endDate = LocalDate.parse(enddate);
                List<Map<String, Object>> l = new ArrayList<>();

                while(startDate.compareTo(endDate) <= 0) {
                    l.add(getFitbitTotal(patientId, dtf.format(startDate)));
                    startDate = startDate.plusDays(1);
                }

                response.status(200);
                response.type("application/json");

                return "{" + JsonBuilder.jsonList("fitbit_total", l, MeasuresLinks.fitbitLinks(patientId, "total", startdate, enddate), "test").toString() + "}";
            }

            response.status(404);
            return "ERRORE";
        });
    }

    private Map<String, Object> getFitbitTotal(int patientId, String date) {
        Integer avgHeartbeat = 0;
        Integer count = 0;
        List<Map<String, Object>> list = FitbitDB.selectDate(patientId, date);

        if(list != null && list.size() > 0) {
            for (Map m : list) {
                if (m.get("avg_heartbeats") != null) {
                    avgHeartbeat += (Integer) m.get("avg_heartbeats");
                    count++;
                }
            }

            if (count != 0)
                avgHeartbeat /= count;
            else
                avgHeartbeat = null;

            Map<String, Object> map = new LinkedHashMap<>();
            map.put("date", date);
            map.put("avg_heartbeats", avgHeartbeat);
            map.put("calories", list.get(list.size() - 1).get("calories"));
            map.put("elevation", list.get(list.size() - 1).get("elevation"));
            map.put("floors", list.get(list.size() - 1).get("floors"));
            map.put("steps", list.get(list.size() - 1).get("steps"));
            map.put("distance", list.get(list.size() - 1).get("distance"));
            map.put("minutes_asleep", list.get(list.size() - 1).get("minutes_asleep"));
            map.put("minutes_awake", list.get(list.size() - 1).get("minutes_awake"));

            return map;
        }

        return null;
    }

    private boolean listIsAllNull(List l) {
        int i=0;

        while(i<l.size()) {
            if(l.get(i) != null)
                return false;
            i++;
        }

        return true;
    }
}
