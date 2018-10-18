package server.database2;

import java.util.List;
import java.util.Map;

public class TaskActivityDB implements ClassTaskDB{
    @Override
    public List<Map<String, Object>> SelectDate(int id, String date) {
        return null;
    }

    @Override
    public List<Map<String, Object>> SelectProgram(int id) {
        return null;
    }

    @Override
    public List<Map<String, Object>> SelectExecuted(int id) {
        return null;
    }

    @Override
    public List<Map<String, Object>> Select(int id) {
        return null;
    }

    @Override
    public boolean Update(Map<String, Object> map) {
        return false;
    }

    @Override
    public boolean Insert(Map<String, Object> map) {
        return false;
    }

    @Override
    public boolean Delete(Map<String, Object> map) {
        return false;
    }
}
