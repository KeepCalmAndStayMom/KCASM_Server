package server.database2;

import java.util.List;
import java.util.Map;

public interface ClassMeasuresDB extends ClassDB {

    List<Map<String, Object>> SelectDate(int id, String date);

    List<Map<String, Object>> SelectDateInterval(int id, String start_timedate, String end_timedate);
}
