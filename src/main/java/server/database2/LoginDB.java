package server.database2;

import java.util.List;
import java.util.Map;

public class LoginDB implements ClassDB {

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

    /*NON NECESSARIA*/
    @Override
    public boolean Delete(Map<String, Object> map) {
        return false;
    }
}
