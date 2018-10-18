package server.database2;


import java.util.List;
import java.util.Map;

public interface ClassDB {

    List<Map<String, Object>> Select(int id);

    boolean Update(Map<String, Object> map);

    boolean Insert(Map<String, Object> map);

    boolean Delete(Map<String, Object> map);
}
