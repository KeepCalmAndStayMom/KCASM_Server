package server.database2;

import java.util.List;
import java.util.Map;

public class MessageMedicPatientDB implements ClassDB{

    /*NON NECESSARIA*/
    @Override
    public List<Map<String, Object>> Select(int id) {
        return null;
    }

    public List<Map<String, Object>> SelectPatientReceived(int Patient_id) {
        return null;
    }

    public List<Map<String, Object>> SelectPatientSent(int Patient_id) {
        return null;
    }

    public List<Map<String, Object>> SelectMedicReceived(int Medic_id) {
        return null;
    }

    public List<Map<String, Object>> SelectMedicSent(int Medic_id) {
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
