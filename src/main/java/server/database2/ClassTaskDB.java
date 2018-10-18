package server.database2;

import java.util.List;
import java.util.Map;

public interface ClassTaskDB extends ClassDB {

    List<Map<String, Object>> SelectDate(int id, String date);

    List<Map<String, Object>> SelectProgram(int id);

    List<Map<String, Object>> SelectExecuted(int id);
}
