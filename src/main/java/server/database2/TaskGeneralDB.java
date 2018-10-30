package server.database2;

import java.util.List;
import java.util.Map;

public class TaskGeneralDB {

    static public List<Map<String, Object>> selectDate(int patientId, String medic_id, String date, String executed) {
        String sql = "SELECT * FROM Task_General WHERE Patient_id=? AND date=?";
        if(medic_id != null)
            sql+=" AND medic_id=" + medic_id;
        if(executed != null)
            sql+=" AND executed=" + executed;

        return SharedTaskFunctionDB.getTaskPatientIdDate(sql, patientId, date);
    }

    public static List<Map<String, Object>> selectDateInterval(int patientId, String medic_id, String startdate, String enddate, String executed) {
        String sql = "SELECT * FROM Task_General WHERE Patient=? AND date BETWEEN ? AND ?";
        if(medic_id != null)
            sql+=" AND medic_id=" + medic_id;
        if(executed != null)
            sql+=" AND executed=" + executed;

        return SharedTaskFunctionDB.getTaskPatientIdDateInterval(sql, patientId, startdate, enddate);
    }

    static public List<Map<String, Object>> selectProgram(int patientId) {
        final String sql = "SELECT * FROM Task_General WHERE Patient_id=? AND starting_program=1";
        return SharedTaskFunctionDB.getTaskPatientId(sql, patientId);
    }

    static public List<Map<String, Object>> selectExecuted(int patientId) {
        final String sql = "SELECT * FROM Task_General WHERE Patient_id=? AND executed=1";
        return SharedTaskFunctionDB.getTaskPatientId(sql, patientId);
    }

    static public List<Map<String, Object>> select(int patientId, String medic_id, String executed) {
        String sql = "SELECT * FROM Task_General WHERE Patient_id=?";
        if(medic_id != null)
            sql+=" AND medic_id=" + medic_id;
        if(executed != null)
            sql+=" AND executed=" + executed;

        return SharedTaskFunctionDB.getTaskPatientId(sql, patientId);
    }

    static public boolean update(Map<String, Object> map) {
        final String sql = "UPDATE Task_General SET Patient_id=?, date=?, category=?, description=?, starting_program=?, executed=? WHERE id=?";
        return SharedTaskFunctionDB.executeUpdate(sql, map);
    }

    static public boolean insert(Map<String, Object> map) {
        final String sql = "INSERT INTO Task_General(id, Patient_id, Medic_id, date, category, description, starting_program, executed) VALUES (null, ?, ?, ?, ?, ?, ?, ?)";
        return SharedTaskFunctionDB.executeInsert(sql, map);
    }

    static public boolean delete(Map<String, Object> map) {
        final String sql = "DELETE from Task_General WHERE id=?";
        return SharedTaskFunctionDB.executeDelete(sql, (Integer) map.get("id"));
    }
}
