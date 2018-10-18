package server.database2;

import java.util.List;
import java.util.Map;

public class FitbitDB implements ClassMeasuresDB {

    @Override
    public List<Map<String, Object>> SelectDate(int id, String date) {
        return null;
    }

    @Override
    public List<Map<String, Object>> SelectDateInterval(int id, String start_timedate, String end_timedate) {
        return null;
    }

    @Override
    public List<Map<String, Object>> Select(int id) {
        return null;
    }

    /*NON NECESSARIA*/
    @Override
    public boolean Update(Map<String, Object> map) {
        return false;
    }

    @Override
    public boolean Insert(Map<String, Object> map) {
        return false;
    }

    /*NON NECESSARIA*/
    @Override
    public boolean Delete(Map<String, Object> map) {
        return false;
    }
}
