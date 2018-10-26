package server.database2;

import java.util.List;
import java.util.Map;

public class TaskGeneralDB {

    static public List<Map<String, Object>> SelectDate(int patientId, String date) {
        final String sql = "SELECT * FROM Task_General WHERE Patient_id=? AND date=?";
        return SharedTaskFunctionDB.getTaskPatientIdDate(sql, patientId, date);
    }

    static public List<Map<String, Object>> SelectProgram(int patientId) {
        final String sql = "SELECT * FROM Task_General WHERE Patient_id=? AND starting_program=1";
        return SharedTaskFunctionDB.getTaskPatientId(sql, patientId);
    }

    static public List<Map<String, Object>> SelectExecuted(int patientId) {
        final String sql = "SELECT * FROM Task_General WHERE Patient_id=? AND executed=1";
        return SharedTaskFunctionDB.getTaskPatientId(sql, patientId);
    }

    static public List<Map<String, Object>> Select(int patientId) {
        final String sql = "SELECT * FROM Task_General WHERE Patient_id=?";
        return SharedTaskFunctionDB.getTaskPatientId(sql, patientId);
    }

    static public boolean Update(Map<String, Object> map) {
        final String sql = "UPDATE Task_General SET Patient_id=?, date=?, category=?, description=?, starting_program=?, executed=? WHERE id=?";
        return SharedTaskFunctionDB.executeUpdate(sql, map);
    }

    static public boolean Insert(Map<String, Object> map) {
        final String sql = "INSERT INTO Task_General(id, Patient_id, Medic_id, date, category, description, starting_program, executed) VALUES (null, ?, ?, ?, ?, ?, ?, ?)";
        return SharedTaskFunctionDB.executeInsert(sql, map);
    }

    static public boolean Delete(Map<String, Object> map) {
        final String sql = "DELETE from Task_General WHERE id=?";
        return SharedTaskFunctionDB.executeDelete(sql, (Integer) map.get("id"));
    }
}
